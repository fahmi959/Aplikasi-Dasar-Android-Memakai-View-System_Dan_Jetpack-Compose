package com.fahmi.fahmipundamentalandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmi.fahmipundamentalandroid.databinding.FragmentNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MengikutiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MengikutiFragment : Fragment(R.layout.fragment_news) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: ReviewAdapter
    private var position: Int = 0
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewsBinding.bind(view)
        adapter = ReviewAdapter(ArrayList())
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = adapter

        arguments?.let {
            position = it.getInt(PengikutFragment.ARG_POSITION)
            username = it.getString(PengikutFragment.ARG_USERNAME) ?: "fahmi959"
        }

        findFollowing(username)


        adapter.set_klik_detail(object : ReviewAdapter.KlikDetail {
            override fun Sudah_KlikDetail_Item_Rv(data: PenggunaGithub) {
                val intent = Intent(requireActivity(), DetailPenggunaGithubActivity::class.java)
                intent.putExtra(DetailPenggunaGithubActivity.TANGKAP_USERNAME, data.login)
                startActivity(intent)
            }
        })

    }

    private fun findFollowing(username: String) {
        showLoading(true)
        ApiConfig.getApiService().getFollowing(username)
            .enqueue(object : Callback<ArrayList<PenggunaGithub>> {
                override fun onResponse(
                    call: Call<ArrayList<PenggunaGithub>>,
                    response: Response<ArrayList<PenggunaGithub>>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        val followersList = response.body()
                        if (followersList != null) {
                            adapter.setDataFollowing(ArrayList(followersList))
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
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MengikutiFragment.
         */
        // TODO: Rename and change types and number of parameters

        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MengikutiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}