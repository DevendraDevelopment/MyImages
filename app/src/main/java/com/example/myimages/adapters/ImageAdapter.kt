package com.example.myimages.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myimages.utils.ImageLoaderTask
import com.example.myimages.models.ModelImages
import com.example.myimages.R
import com.example.myimages.databinding.ImageItemBinding


class ImageAdapter(
    private var context: Context,
    private val mData: List<ModelImages>
) :
    RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<ImageItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.image_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mData[position])
        ImageLoaderTask(context, holder.binding.imageView).execute(mData[position]?.thumbnail?.domain+"/"+mData[position]?.thumbnail?.basePath+"/"+ mData[position]?.thumbnail?.qualities?.getOrElse(3){20}+"/"+mData[position]?.thumbnail?.key)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder internal constructor(val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ){
        fun bind(myObject: ModelImages?) {
            binding.item = myObject
            binding.executePendingBindings()
        }
    }
}