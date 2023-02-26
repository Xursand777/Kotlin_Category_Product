package com.x7.kotlin_category_product.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.x7.kotlin_category_product.databinding.RecyclerviewItemBinding
import com.x7.kotlin_category_product.model.CategoryModel

class CategoryAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<CategoryModel>
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    val mainActivity:MainActivity=context as MainActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view= RecyclerviewItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            textviewcategory.text=arrayList.get(position).name
            //Glide
            Glide.with(context).load(arrayList.get(position).imageurl).centerCrop().into(imageviewcategory)
            //Glide
            linearlay1.setOnLongClickListener {
                val intent=Intent(context,MainActivity3::class.java)
                intent.putExtra("name",arrayList.get(position).name)
                intent.putExtra("image",arrayList.get(position).imageurl)
                context.startActivity(intent)
                return@setOnLongClickListener true
            }
            linearlay1.setOnClickListener {
                mainActivity.categorychanged(arrayList.get(position).name!!)
            }
        }
    }

    override fun getItemCount(): Int =arrayList.size

    class CategoryViewHolder(val binding: RecyclerviewItemBinding):RecyclerView.ViewHolder(binding.root)

}