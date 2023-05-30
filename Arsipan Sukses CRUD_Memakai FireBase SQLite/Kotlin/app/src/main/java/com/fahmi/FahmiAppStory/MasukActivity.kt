package com.fahmi.FahmiAppStory

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.fahmi.FahmiAppStory.databinding.ActivityMasukBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import org.json.JSONObject

@SuppressLint("CheckResult")
class MasukActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMasukBinding

    // DEKLARASI VARIABEL FIREBASE
    private lateinit var auth:FirebaseAuth
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Logic Auth FIREBASE
        auth = FirebaseAuth.getInstance()

        // Validasi Username
        val usernameStream = RxTextView.textChanges(binding.editTeksEmail)
            .skipInitialValue()
            .map { username -> username.isEmpty() }
        usernameStream.subscribe{
            showTextMinimalAlert(it , "Email/Username")
        }

        // Validasi Password
        val sandiStream = RxTextView.textChanges(binding.editTeksSandi)
            .skipInitialValue()
            .map { sandi -> sandi.isEmpty() }
        sandiStream.subscribe{
            showTextMinimalAlert(it , "Sandi")
        }

        // Tombol Nyala Benar atau Salah
        val invalidFieldsStream = Observable.combineLatest(

            usernameStream,
            sandiStream,

            {usernameInvalid : Boolean, sandiInvalid: Boolean
                ->!usernameInvalid && !sandiInvalid
            })
        invalidFieldsStream.subscribe{ isValid ->
            if (isValid){
                binding.tombolMasuk.isEnabled = true
                binding.tombolMasuk.backgroundTintList = ContextCompat.getColorStateList(this, R.color.primary_color)
            } else{
                binding.tombolMasuk.isEnabled = false
                binding.tombolMasuk.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.darker_gray )
            }

            // MAINKAN ANIMASI (OPTIONAL)
            playAnimation()
        }


        // KLIK TOMBOL atau BUTTON
        binding.tombolMasuk.setOnClickListener{
            val email = binding.editTeksEmail.text.toString().trim()
            val sandi = binding.editTeksSandi.text.toString().trim()
        masukUser(email, sandi)


        }
        binding.tidakPunyaAkun.setOnClickListener{
            startActivity(Intent(this, DaftarActivity::class.java))
        }

        binding.lupaSandi.setOnClickListener{
            startActivity(Intent(this, LupaSandiActivity::class.java))
        }

        // MAINKAN ANIMASI (OPTIONAL)
        playAnimation()
    }

    private fun showTextMinimalAlert(isNotValid: Boolean , text: String){
        if (text== "Email/Username")
            binding.editTeksEmail.error = if (isNotValid) "$text tidak boleh kosong!" else null

        else if (text == "Password")
            binding.editTeksSandi.error = if (isNotValid) "$text tidak boleh kosong!" else null
    }

//    // FUNGSI Auth LOGIN FIREBASE
//    private fun masukUser(email: String, sandi: String){
//        auth.signInWithEmailAndPassword(email, sandi)
//            .addOnCompleteListener(this){
//                // SETELAH BERHASIL LOGIN (TUJUAN ACTIVITY) DIARAHKAN INTENT KE MANA
//                login -> if (login.isSuccessful){
//                    Intent(this, HomeNoteActivity::class.java).also {
//                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(it)
//                        Toast.makeText(this, "Masuk berhasil !", Toast.LENGTH_SHORT).show()
//                    }
//            } else {
//                Toast.makeText(this, login.exception?.message, Toast.LENGTH_SHORT).show()
//            }
//            }
//    }

    // FUNGSI Auth LOGIN API
    private fun masukUser(email: String, sandi: String) {
        val requestBody = JSONObject()
        requestBody.put("email", email)
        requestBody.put("password", sandi)
        val request = JsonObjectRequest(
            Request.Method.POST,
            "https://story-api.dicoding.dev/v1/login",
            requestBody,
            { response ->
                val error = response.getBoolean("error")
                if (!error) {
                    val loginResult = response.getJSONObject("loginResult")
                    val userId = loginResult.getString("userId")
                    val name = loginResult.getString("name")
                    token = loginResult.getString("token")

                    // SETELAH BERHASIL LOGIN (TUJUAN ACTIVITY) DIARAHKAN INTENT KE MANA
                    Intent(this, HomeNoteActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Toast.makeText(this, "Masuk berhasil !", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val message = response.getString("message")
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(request)
    }

    // FUNGSI UNTUK ANIMASI (OPTIONAL)
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.teksviewMasukSubJudul, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val judul = ObjectAnimator.ofFloat(binding.teksviewMasukJudul, View.ALPHA, 1f).setDuration(500)
        val subjudul = ObjectAnimator.ofFloat(binding.teksviewMasukSubJudul, View.ALPHA, 1f).setDuration(500)
        val v = ObjectAnimator.ofFloat(binding.inputEmail, View.ALPHA, 1f).setDuration(500)
        val w = ObjectAnimator.ofFloat(binding.editTeksEmail, View.ALPHA, 1f).setDuration(500)
        val x = ObjectAnimator.ofFloat(binding.inputSandi, View.ALPHA, 1f).setDuration(500)
        val y = ObjectAnimator.ofFloat(binding.editTeksSandi, View.ALPHA, 1f).setDuration(500)
        val z = ObjectAnimator.ofFloat(binding.tidakPunyaAkun, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.tombolMasuk, View.ALPHA, 1f).setDuration(500)
        val lupasandi = ObjectAnimator.ofFloat(binding.lupaSandi, View.ALPHA, 1f).setDuration(500)

        val together_bersama = AnimatorSet().apply {
            playTogether(login, lupasandi)
        }

        AnimatorSet().apply {
            playSequentially(judul,subjudul, v , w,x,y, together_bersama , z)
            start()
        }
    }
}