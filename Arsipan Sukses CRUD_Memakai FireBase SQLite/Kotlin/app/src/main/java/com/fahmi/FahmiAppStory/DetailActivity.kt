package com.fahmi.FahmiAppStory

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fahmi.FahmiAppStory.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menggunakan ViewBinding untuk mengatur layout activity
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data dari intent
        val dataHero = intent.getParcelableExtra<Note>("key_note")

// Mengatur data dengan ViewBinding
        binding.itemName.text = dataHero?.nama
        binding.itemTitle.text = dataHero?.title
        binding.itemDescription.text = dataHero?.description
//// Menggunakan library yang sesuai untuk mengatur gambar dari URL, misalnya Picasso atau Glide
//        Picasso.get().load(dataHero?.model_foto_url).into(binding.itemPhoto)


// Mengubah ByteArray menjadi Bitmap
        val bitmap = BitmapFactory.decodeByteArray(
            dataHero?.model_foto_url, 0, dataHero?.model_foto_url?.size ?: 0
        )

// Mengatur Bitmap ke ImageView menggunakan ViewBinding
        binding.itemPhoto.setImageBitmap(bitmap)


    }
}