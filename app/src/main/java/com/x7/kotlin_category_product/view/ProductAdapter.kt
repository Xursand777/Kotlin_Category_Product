package com.x7.kotlin_category_product.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.x7.kotlin_category_product.databinding.RecyclerviewItem2Binding
import com.x7.kotlin_category_product.model.ProductModel


class ProductAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<ProductModel>,
):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=RecyclerviewItem2Binding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       holder.binding.apply {
           textviewproduct.text=arrayList.get(position).name
           Glide.with(context).load(arrayList.get(position).imageurl).centerCrop().into(imageviewproduct)
       }
    }

    override fun getItemCount(): Int =arrayList.size

    class ProductViewHolder(val binding: RecyclerviewItem2Binding):RecyclerView.ViewHolder(binding.root)
}