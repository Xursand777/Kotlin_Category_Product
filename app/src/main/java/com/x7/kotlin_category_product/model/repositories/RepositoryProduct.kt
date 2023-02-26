package com.x7.kotlin_category_product.model.repositories

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.x7.kotlin_category_product.model.CategoryModel
import com.x7.kotlin_category_product.model.ProductModel
import com.x7.kotlin_category_product.utilits.Constants.ALLPRODUCTS

import com.x7.kotlin_category_product.utilits.Constants.PRODUCTS

class RepositoryProduct constructor(
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(PRODUCTS),
    var databaseReferenceall: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(ALLPRODUCTS),
    var storageReference: StorageReference = FirebaseStorage.getInstance().getReference().child(PRODUCTS)
) {
    var livedatasucces=MutableLiveData<Boolean>()
    var livedataprogress=MutableLiveData<Double>()
     val livedataarrraylisttwo = MutableLiveData<ArrayList<ProductModel>>()
    val arrayList = ArrayList<ProductModel>()

    fun addproduct(
        categoryname:String,
        name: String,
        uri: Uri,
        price: String,
        description: String,
    ){

        databaseReference.child(categoryname)
        if (uri != null) {
             succes(true)
            val filereference: StorageReference = storageReference.child(System.currentTimeMillis().toString() + "." + System.currentTimeMillis().toString())
            filereference.putFile(uri)
                .addOnSuccessListener {
                    filereference.downloadUrl.addOnSuccessListener {
                        var pushkey = databaseReference.push().key.toString()
                        val product=ProductModel(name,it.toString(),price,description,pushkey)
                        databaseReference.child(categoryname).child(pushkey).setValue(product)
                        databaseReferenceall.child(pushkey).setValue(product).addOnCompleteListener {
                            if (it.isSuccessful){
                                succes(false)
                            }
                        }


                    }
                }
                .addOnProgressListener {
                    val progress: Double = 100.0 * it.getBytesTransferred() / it.getTotalByteCount()
                      livedataprogress.value=progress
                }
        }
    }


    //shu yerda yangi fun ochib allproducts dagilarni o`qib olish code sini yozaman
    fun readfromsfirebasetwo():MutableLiveData<ArrayList<ProductModel>>{
          databaseReferenceall.addValueEventListener(object :ValueEventListener{
              override fun onDataChange(snapshot: DataSnapshot) {
                  arrayList.clear()

                  for (datasnapshop:DataSnapshot in snapshot.children){
                      val productM=datasnapshop.getValue(ProductModel::class.java)
                      arrayList.add(productM!!)
                  }
                  livedataarrraylisttwo.value=arrayList

              }

              override fun onCancelled(error: DatabaseError) {

              }
          })
        return livedataarrraylisttwo
    }

    fun readeverycategory(categoryname: String):MutableLiveData<ArrayList<ProductModel>>{
        databaseReference.child(categoryname).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()

                for (datasnapshop:DataSnapshot in snapshot.children){
                    val productM=datasnapshop.getValue(ProductModel::class.java)
                    arrayList.add(productM!!)
                }
                livedataarrraylisttwo.value=arrayList

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return livedataarrraylisttwo
    }


    fun succes(boolean: Boolean){
       livedatasucces.value=boolean
    }



}