package com.x7.kotlin_category_product.model.repositories

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.x7.kotlin_category_product.model.CategoryModel
import com.x7.kotlin_category_product.utilits.Constants.CATEGORIES

class RepositoryCategory constructor(
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(CATEGORIES),
    val storageReference: StorageReference = FirebaseStorage.getInstance().getReference(CATEGORIES)
) {

    var livedatasucces = MutableLiveData<Boolean>()
    var livedataprogress = MutableLiveData<Double>()
    val arraylist = ArrayList<CategoryModel>()
    val livedataarraylist = MutableLiveData<ArrayList<CategoryModel>>()


    fun uploadcategory(name: String, uri: Uri) {
        if (uri != null) {
            succes(true)
            val filereference: StorageReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + System.currentTimeMillis().toString()
            )
            filereference.putFile(uri)
                .addOnSuccessListener {
                    filereference.downloadUrl.addOnSuccessListener {
                        var pushkey = databaseReference.push().key.toString()
                        val category = CategoryModel(name, it.toString(), pushkey)
                        databaseReference.child(pushkey).setValue(category).addOnCompleteListener {
                            if (it.isSuccessful) {
                                succes(false)
                            }
                        }

                    }
                }
                .addOnProgressListener {
                    val progress: Double = 100.0 * it.getBytesTransferred() / it.getTotalByteCount()
                    livedataprogress.value = progress
                }
        }

    }

    fun succes(boolean: Boolean) {
        livedatasucces.value = boolean

    }

    fun readfromfirebase(): MutableLiveData<ArrayList<CategoryModel>> {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arraylist.clear()

                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val categoryM = datasnapshot.getValue(CategoryModel::class.java)
                    arraylist.add(categoryM!!)
                }
                livedataarraylist.value = arraylist
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return livedataarraylist
    }
}