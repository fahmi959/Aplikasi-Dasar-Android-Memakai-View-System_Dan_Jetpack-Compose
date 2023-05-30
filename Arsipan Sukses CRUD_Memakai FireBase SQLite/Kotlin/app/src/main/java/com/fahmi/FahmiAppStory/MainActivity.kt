package com.fahmi.FahmiAppStory

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentResolver
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.fahmi.FahmiAppStory.databinding.ActivityMainBinding
import com.google.android.material.animation.AnimatorSetCompat.playTogether
import com.google.firebase.auth.FirebaseAuth
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnMasuk.setOnClickListener{
            startActivity(Intent(this, MasukActivity::class.java))
        }

        binding.btnDaftar.setOnClickListener{
            startActivity(Intent(this, DaftarActivity::class.java))
        }

        // MAINKAN ANIMASI
        playAnimation()

    }

    // FUNGSI UNTUK ANIMASI
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.gifFahmiz, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.teksviewMulaiJudul, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.teksviewMulaiSubJudul, View.ALPHA, 1f).setDuration(500)
        val signupz = ObjectAnimator.ofFloat(binding.gifFahmiz, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.btnMasuk, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.btnDaftar, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(title, desc)
        }

        AnimatorSet().apply {
            playSequentially(login, signup, signupz, together)
            start()
        }
    }


    // JIKA KELUAR APLIKASI LALU KEMBALI KE APLIKASI MAKA INI ADALAH ACTIVITY YANG AKAN DITAMPILKAN
    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this, HomeFahmiActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }




}