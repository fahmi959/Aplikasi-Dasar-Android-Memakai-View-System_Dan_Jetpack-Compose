package com.fahmi.fahmipundamentalandroid

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fahmi.fahmipundamentalandroid.databinding.ActivityDetailPenggunaBinding
import com.fahmi.fahmipundamentalandroid.databinding.ActivityDetailPenggunaGithubBinding
import retrofit2.Callback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.message.BasicHeader
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
//
//class DetailPenggunaActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityDetailPenggunaBinding
//
//    companion object {
//        const val EXTRA_USERNAME = "extra_username"
//        const val TAG = "DetailPenggunaActivity"
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDetailPenggunaBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        supportActionBar?.title = getString(R.string.description)
//
//
////        val username = "fahmi959"
//        val username = intent.getStringExtra(EXTRA_USERNAME)
//
//        if (username != null) {
//            showLoading(true)
//            getUserDetail(username)
//        } else {
//            Toast.makeText(this@DetailPenggunaActivity, "tidak ada username ditemukan ", Toast.LENGTH_SHORT).show()
//        }
//
//
//        binding.btnFollowers.setOnClickListener {
//            val followersIntent = Intent(this, MainActivity::class.java)
//            followersIntent.putExtra(DetailPenggunaGithubActivity.TANGKAP_USERNAME, username)
//            followersIntent.putExtra(DetailPenggunaGithubActivity.TANGKAP_FOLLOWERS, true)
//            startActivity(followersIntent)
//        }
//
//    }
//
//    private fun getUserDetail(username: String) {
//        ApiConfig.getApiService().getUserDetail(username).enqueue(object : Callback<DetailUserResponse> {
//            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val user = response.body()
//                    if (user != null) {
//                        binding.apply {
//                            usernamePengguna.text = user.login
//                            namaPengguna.text = user.name
//                            emailPengguna.text = user.email as CharSequence?
//                            lokasiPengguna.text = user.location
//                            perusahaanPengguna.text = user.company
//                            blogPengguna.text = user.blog
//                            biodataPengguna.text = user.bio
//
//                            Glide.with(this@DetailPenggunaActivity)
//                                .load(user.avatarUrl)
//                                .transition(DrawableTransitionOptions.withCrossFade())
//                                .centerCrop()
//                                .into(fotoPengguna)
//                        }
//                    } else {
//                        Toast.makeText(this@DetailPenggunaActivity, getString(R.string.description), Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(this@DetailPenggunaActivity, getString(R.string.description), Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure: ${t.message}")
//                Toast.makeText(this@DetailPenggunaActivity, "error cuk", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun showLoading(state: Boolean) {
//        binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE
//    }
//}


class DetailPenggunaActivity : AppCompatActivity() {


    companion object {

        // TAB MENU FRAGMENT
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailPenggunaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPenggunaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRandomQuote()

// TAB MENU FRAGMENT
        val sectionsPagerAdapter = FragmentsPagerAdapter(this, username = "fahmi959")
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(DetailPenggunaGithubActivity.TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun getRandomQuote() {
        binding.progressBarDetail.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/fahmi959"
        val token = "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" // Ganti dengan token akses Anda
        val headers = arrayOf(
            BasicHeader("Authorization", "Token $token"),
            BasicHeader("User-Agent", "YourAppName") // Ganti dengan nama aplikasi Anda
        )
        client.get(null, url, headers, null, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                // Jika koneksi berhasil
                binding.progressBarDetail.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(ContentValues.TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val login = responseObject.getString("login")
                    val name = responseObject.getString("name")
                    val email = responseObject.getString("email")
                    val location = responseObject.getString("location")
                    val company = responseObject.getString("company")
                    val blog = responseObject.getString("blog")
                    val bio = responseObject.getString("bio")
                    binding.usernamePengguna.text = login
                    binding.namaPengguna.text = name
                    binding.emailPengguna.text = email

                    Glide.with(this@DetailPenggunaActivity)
                        .load(responseObject.getString("avatar_url"))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(binding.fotoPengguna)

                    binding.lokasiPengguna.text = location
                    binding.perusahaanPengguna.text = company
                    binding.blogPengguna.text = blog
                    binding.biodataPengguna.text = bio

                } catch (e: Exception) {
                    Toast.makeText(this@DetailPenggunaActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                // Jika koneksi gagal
                binding.progressBarDetail.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@DetailPenggunaActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


}
