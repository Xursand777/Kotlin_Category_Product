package com.x7.kotlin_category_product.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.x7.kotlin_category_product.R
import com.x7.kotlin_category_product.databinding.ActivityMainBinding
import com.x7.kotlin_category_product.model.CategoryModel
import com.x7.kotlin_category_product.utilits.Constants.CATEGORIES
import com.x7.kotlin_category_product.viewmodel.CategoryViewModel
import com.x7.kotlin_category_product.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var productAdapter: ProductAdapter
    lateinit var arrayList: ArrayList<CategoryModel>
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var productViewModel: ProductViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()


        categoryViewModel= ViewModelProvider(this@MainActivity).get(CategoryViewModel::class.java)
        productViewModel= ViewModelProvider(this@MainActivity).get(ProductViewModel::class.java)

        binding.apply {
            imageviewopenac2.setOnClickListener {
                startActivity(Intent(this@MainActivity, MainActivity2::class.java))
            }

            recyclerview1.layoutManager= LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            categoryViewModel.readalldatafirebase().observe(this@MainActivity,{
                categoryAdapter= CategoryAdapter(this@MainActivity,it)
                recyclerview1.adapter=categoryAdapter
            })
            recyclerview2.layoutManager=GridLayoutManager(this@MainActivity,3)
            productViewModel.readalldatafirebasetwo().observe(this@MainActivity,{
                it.shuffle()
                productAdapter= ProductAdapter(this@MainActivity,it)
                recyclerview2.adapter=productAdapter
            })
        }

    }

    fun categorychanged(categoryname:String){
        productViewModel.readeverycategory(categoryname).observe(this@MainActivity,{
            productAdapter= ProductAdapter(this@MainActivity,it)
            binding.recyclerview2.adapter=productAdapter
        })
    }
}