package com.fahmi.fahmipundamentalandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmi.fahmipundamentalandroid.databinding.ListPenggunaFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PengikutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PengikutFragment : Fragment(R.layout.list_pengguna_fragment) {
    private lateinit var adapter: ReviewAdapter
    private lateinit var binding: ListPenggunaFragmentBinding
//    private lateinit var adapter: ReviewAdapter
    private var position: Int = 0
    private var username: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ListPenggunaFragmentBinding.bind(view)
        adapter = ReviewAdapter(ArrayList())
        binding.rvFragmentUser.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFragmentUser.adapter = adapter

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: "fahmi959"
        }

      findFollowers(username)


        adapter.set_klik_detail(object : ReviewAdapter.KlikDetail {
            override fun Sudah_KlikDetail_Item_Rv(data: PenggunaGithub) {
                val intent = Intent(requireActivity(), DetailPenggunaGithubActivity::class.java)
                intent.putExtra(DetailPenggunaGithubActivity.TANGKAP_USERNAME, data.login)
                startActivity(intent)
            }
        })
    }


    private fun findFollowers(username: String) {
        showLoading(true)
        ApiConfig.getApiService().getFollowers(username)
            .enqueue(object : Callback<ArrayList<PenggunaGithub>> {
                override fun onResponse(
                    call: Call<ArrayList<PenggunaGithub>>,
                    response: Response<ArrayList<PenggunaGithub>>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val followersList = response.body()
                        if (followersList != null) {
                            adapter.setDataFollowers(ArrayList(followersList))
                        }
                    } else {
                        Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ArrayList<PenggunaGithub>>, t: Throwable) {
                    showLoading(false)
                    Log.e(MainActivity.TAG, "onFailure: ${t.message}")
                }
            })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFahmi.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}