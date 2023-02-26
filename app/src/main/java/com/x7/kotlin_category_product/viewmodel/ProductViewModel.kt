package com.x7.kotlin_category_product.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.x7.kotlin_category_product.model.ProductModel
import com.x7.kotlin_category_product.model.repositories.RepositoryProduct

class ProductViewModel constructor(
    val repositoryProduct: RepositoryProduct= RepositoryProduct()
):ViewModel(){

    fun addnewproduct(
        categoryname: String,
        name: String,
        uri: Uri,
        price: String,
        description: String,
    ){
        repositoryProduct.addproduct(categoryname,name,uri,price,description)
    }
    fun readalldatafirebasetwo(): MutableLiveData<ArrayList<ProductModel>> {
        return repositoryProduct.readfromsfirebasetwo()
    }

    fun readeverycategory(categoryname: String):MutableLiveData<ArrayList<ProductModel>>{
        return repositoryProduct.readeverycategory(categoryname)
    }

    fun uploadproductprogress():MutableLiveData<Double>{
        return repositoryProduct.livedataprogress
    }
    fun uploadsucces():MutableLiveData<Boolean>{
        return repositoryProduct.livedatasucces
    }


}