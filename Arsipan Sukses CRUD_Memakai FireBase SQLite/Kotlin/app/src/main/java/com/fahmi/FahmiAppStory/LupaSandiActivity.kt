package com.fahmi.FahmiAppStory

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.fahmi.FahmiAppStory.databinding.ActivityLupaSandiBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView

@SuppressLint("CheckResult")
class LupaSandiActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityLupaSandiBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaSandiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Logic AUTH
        auth = FirebaseAuth.getInstance()

        // VALIDASI EMAIL
        val emailStream = RxTextView.textChanges(binding.editTeksEmail)
            .skipInitialValue()
            .map { email -> !Patterns.EMAIL_ADDRESS.matcher(email).matches()  }
        emailStream.subscribe{
            showEmailValidAlert(it)
        }

        // LOGIC LUPA PASSWORD
        binding.tombolLupaSandi.setOnClickListener{
            val email = binding.editTeksEmail.text.toString().trim()

                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this){
                        reset -> if (reset.isSuccessful){
                            Intent(this, MasukActivity::class.java).also{
                                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(it)
                                Toast.makeText(this, "Periksa email untuk mengatur ulang kata sandi !", Toast.LENGTH_SHORT).show()

                            }
                    } else {
                        Toast.makeText(this, reset.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                    }
        }

        // AKTIVITAS SETELAH DI KLIK
        binding.kembaliMasuk.setOnClickListener{
            startActivity(Intent(this, MasukActivity::class.java))
        }

    }
    private fun showEmailValidAlert(isNotValid: Boolean){
        if (isNotValid){
            binding.apply {
                editTeksEmail.error = "Email tidak valid !"
                tombolLupaSandi.isEnabled = false
            }
            binding.tombolLupaSandi.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.darker_gray )

        }  else {
            binding.editTeksEmail.error = null
            binding.tombolLupaSandi.isEnabled = true
            binding.tombolLupaSandi.backgroundTintList = ContextCompat.getColorStateList(this, R.color.primary_color)

        }
    }
}