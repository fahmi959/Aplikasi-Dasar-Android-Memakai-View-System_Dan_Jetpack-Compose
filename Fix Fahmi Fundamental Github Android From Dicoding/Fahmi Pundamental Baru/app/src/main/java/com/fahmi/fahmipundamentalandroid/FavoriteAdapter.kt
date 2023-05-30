package com.fahmi.fahmipundamentalandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahmi.fahmipundamentalandroid.databinding.ItemRowBinding

class FavoriteAdapter(var favoriteList: List<PenggunaGithub>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(penggunaGithub: PenggunaGithub) {
//            binding.ivBookmark.setImageResource(R.drawable.ic_bookmarked_white)
            binding.ivBookmark.setVisibility(View.GONE)
            binding.ivDeleteBookmark.setVisibility(View.VISIBLE)


            binding.tvItemName.text = penggunaGithub.login
            binding.tvItemDescription.text = penggunaGithub.id.toString()
            binding.tvItemURLUSER.text = penggunaGithub.htmlUrl

            Glide.with(itemView.context)
                .load(penggunaGithub.avatarUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgItemPhoto)

// KLIK LIST RV
            binding.root.setOnClickListener {
                klik_detail?.Sudah_KlikDetail_Item_Rv(penggunaGithub)
            }

                // KLIK DELETE LIST RV
            binding.ivDeleteBookmark.setOnClickListener {
                klikDelete?.Sudah_KlikDelete_Item_Rv(adapterPosition)
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size


    // Klik List Rv
    private var klik_detail: KlikDetail? = null

    fun set_klik_detail(klik_detail: KlikDetail) {
        this.klik_detail = klik_detail
    }

    interface KlikDetail {
        fun Sudah_KlikDetail_Item_Rv(data: PenggunaGithub)
    }


    // KLIK DELETE RV
    private var klikDelete: KlikDelete? = null

    fun set_klik_delete(klikDelete: KlikDelete) {
        this.klikDelete = klikDelete
    }
    interface KlikDelete {
        fun Sudah_KlikDelete_Item_Rv(position: Int)
    }



}
