package com.fahmi.FahmiAppStory

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fahmi.FahmiAppStory.databinding.ActivityHomeFahmiBinding
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding : ActivityHomeFahmiBinding

private lateinit var auth: FirebaseAuth

class HomeFahmiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeFahmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tombolLogout.setOnClickListener{
          //  // Logout dengan Firebase
//           auth.signOut()

            // Hapus session lokal
            val sharedPref = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()

            Intent(this, MasukActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(this, "Keluar berhasil !", Toast.LENGTH_SHORT).show()
            }







        }
    }
}