package com.example.fahmiardiansyahbangkit2023_crushgearturbo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CrushgearturboAdapter(private val context: Context, private val crushgearturbo: List<CrushGearTurbo>, val listener:(CrushGearTurbo) -> Unit) : RecyclerView.Adapter<CrushgearturboAdapter.CrushgearturboViewHolder>(){
    class CrushgearturboViewHolder (view: View): RecyclerView.ViewHolder(view){

        val img_CrushGearTurbo = view.findViewById<ImageView>(R.id.item_photo)
        val name_CrushGearTurbo = view.findViewById<TextView>(R.id.item_name)
        val description_CrushGearTurbo = view.findViewById<TextView>(R.id.item_description)


        fun bindView(crushgearturbo: CrushGearTurbo, listener: (CrushGearTurbo) -> Unit){

            img_CrushGearTurbo.setImageResource(crushgearturbo.img_CrushGearTurbo)
            name_CrushGearTurbo.text = crushgearturbo.name_CrushGearTurbo
            description_CrushGearTurbo.text = crushgearturbo.description_CrushGearTurbo
            itemView.setOnClickListener{listener(crushgearturbo)}
        }

    }

    override fun getItemCount(): Int = crushgearturbo.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrushgearturboViewHolder {
        return CrushgearturboViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_layout_crushgearturbo_fahmi, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CrushgearturboViewHolder, position: Int) {
        holder.bindView(crushgearturbo[position], listener)
    }
}