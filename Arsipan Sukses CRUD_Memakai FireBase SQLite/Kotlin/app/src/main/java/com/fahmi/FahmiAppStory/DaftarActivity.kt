package com.fahmi.FahmiAppStory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fahmi.FahmiAppStory.databinding.ActivityDaftarBinding
import com.google.gson.JsonObject
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.*

@SuppressLint("CheckResult")
//class DaftarActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityDaftarBinding
//
//    // DEKLARASI VARIABEL FIREBASE
//    private lateinit var auth: FirebaseAuth
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDaftarBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Logic Auth FIREBASE
//        auth = FirebaseAuth.getInstance()
//
//
//        // Validasi Nama Lengkap
//        val nama_lengkapStream = RxTextView.textChanges(binding.editTeksNamalengkap)
//            .skipInitialValue()
//            .map { nama_lengkap -> nama_lengkap.isEmpty() }
//        nama_lengkapStream.subscribe {
//            showNameExistAler(it)
//        }
//
//        // Validasi Email
//        val emailStream = RxTextView.textChanges(binding.editTeksEmail)
//            .skipInitialValue()
//            .map { email -> !Patterns.EMAIL_ADDRESS.matcher(email).matches() }
//        emailStream.subscribe {
//            showEmailValidAlert(it)
//        }
//
//        // Validasi Username
//        val usernameStream = RxTextView.textChanges(binding.editTeksUsername)
//            .skipInitialValue()
//            .map { username -> username.length < 5 }
//        usernameStream.subscribe {
//            showTextMinimalAlert(it, "Username")
//        }
//
//        // Validasi Password
//        val sandiStream = RxTextView.textChanges(binding.editTeksSandi)
//            .skipInitialValue()
//            .map { sandi -> sandi.length < 6 }
//        sandiStream.subscribe {
//            showTextMinimalAlert(it, "Sandi")
//        }
//
//        // Validasi Ulang Sandi atau Re-Password
//        val ulangsandiStream = Observable.merge(
//            RxTextView.textChanges(binding.editTeksSandi)
//                .skipInitialValue()
//                .map { sandi ->
//                    sandi.toString() != binding.editTeksUlangsandi.text.toString()
//                },
//            RxTextView.textChanges(binding.editTeksUlangsandi)
//                .skipInitialValue()
//                .map { ulangsandi ->
//                    ulangsandi.toString() != binding.editTeksSandi.text.toString()
//                })
//        ulangsandiStream.subscribe {
//            showPasswordConfirmAlert(it)
//        }
//
//        // Tombol Nyala Benar atau Salah
//        val invalidFieldsStream = Observable.combineLatest(
//            nama_lengkapStream,
//            emailStream,
//            usernameStream,
//            sandiStream,
//            ulangsandiStream,
//            {
//                    nama_lengkapInvalid: Boolean, emailInvalid: Boolean, usernameInvalid: Boolean, sandiInvalid: Boolean, ulangsandiInvalid: Boolean,
//                ->
//                !nama_lengkapInvalid && !emailInvalid && !usernameInvalid && !sandiInvalid && !ulangsandiInvalid
//            })
//        invalidFieldsStream.subscribe { isValid ->
//            if (isValid) {
//                binding.tombolDaftar.isEnabled = true
//                binding.tombolDaftar.backgroundTintList =
//                    ContextCompat.getColorStateList(this, R.color.primary_color)
//            } else {
//                binding.tombolDaftar.isEnabled = false
//                binding.tombolDaftar.backgroundTintList =
//                    ContextCompat.getColorStateList(this, android.R.color.darker_gray)
//            }
//        }
//
//        // KLIK BUTTON ATAU TOMBOL
//        binding.tombolDaftar.setOnClickListener {
//            val email = binding.editTeksEmail.text.toString().trim()
//            val sandi = binding.editTeksSandi.text.toString().trim()
//
//            daftarPengguna(email, sandi)
//        }
//        binding.punyaAkun.setOnClickListener {
//            startActivity(Intent(this, MasukActivity::class.java))
//        }
//    }
//
//    private fun showNameExistAler(isNotValid: Boolean) {
//        binding.editTeksNamalengkap.error = if (isNotValid) "Nama tidak boleh kosong!" else null
//
//    }
//
//    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
//        if (text == "Username")
//            binding.editTeksUsername.error =
//                if (isNotValid) "$text harus lebih dari 4 karakter" else null
//
//        if (text == "Sandi")
//            binding.editTeksSandi.error = if (isNotValid) "$text minimal 6 karakter" else null
//    }
//
//    private fun showEmailValidAlert(isNotValid: Boolean) {
//        binding.editTeksEmail.error = if (isNotValid) "Email tidak valid" else null
//    }
//
//    private fun showPasswordConfirmAlert(isNotValid: Boolean) {
//        binding.editTeksUlangsandi.error = if (isNotValid) "Sandi tidak sesuai" else null
//    }
//
//
//    // FUNGSI REGISTER AUTH FIREBASE
//    private fun daftarPengguna(email: String, sandi: String) {
//        auth.createUserWithEmailAndPassword(email, sandi)
//            .addOnCompleteListener(this) {
//                if (it.isSuccessful) {
//                    startActivity(Intent(this, MasukActivity::class.java))
//                    Toast.makeText(this, "Daftar pengguna berhasil", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//}

class DaftarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Validasi Nama Lengkap
        val nama_lengkapStream = RxTextView.textChanges(binding.editTeksNamalengkap)
            .skipInitialValue()
            .map { nama_lengkap -> nama_lengkap.isEmpty() }
        nama_lengkapStream.subscribe {
            showNameExistAler(it)
        }

        // Validasi Email
        val emailStream = RxTextView.textChanges(binding.editTeksEmail)
            .skipInitialValue()
            .map { email -> !Patterns.EMAIL_ADDRESS.matcher(email).matches() }
        emailStream.subscribe {
            showEmailValidAlert(it)
        }

        // Validasi Username
        val usernameStream = RxTextView.textChanges(binding.editTeksUsername)
            .skipInitialValue()
            .map { username -> username.length < 4 }
        usernameStream.subscribe {
            showTextMinimalAlert(it, "Username")
        }

        // Validasi Password
        val sandiStream = RxTextView.textChanges(binding.editTeksSandi)
            .skipInitialValue()
            .map { sandi -> sandi.length < 8 }
        sandiStream.subscribe {
            showTextMinimalAlert(it, "Sandi")
        }

        // Validasi Ulang Sandi atau Re-Password
        val ulangsandiStream = Observable.merge(
            RxTextView.textChanges(binding.editTeksSandi)
                .skipInitialValue()
                .map { sandi ->
                    sandi.toString() != binding.editTeksUlangsandi.text.toString()
                },
            RxTextView.textChanges(binding.editTeksUlangsandi)
                .skipInitialValue()
                .map { ulangsandi ->
                    ulangsandi.toString() != binding.editTeksSandi.text.toString()
                })
        ulangsandiStream.subscribe {
            showPasswordConfirmAlert(it)
        }

        // Tombol Nyala Benar atau Salah
        val invalidFieldsStream = Observable.combineLatest(
            nama_lengkapStream,
            emailStream,
            usernameStream,
            sandiStream,
            ulangsandiStream,
            {
                    nama_lengkapInvalid: Boolean, emailInvalid: Boolean, usernameInvalid: Boolean, sandiInvalid: Boolean, ulangsandiInvalid: Boolean,
                ->
                !nama_lengkapInvalid && !emailInvalid && !usernameInvalid && !sandiInvalid && !ulangsandiInvalid
            })
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                binding.tombolDaftar.isEnabled = true
                binding.tombolDaftar.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.primary_color)
            } else {
                binding.tombolDaftar.isEnabled = false
                binding.tombolDaftar.backgroundTintList =
                    ContextCompat.getColorStateList(this, android.R.color.darker_gray)
            }
        }

        // KLIK BUTTON ATAU TOMBOL
        binding.tombolDaftar.setOnClickListener {
            val name = binding.editTeksNamalengkap.text.toString().trim()
            val email = binding.editTeksEmail.text.toString().trim()
            val sandi = binding.editTeksSandi.text.toString().trim()
            daftarPengguna(name, email, sandi)
        }

        binding.punyaAkun.setOnClickListener {
            startActivity(Intent(this, MasukActivity::class.java))
        }
    }

    private fun showNameExistAler(isNotValid: Boolean) {
        binding.editTeksNamalengkap.error = if (isNotValid) "Nama tidak boleh kosong!" else null

    }

    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
        if (text == "Username")
            binding.editTeksUsername.error =
                if (isNotValid) "$text harus lebih dari 3 karakter" else null

        if (text == "Sandi")
            binding.editTeksSandi.error = if (isNotValid) "$text minimal 8 karakter" else null
    }

    private fun showEmailValidAlert(isNotValid: Boolean) {
        binding.editTeksEmail.error = if (isNotValid) "Email tidak valid" else null
    }

    private fun showPasswordConfirmAlert(isNotValid: Boolean) {
        binding.editTeksUlangsandi.error = if (isNotValid) "Sandi tidak sesuai" else null
    }

    // FUNGSI REGISTER DENGAN API
    fun daftarPengguna(name: String, email: String, password: String) {
        val nameBody = name.toRequestBody("text/plain".toMediaType())
        val emailBody = email.toRequestBody("text/plain".toMediaType())
        val passwordBody = password.toRequestBody("text/plain".toMediaType())

        val service = ApiConfig().getApiService().registerPengguna(nameBody, emailBody, passwordBody)
        service.enqueue(object : Callback<RegisterPengguna> {
            override fun onResponse(
                call: Call<RegisterPengguna>,
                response: Response<RegisterPengguna>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        startActivity(Intent(this@DaftarActivity, MasukActivity::class.java))
                        Toast.makeText(this@DaftarActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@DaftarActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RegisterPengguna>, t: Throwable) {
                Toast.makeText(this@DaftarActivity, "Gagal instance Retrofit", Toast.LENGTH_SHORT).show()
            }
        })
    }


}