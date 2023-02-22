package com.x7.kotlin_category_product.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.x7.kotlin_category_product.model.CategoryModel
import com.x7.kotlin_category_product.model.repositories.RepositoryCategory

class CategoryViewModel constructor(
    val repositoryCategory: RepositoryCategory = RepositoryCategory()
): ViewModel() {

    fun uploadnewcategory(name:String,uri: Uri){
        repositoryCategory.uploadcategory(name,uri)
    }
    fun uploadsucces(): MutableLiveData<Boolean> {
        return repositoryCategory.livedatasucces
    }
    fun uploadprogress(): MutableLiveData<Double> {
        return repositoryCategory.livedataprogress
    }
    fun readalldatafirebase():MutableLiveData<ArrayList<CategoryModel>>{
        return repositoryCategory.readfromfirebase()
    }

}