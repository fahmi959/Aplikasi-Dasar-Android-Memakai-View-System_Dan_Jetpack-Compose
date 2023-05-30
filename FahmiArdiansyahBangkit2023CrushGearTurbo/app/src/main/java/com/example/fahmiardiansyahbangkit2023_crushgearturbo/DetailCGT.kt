package com.example.fahmiardiansyahbangkit2023_crushgearturbo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailCGT : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_cgt)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val crushgearturbo = intent.getParcelableExtra<CrushGearTurbo>(MainActivity.INTENT_PARCELABLE)

        val img_CrushGearTurbo = findViewById<ImageView>(R.id.item_photo)
        val name_CrushGearTurbo = findViewById<TextView>(R.id.item_name)
        val detail_CrushGearTurbo = findViewById<TextView>(R.id.item_detail)

        img_CrushGearTurbo.setImageResource(crushgearturbo?.img_CrushGearTurbo!!)
        name_CrushGearTurbo.text = crushgearturbo.name_CrushGearTurbo
        detail_CrushGearTurbo.text = crushgearturbo.detail_CrushGearTurbo

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}