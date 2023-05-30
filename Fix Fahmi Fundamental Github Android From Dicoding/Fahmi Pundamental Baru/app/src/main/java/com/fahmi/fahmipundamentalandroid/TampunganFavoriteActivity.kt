package com.fahmi.fahmipundamentalandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahmi.fahmipundamentalandroid.databinding.ActivityTampunganFavoriteBinding


class TampunganFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTampunganFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteDbHelper: FavoriteDbHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTampunganFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAdapter = FavoriteAdapter(emptyList())
        binding.rvFav.adapter = favoriteAdapter
        binding.rvFav.layoutManager = LinearLayoutManager(this)

        favoriteDbHelper = FavoriteDbHelper.getInstance(this)
        loadData()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // KLIK ITEM RECYCLE VIEW

       favoriteAdapter.set_klik_detail(object : FavoriteAdapter.KlikDetail {
            override fun Sudah_KlikDetail_Item_Rv(data: PenggunaGithub) {
                Intent(this@TampunganFavoriteActivity, DetailPenggunaGithubActivity::class.java).also {
                    it.putExtra(DetailPenggunaGithubActivity.TANGKAP_USERNAME ,data.login)
                    it.putExtra(DetailPenggunaGithubActivity.TANGKAP_ID ,data.id)
                    it.putExtra(DetailPenggunaGithubActivity.TANGKAP_AvatarURL ,data.avatarUrl)
                    it.putExtra(DetailPenggunaGithubActivity.TANGKAP_HtmlURL ,data.htmlUrl)
                    startActivity(it)
                }
            }
        })


        // KLIK DELETE HAPUS PADA FAVORITE LIST RECYCLE VIEW
        favoriteAdapter.set_klik_delete(object : FavoriteAdapter.KlikDelete {
            override fun Sudah_KlikDelete_Item_Rv(position: Int) {
                removeFavorite(position)
                Toast.makeText(this@TampunganFavoriteActivity, "BERHASIL DI DELETE", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun loadData() {
        showLoading(true)
        val favoriteList = favoriteDbHelper.getFavoriteList()
        favoriteAdapter.favoriteList = favoriteList
        favoriteAdapter.notifyDataSetChanged()
        showLoading(false)
    }

    private fun insertFavorite(penggunaGithub: PenggunaGithub) {
        favoriteDbHelper.insertFavorite(penggunaGithub)
        loadData()
    }

//    private fun removeFavorite(penggunaGithub: PenggunaGithub) {
//        favoriteDbHelper.removeFavorite(penggunaGithub)
//        loadData()
//    }

//    private fun removeFavorite(position: Int) {
//        val penggunaGithub = favoriteAdapter.favoriteList[position]
//        favoriteDbHelper.removeFavorite(penggunaGithub)
//        favoriteAdapter.favoriteList = favoriteDbHelper.getFavoriteList()
//        favoriteAdapter.notifyDataSetChanged()
//    }

private fun removeFavorite(position: Int) {
    val penggunaGithub = favoriteAdapter.favoriteList[position]
    favoriteDbHelper.removeFavorite(penggunaGithub)
    favoriteAdapter.favoriteList = favoriteDbHelper.getFavoriteList()
    favoriteAdapter.notifyItemRemoved(position)

}



    private fun isFavorite(penggunaGithub: PenggunaGithub): Boolean {
        return favoriteDbHelper.isFavorite(penggunaGithub)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}