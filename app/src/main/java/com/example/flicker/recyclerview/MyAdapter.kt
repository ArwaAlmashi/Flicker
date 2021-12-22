package com.example.flicker.recyclerview

import android.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flicker.activity.DetailActivity
import com.example.flicker.databinding.ActivityDetailBinding
import com.example.flicker.databinding.CellBinding
import com.example.flicker.model.Photo
import androidx.core.content.ContextCompat.startActivity

import android.graphics.Bitmap
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat


class MyAdapter(private val context: Context, private var photos: ArrayList<Photo>) :
    RecyclerView.Adapter<MyAdapter.CellHolder>() {

    private var lastPosition = -1

    class CellHolder(val binding: CellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.CellHolder {
        return CellHolder(
            CellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: MyAdapter.CellHolder, position: Int) {
        val photo = photos[position]
        holder.binding.apply {
            if (photo.url_h != null) {
                Glide.with(context)
                    .load(photo.url_h)
                    .into(image)
                nullText.text =""
            } else {
                image.setImageResource(0)
                nullText.text = "No Image"
            }
            title.text = photo.title
        }

        holder.binding.cell.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("url_image", photo.url_h)
            intent.putExtra("title_image", photo.title)
            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int = photos.size

    fun update(newList: ArrayList<Photo>) {
        photos = newList
        notifyDataSetChanged()
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {

            val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}