package com.fahmi.fahmipundamentalandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahmi.fahmipundamentalandroid.databinding.ItemRowBinding
import kotlinx.coroutines.NonDisposableHandle.parent


class PenggunaAdapter(private val listPengguna: ArrayList<ParsingPengguna>) : RecyclerView.Adapter<PenggunaAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

//       // JIKA TIDAK PAKAI VIEW BINDING
////        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row, viewGroup, false)
////        return ViewHolder(view)

        val binding = ItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // JIKA TIDAK PAKAI VIEW BINDING
//        val pengguna = listPengguna[position]
//        viewHolder.tvName.text = pengguna.login // Mengisi data nama pengguna ke TextView tvName
//        viewHolder.tvDescription.text = pengguna.id.toString()
//        Glide.with(viewHolder.itemView.context) // Menggunakan Glide untuk load avatar
//            .load(pengguna.avatar_url)
//            .into(viewHolder.avatarUrl) // Menampilkan avatar pada ImageView img_item_photo

        val pengguna = listPengguna[position]
        viewHolder.bind(pengguna)
    }

    override fun getItemCount(): Int {
        return listPengguna.size
    }

    inner class ViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
//        // JIKA TIDAK PAKAI VIEW BINDING
//        val tvName: TextView = view.findViewById(R.id.tv_item_name)
//        val tvDescription: TextView = view.findViewById(R.id.tv_item_description)
//        val avatarUrl: ImageView = view.findViewById(R.id.img_item_photo)
        fun bind(pengguna: ParsingPengguna) {
            // Bind data pengguna ke view di sini
            binding.tvItemName.text = pengguna.login
            binding.tvItemDescription.text = pengguna.login
// TAMBAHKAN TEXTVIEW ATAU IMAGEVIEW DISINI
            Glide.with(binding.root.context)
                .load(pengguna.avatar_url)
                .into(binding.imgItemPhoto)
        }
    }
}