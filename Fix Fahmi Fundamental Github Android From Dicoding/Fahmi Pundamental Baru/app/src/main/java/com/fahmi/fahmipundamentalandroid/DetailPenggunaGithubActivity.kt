package com.fahmi.fahmipundamentalandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fahmi.fahmipundamentalandroid.databinding.ActivityDetailPenggunaGithubBinding


import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPenggunaGithubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPenggunaGithubBinding
    private lateinit var user: PenggunaGithub
    private var isFavorite = false
    private lateinit var favoriteAdapter : FavoriteAdapter


    companion object {
        const val TANGKAP_USERNAME = "tangkap_username"
        const val TAG = "DetailPenggunaGithubActivity"
         const val TANGKAP_FOLLOWERS = "tangkap_followers"
        const val TANGKAP_ID = "tangkap_id"
        const val TANGKAP_AvatarURL = "tangkap_AvatarURL"
        const val TANGKAP_HtmlURL = "tangkap_HtmlURL"

        const val TAMPUNGAN_USERNAME = "tampungan_username"

        // TAB MENU FRAGMENT
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPenggunaGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.actionbar_detailactivity)


//        val username = "fahmi959"
        val username = intent.getStringExtra(TANGKAP_USERNAME)

        if (username != null) {
            showLoading(true)
            getUserDetail(username)
        } else {
            Toast.makeText(this@DetailPenggunaGithubActivity, "tidak ada username ditemukan ", Toast.LENGTH_SHORT).show()
        }



        // MEMBAGIKAN KEPADA TAB MENU FRAGMENT
        val berbagi = Bundle()
        berbagi.putString(DetailPenggunaGithubActivity.TANGKAP_USERNAME, username)

        // TAB MENU FRAGMENT
        val sectionsPagerAdapter = intent.getStringExtra(TANGKAP_USERNAME)
            ?.let { FragmentsPagerAdapter(this, it) }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(DetailPenggunaGithubActivity.TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f




//        binding.imgFav.setOnClickListener {
//            val penggunaGithub = PenggunaGithub(
//                login = intent.getStringExtra(DetailPenggunaGithubActivity.TANGKAP_USERNAME) ?: "",
//                id = intent.getIntExtra(DetailPenggunaGithubActivity.TANGKAP_ID, -1),
//                avatarUrl = intent.getStringExtra(DetailPenggunaGithubActivity.TANGKAP_AvatarURL) ?: "",
//                htmlUrl = intent.getStringExtra(DetailPenggunaGithubActivity.TANGKAP_HtmlURL)?: "",
//                isBookmarked = true
//            )
//
//            val dbHelper = FavoriteDbHelper.getInstance(this@DetailPenggunaGithubActivity)
//            val isAdded = dbHelper.isFavorite(penggunaGithub)
//            // INI ADALAH FUNGSINYA SAAT DIMASUKKAN KE DALAM DATABASE
//            dbHelper.insertFavorite(penggunaGithub)
//            // PARAMETER JIKA SUKSES
//            binding.imgFav.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
//            Toast.makeText(this, "User added to favorites", Toast.LENGTH_SHORT).show()
//
////            if (isAdded) {
////                dbHelper.removeFavorite(penggunaGithub)
////                binding.imgFav.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
////                Toast.makeText(this, "User removed from favorites", Toast.LENGTH_SHORT).show()
////            } else {
////                dbHelper.insertFavorite(penggunaGithub)
////                binding.imgFav.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
////                Toast.makeText(this@DetailPenggunaGithubActivity, "User added to favorites", Toast.LENGTH_SHORT).show()
////            }
//        }


        val dbHelper = FavoriteDbHelper.getInstance(this@DetailPenggunaGithubActivity)
        val penggunaGithub = PenggunaGithub(
            login = intent.getStringExtra(TANGKAP_USERNAME) ?: "",
            id = intent.getIntExtra(TANGKAP_ID, -1),
            avatarUrl = intent.getStringExtra(TANGKAP_AvatarURL) ?: "",
            htmlUrl = intent.getStringExtra(TANGKAP_HtmlURL) ?: "",
            isBookmarked = true
        )

        isFavorite = dbHelper.isFavorite(penggunaGithub)
        updateFavoriteButton()

        binding.imgFav.setOnClickListener {
            if (isFavorite) {
                // Hapus pengguna dari database favorit
                dbHelper.removeFavorite(penggunaGithub)


                Toast.makeText(this, "User removed from favorites", Toast.LENGTH_SHORT).show()
            } else {
                // Tambahkan pengguna ke database favorit
                dbHelper.insertFavorite(penggunaGithub)
                Toast.makeText(this, "User added to favorites", Toast.LENGTH_SHORT).show()
            }
            isFavorite = !isFavorite
            updateFavoriteButton()
        }
    }

    private fun updateFavoriteButton() {
        if (isFavorite) {
            binding.imgFav.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
        } else {
            binding.imgFav.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
    }







    private fun getUserDetail(username: String) {
        ApiConfig.getApiService().getUserDetail(username).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        binding.apply {
                            usernamePengguna.text = user.login
                            namaPengguna.text = user.name
                            emailPengguna.text = user.email as CharSequence?
                            lokasiPengguna.text = user.location
                            perusahaanPengguna.text = user.company
                            blogPengguna.text = user.blog
                            biodataPengguna.text = user.bio
                            followersPengguna.text = "${user.followers} PENGIKUT"
                            followingPengguna.text = "${user.following} MENGIKUTI"

                            Glide.with(this@DetailPenggunaGithubActivity)
                                .load(user.avatarUrl)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .centerCrop()
                                .into(fotoPengguna)
                        }
                    } else {
                        Toast.makeText(this@DetailPenggunaGithubActivity, getString(R.string.description), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@DetailPenggunaGithubActivity,"GAGAL MERESPON API", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
                Toast.makeText(this@DetailPenggunaGithubActivity, "error cuk", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE
    }





}