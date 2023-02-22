package com.x7.kotlin_category_product.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.x7.kotlin_category_product.R
import com.x7.kotlin_category_product.databinding.ActivityMainBinding
import com.x7.kotlin_category_product.model.CategoryModel
import com.x7.kotlin_category_product.utilits.Constants.CATEGORIES
import com.x7.kotlin_category_product.viewmodel.CategoryViewModel

class MainActivity : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    lateinit var storageReference: StorageReference
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var arrayList: ArrayList<CategoryModel>
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()


        categoryViewModel= ViewModelProvider(this@MainActivity).get(CategoryViewModel::class.java)

        binding.apply {
            imageviewopenac2.setOnClickListener {
                startActivity(Intent(this@MainActivity, MainActivity2::class.java))
            }

            recyclerview1.layoutManager= LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,true)
            categoryViewModel.readalldatafirebase().observe(this@MainActivity,{
                categoryAdapter= CategoryAdapter(this@MainActivity,it)
                recyclerview1.adapter=categoryAdapter
            })

        }

    }
}