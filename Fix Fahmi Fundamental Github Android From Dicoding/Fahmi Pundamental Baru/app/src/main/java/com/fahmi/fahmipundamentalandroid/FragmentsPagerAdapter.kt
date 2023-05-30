package com.fahmi.fahmipundamentalandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
class FragmentsPagerAdapter(activity: AppCompatActivity, private var username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = PengikutFragment()
                val args = Bundle().apply {
                    putInt(PengikutFragment.ARG_POSITION, position + 1)
                    putString(PengikutFragment.ARG_USERNAME, username)
                }
                fragment.arguments = args
            }
            1 -> {
                fragment = MengikutiFragment()
                val args = Bundle().apply {
                    putInt(MengikutiFragment.ARG_POSITION, position + 1)
                    putString(MengikutiFragment.ARG_USERNAME, username)
                }
                fragment.arguments = args
            }
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}