package com.fahmi.FahmiAppStory

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fahmi.FahmiAppStory.DB_SQLite.DatabaseContract
import com.fahmi.FahmiAppStory.DB_SQLite.DatabaseContract.NoteColumns.Companion.DATE
import com.fahmi.FahmiAppStory.DB_SQLite.NoteHelper
import com.fahmi.FahmiAppStory.databinding.ActivityNoteAddUpdateBinding
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class NoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {

    // DEKLARASI VARIABEL UMUM
    private var isEdit = false
    private var note: Note? = null
    private var position: Int = 0
    private lateinit var noteHelper: NoteHelper
    private lateinit var binding: ActivityNoteAddUpdateBinding
    // Deklarasi konstanta untuk kode permintaan pemilihan gambar
    private val PICK_IMAGE_REQUEST = 1

    companion object {
        // DEKLARASI VARIABEL OBJEK
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    // MENGIMPLEMENTASIKAN ONCREATE MULAI DARI PANGGIL INSTANCE HINGGA DATA HASIL DARI INTENT.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        noteHelper = NoteHelper.getInstance(applicationContext)
        noteHelper.open()
        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = Note()
        }
        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            note?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
                binding.edtNama.setText(it.nama)
                Glide.with(this)
                    .load(it.model_foto_url)
                    .into(binding.gambarFotoPadaLayout)
            }
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle

        // TOMBOL SUBMIT KETIKA DI KLIK
        binding.btnSubmit.setOnClickListener(this)

        // TOMBOL AKSI KAMERA
        binding.tombolKamera.setOnClickListener {
            startActivity(Intent(this, MainKameraActivity::class.java))
        }


// Mengatur OnClickListener untuk tombol "Pilih Gambar"
        binding.btnPilihGambar.setOnClickListener {
         startGallery()
        }
    }



    // Fungsi untuk memilih gambar dari galeri
    private fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
        Toast.makeText(this, "Berhasil Membuka Gallery", Toast.LENGTH_SHORT).show()
    }


    //    digunakan untuk mengatur dan menangani aktivitas galeri,
//    serta mengambil gambar yang dipilih dari galeri dan menyimpannya ke dalam objek note dalam bentuk byte array.
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri? = result.data?.data
            selectedImg?.let {

                // Meluncurkan fungsi getImageByteArrayFromUri() sebagai coroutine
                lifecycleScope.launch {

                    // Mengambil byte array dari Uri menggunakan fungsi getImageByteArrayFromUri()
                    val byteArray = getImageByteArrayFromUri(it)

                    // Mengompres byte array gambar
                    val compressedByteArray = reduceByteArrayImage(byteArray)

                    // Menyimpan byte array yang sudah dikompres ke dalam objek note atau SQLite
                    note?.model_foto_url = compressedByteArray

                    // Menggunakan Glide untuk menampilkan gambar ke ImageView
                    Glide.with(this@NoteAddUpdateActivity)
                        .asBitmap()
                        .load(it)
                        .apply(RequestOptions().fitCenter()) // Menggunakan fitCenter untuk menyesuaikan ukuran gambar
                        .into(binding.gambarFotoPadaLayout)

                }
            }
        }
    }

    private suspend fun getImageByteArrayFromUri(imageUri: Uri): ByteArray = withContext(Dispatchers.IO) {
        val contentResolver = contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)
        inputStream?.readBytes() ?: ByteArray(0)
    }


//    // Override onActivityResult untuk menangani hasil pemilihan gambar
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
//            // Mendapatkan URI gambar dari Intent
//            val imageUri = data.data
//
//            // Mendapatkan byte array dari URI gambar
//            lifecycleScope.launch {
//
//
//                // Mengubah byte array menjadi Bitmap
//                val byteArray = imageUri?.let { getImageByteArrayFromUri(it) }
//                val bitmap = byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, it.size) }
//
//                // Menggunakan Glide untuk mengurangi ukuran file gambar sebelum ditampilkan di ImageView
//                Glide.with(this@NoteAddUpdateActivity)
//                    .asBitmap()
//                    .load(imageUri)
//                    .apply(RequestOptions().fitCenter()) // Menggunakan fitCenter untuk menyesuaikan ukuran gambar
//                    .into(binding.gambarFotoPadaLayout)
//
//                // Menyimpan byte array ke dalam objek note
//                note?.model_foto_url = byteArray
//
//                // Mengurangi ukuran file gambar
//                val file = File(imageUri?.path)
//                val reducedFile = reduceFileImage(file)
//
//
//                // Kode untuk mengupload gambar ke server
//                // uploadImage()
//
//
//            }
//        }
//    }



    // UNTUK MENGURANGI MELALUI UTILS UKURAN FILE GAMBAR AGAR TIDAK > 1 MB , DAN MENCEGAH ERROR
