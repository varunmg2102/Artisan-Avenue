package com.varun.artisansavenue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.varun.artisansavenue.databinding.AllOrderItemLayoutBinding
import com.varun.artisansavenue.model.AllOrderModel

class AllOrderAdapter (val list: ArrayList<AllOrderModel>, val context: Context):
RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>()
{

    inner class AllOrderViewHolder(val binding : AllOrderItemLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTitle.text = list[position].name
        holder.binding.productPrice.text = list[position].price
        Glide.with(holder.itemView.context)
            .load(list[position].image)
            .into(holder.binding.productImage)



        when(list[position].status){
            "Ordered" -> {
               holder.binding.productStatus.text = "Ordered"
            }
            "Dispatched" -> {
                holder.binding.productStatus.text = "Dispatched"
            }
            "Delivered" -> {
                holder.binding.productStatus.text = "Delivered"
            }
            "Canceled" -> {
                holder.binding.productStatus.text = "Canceled"
            }
        }
    }

}