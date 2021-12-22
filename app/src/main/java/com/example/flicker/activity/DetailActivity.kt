package com.example.flicker.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flicker.databinding.ActivityDetailBinding
import android.graphics.Bitmap
import com.bumptech.glide.Glide


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.apply {

            val imageUrl = intent.getStringExtra("url_image")
            val imageTitle = intent.getStringExtra("title_image")

            Glide.with(this@DetailActivity)
                .load(imageUrl)
                .into(imageDetail)
            titleDetail.text = imageTitle

        }
    }
}