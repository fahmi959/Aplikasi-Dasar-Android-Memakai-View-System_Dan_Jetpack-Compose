package com.fahmi.fahmipundamentalandroid

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmi.fahmipundamentalandroid.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.widget.SearchView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    companion object {
        const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
        private lateinit var adapter: ReviewAdapter

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MEMPERTAHANKAN THEMA

        SettinganActivity.ThemeUtils.applyTheme(this)


//        supportActionBar?.hide()  // MENYEMBUNYIKAN ACTION BAR

        val layoutManager = LinearLayoutManager(this)
        binding.rvHeroes.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvHeroes.addItemDecoration(itemDecoration)


        // LOGIKA KLIK
        binding.btnAllQuotes.setOnClickListener {
            val intent = Intent(this, ListQuotesActivity::class.java)
            startActivity(intent)
        }

        // LOGIKA KLIK
        binding.tombolBiasa.setOnClickListener {
            startActivity(Intent(this, DetailPenggunaActivity::class.java))
        }



        // LOGIKA FUNGSI PENCARIAN
        findRestaurant(query = "fahmi959") // fahmi959 adalah DEFFAULT SAAT MAIN ACTIVITY MUNCUL

            // KLIK ITEM RECYCLE VIEW
        adapter = ReviewAdapter(ArrayList())
        binding.rvHeroes.adapter = adapter

        adapter.set_klik_detail(object : ReviewAdapter.KlikDetail {
            override fun Sudah_KlikDetail_Item_Rv(data: PenggunaGithub) {
                Intent(this@MainActivity, DetailPenggunaGithubActivity::class.java).also {
                    it.putExtra(DetailPenggunaGithubActivity.TANGKAP_USERNAME ,data.login)
                    it.putExtra(DetailPenggunaGithubActivity.TANGKAP_ID ,data.id)
                    it.putExtra(DetailPenggunaGithubActivity.TANGKAP_AvatarURL ,data.avatarUrl)
                    it.putExtra(DetailPenggunaGithubActivity.TANGKAP_HtmlURL ,data.htmlUrl)
                    startActivity(it)
                }
            }
        })




    }
    

    private fun findRestaurant(query: String) {
        showLoading(true)
        ApiConfig.getApiService().getSearchUsers(query)
            .enqueue(object : Callback<GithubResponse> {
                override fun onResponse(
                    call: Call<GithubResponse>,
                    response: Response<GithubResponse>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            adapter.setReviewUsers(responseBody.items)
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                    showLoading(false)
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarMain.visibility = if (isLoading) View.VISIBLE else View.GONE
    }







    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.cari_pengguna).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.pencarian)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /*
            Gunakan method ini ketika search selesai atau OK
             */
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                findRestaurant(query)
                return true
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cari_pengguna -> {
                //masukan SEARCH VIEW
                return true
            }
            R.id.penampung_favorite -> {
                val o = Intent(this, TampunganFavoriteActivity::class.java)
                startActivity(o)
                return true
            }
            R.id.settingan -> {
                val i = Intent(this, SettinganActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }





}