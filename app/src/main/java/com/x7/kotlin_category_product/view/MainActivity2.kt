package com.x7.kotlin_category_product.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.x7.kotlin_category_product.R
import com.x7.kotlin_category_product.databinding.ActivityMain2Binding
import com.x7.kotlin_category_product.viewmodel.CategoryViewModel

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    var imageuri: Uri?=null
    lateinit var categoryViewModel: CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)



        title="Add new categories"

        categoryViewModel= ViewModelProvider(this@MainActivity2).get(CategoryViewModel::class.java)

        binding.apply {
            imageviewopengallery.setOnClickListener {
                openFileChooser()
            }
            buttonaddcategory.setOnClickListener {
                if (imageuri!=null){
                    categoryViewModel.uploadnewcategory(edittexcategoryname.text.toString(),imageuri!!)
                }else{
                    Toast.makeText(this@MainActivity2,"Select a Image for Category", Toast.LENGTH_SHORT).show()
                }

            }
        }



        //progressbarshowhide
        categoryViewModel.uploadsucces().observe(this@MainActivity2,{
            if (it){
                showprogressbar()
            }else{
                hideprogressbar()
            }
        })
        categoryViewModel.uploadprogress().observe(this@MainActivity2,{
            binding.apply {
                progressBarhorizontal.progress=it.toInt()
                textviewprogress.text="${it.toInt()} %"
            }
        })
        //progressbarshowhide

    }





    //Show progressbar hide progressbar
    fun showprogressbar(){
        binding.progressBarhorizontal.visibility= View.VISIBLE
        binding.textviewprogress.visibility= View.VISIBLE
    }
    fun hideprogressbar(){
        binding.progressBarhorizontal.visibility= View.GONE
        binding.textviewprogress.visibility= View.GONE
    }
    //Show progressbar hide progressbar

    //Open Gallery
    fun openFileChooser() {
        getContent.launch("image/*")
    }
    //Open Gallery and Set image to imageview
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        binding.imageviewopengallery.setImageURI(uri)
        if (uri != null) {
            imageuri = uri
            binding.buttonaddcategory.isEnabled=true
        }
    }
}