package com.fahmi.fahmipundamentalandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmi.fahmipundamentalandroid.databinding.ActivityListQuotesBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.message.BasicHeader
import org.json.JSONArray


class ListQuotesActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ListQuotesActivity"
    }

    private lateinit var binding: ActivityListQuotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "List of Quotes"
        val layoutManager = LinearLayoutManager(this)
        binding.rvListQuotes.layoutManager = layoutManager // Set layout manager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListQuotes.addItemDecoration(itemDecoration) // Set item decoration
        getListQuotes()


    }

    private fun getListQuotes() {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users" // URL untuk daftar pengguna di GitHub API
        val token = "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" // Ganti dengan token akses Anda
        val headers = arrayOf(
            BasicHeader("Authorization", "Token $token"),
            BasicHeader("User-Agent", "YourAppName") // Ganti dengan nama aplikasi Anda
        )
        client.get(null, url, headers, null, object : AsyncHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                // Jika koneksi berhasil
                binding.progressBar.visibility = View.INVISIBLE

                val listUsers = ArrayList<ParsingPengguna>() // Menggunakan model Quote sebagai objek dalam ArrayList
                val result = responseBody?.let { String(it) }
                if (result != null) {
                    Log.d(TAG, result)
                }
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login") // Mengambil username pengguna
                        val url_usernya = jsonObject.getString("url") // Mengambil username pengguna
                        val avatarUrl = jsonObject.getString("avatar_url") // Mengambil URL avatar
                        val pengguna = ParsingPengguna(username, url_usernya, avatarUrl) // Membuat objek Quote dengan username dan avatarUrl
                        listUsers.add(pengguna) // Menambahkan objek Quote ke dalam ArrayList
                    }
                    val adapter = PenggunaAdapter(listUsers) // Menggunakan adapter untuk menampilkan daftar pengguna
                    binding.rvListQuotes.adapter = adapter // Set adapter ke RecyclerView

                } catch (e: Exception) {
                    Toast.makeText(this@ListQuotesActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                // Jika koneksi gagal
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@ListQuotesActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }



}