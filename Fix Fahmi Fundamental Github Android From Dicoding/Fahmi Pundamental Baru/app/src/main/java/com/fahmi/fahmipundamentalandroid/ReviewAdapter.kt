package com.fahmi.fahmipundamentalandroid

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fahmi.fahmipundamentalandroid.databinding.ItemRowBinding
class ReviewAdapter(
    private val listReview: ArrayList<PenggunaGithub>,

) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    private val context: Context? = null
    fun setReviewUsers(users: ArrayList<PenggunaGithub>) {
        listReview.clear()
        listReview.addAll(users)
        notifyDataSetChanged()
    }

    fun setDataFollowers(followers: ArrayList<PenggunaGithub>) {
        listReview.clear()
        listReview.addAll(followers)
        notifyDataSetChanged()
    }

    fun setDataFollowing(following: ArrayList<PenggunaGithub>) {
        listReview.clear()
        listReview.addAll(following)
        notifyDataSetChanged()
    }

//    fun toggleBookmark(pengguna: PenggunaGithub) {
//        val favDbHelper = FavoriteDbHelper.getInstance(context!!)
//        val favInDb = favDbHelper.getAllFavorites().find { it.id == pengguna.id }
//
//        favInDb?.let { fav ->
//            fav.isBookmarked = !fav.isBookmarked
//            favDbHelper.updateFav(fav)
//
//            // update tampilan daftar dengan data terbaru
//            val position = listReview.indexOf(pengguna)
//            if (position != -1) {
//                listReview[position] = pengguna.copy(isBookmarked = fav.isBookmarked)
//                notifyItemChanged(position)
//            }
//        }
//    }


    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pengguna: PenggunaGithub) {
            Glide.with(itemView)
                .load(pengguna.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = pengguna.login
            binding.tvItemDescription.text = pengguna.id.toString()
            binding.tvItemURLUSER.text = pengguna.htmlUrl

            binding.root.setOnClickListener {
                klik_detail?.Sudah_KlikDetail_Item_Rv(pengguna)
            }

            val ivBookmark = binding.ivBookmark
            val fav = listReview.find { it.id == pengguna.id }
            if (fav != null && fav.isBookmarked) {
                ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmarked_white))
            } else {
                ivBookmark.setImageDrawable(ContextCompat.getDrawable
                    (ivBookmark.context, R.drawable.ic_bookmark_white))
            }
            ivBookmark.setOnClickListener {
                fav?.let {
//                    toggleBookmark(pengguna)
                    ivBookmark.setImageDrawable(
                        ContextCompat.getDrawable(
                            ivBookmark.context,
                            if (it.isBookmarked) R.drawable.ic_bookmarked_white else R.drawable.ic_bookmark_white
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listReview[position])


    }

    private var klik_detail: KlikDetail? = null

    fun set_klik_detail(klik_detail: KlikDetail) {
        this.klik_detail = klik_detail
    }

    interface KlikDetail {
        fun Sudah_KlikDetail_Item_Rv(data: PenggunaGithub)
    }

    // FAV

    override fun getItemCount(): Int = listReview.size
}