//     private fun reduceFileImage(file: File): File {
//        return file
//    }
    private fun reduceByteArrayImage(byteArray: ByteArray): ByteArray {
        val maxFileSize = 1 * 300 * 1024 // Batasan ukuran file dalam byte (1 MB = 1 * 1024 * 1024 bytes)

        // Cek ukuran byte array
        if (byteArray.size <= maxFileSize) {
            // Jika ukuran byte array sudah sesuai, langsung kembalikan byteArray
            return byteArray
        } else {
            // Jika ukuran byte array melebihi batasan, lakukan proses kompresi
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            var compressQuality = 100
            var streamLength: Int
            lateinit var bmpStream: ByteArrayOutputStream
            do {
                bmpStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
                val bmpPicByteArray = bmpStream.toByteArray()
                streamLength = bmpPicByteArray.size
                compressQuality -= 5
            } while (streamLength > maxFileSize)

            return bmpStream.toByteArray()
        }
    }


        // AKSI TOMBOL DI KLIK SERTA MENGEMBALIKAN NILAI DENGAN MENGGUNAKAN setResult.
    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            val nama = binding.edtNama.text.toString().trim()
            val model_foto_url = note?.model_foto_url
            // VALIDASI KOLOM ISIAN JUDUL
            if (title.isEmpty()) {
                binding.edtTitle.error = "Judul tidak boleh kosong"
                return
            }
            // VALIDASI KOLOM ISIAN NAMA
            if (nama.isEmpty()) {
                binding.edtNama.error = "Nama Belum Diisi"
                return
            }
            // VALIDASI UNTUK MEMERIKSA FOTO ATAU GAMBAR PADA IMAGE VIEW KALAU TERJADI KEKOSONGAN
            if (model_foto_url == null || model_foto_url.isEmpty()) {
                // Mengganti sumber gambar dengan gambar placeholder atau ikon error
                binding.gambarFotoPadaLayout.setImageResource(R.drawable.ic_place_holder) // Menggunakan gambar placeholder sebagai contoh
                Toast.makeText(this@NoteAddUpdateActivity, "Foto belum diisi atau Kosong", Toast.LENGTH_SHORT).show()
//                return
            }

            note?.title = title
            note?.description = description
            note?.nama = nama
            note?.model_foto_url = model_foto_url
            val intent = Intent()
            intent.putExtra(EXTRA_NOTE, note)
            intent.putExtra(EXTRA_POSITION, position)

            val values = ContentValues()
            values.put(DatabaseContract.NoteColumns.TITLE, title)
            values.put(DatabaseContract.NoteColumns.DESCRIPTION, description)
            values.put(DatabaseContract.NoteColumns.NAMA, nama)
            values.put(DatabaseContract.NoteColumns.MODEL_FOTO_URL, model_foto_url)

            if (isEdit) {
                val result = noteHelper.update(note?.id.toString(), values).toLong()
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    Toast.makeText(this@NoteAddUpdateActivity, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                }
            } else {
                note?.date = getCurrentDate()
                values.put(DATE, getCurrentDate())
                val result = noteHelper.insert(values)
                if (result > 0) {
                    note?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    Toast.makeText(this@NoteAddUpdateActivity, "Gagal menambah data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    // METODE untuk getCurrentDate()
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    // UNTUK MEMANGGIL menu_form.xml YANG ADA DI RES FOLDER DIRECTORY MENU
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    // FUNGSI KETIKA MENU NYA DI KLIK
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    // PEMANGGILAN METODE ALERT NYA SAAT MENU DIATAS DI KLIK MAKA MUNCUL DIALOG
    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    // MEMUNCULKAN DIALOGNYA DAN MENGEMBALIKAN NILAI RESULT UNTUK DITERIMA PADA ACTIVITY NANTINYA .
    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Note"
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    val result = noteHelper.deleteById(note?.id.toString()).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else {
                        Toast.makeText(this@NoteAddUpdateActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }






}


// catatan khusus
// Tanggung jawab utama NoteAddUpdateActivity adalah sebagai berikut:
// Menyediakan form untuk melakukan proses input data.
// Menyediakan form untuk melakukan proses pembaruan data.
// Jika pengguna berada pada proses pembaruan data maka setiap kolom pada form sudah terisi otomatis dan ikon untuk hapus yang berada pada sudut kanan atas ActionBar ditampilkan dan berfungsi untuk menghapus data.
// Sebelum proses penghapusan data, dialog konfirmasi akan tampil. Pengguna akan ditanya terkait penghapusan yang akan dilakukan.
// Jika pengguna menekan tombol back (kembali) baik pada ActionBar maupun peranti, maka akan tampil dialog konfirmasi sebelum menutup halaman.
// MATERI INI BERASAL DARI ANDROID FUNDAMENTAL DICODING FAHMI ARDIANSYAH. BAGIAN LATIHAN SQLITE . BULAN PUASA DI SEMARANG - FAHMI ARDIANSYAH 2023
// Masih ingat materi di mana sebuah Activity menjalankan Activity lain dan menerima nilai balik pada metode registerForActivityResult()? Tepatnya di Activity yang dijalankan dan ditutup dengan menggunakan parameter RESULTCODE. Jika Anda lupa, baca kembali modul 1 tentang Intent ya!