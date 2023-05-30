package com.fahmi.FahmiAppStory

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmi.FahmiAppStory.Adapter.NoteAdapter
import com.fahmi.FahmiAppStory.DB_SQLite.NoteHelper
import com.fahmi.FahmiAppStory.Helper.MappingHelper
import com.fahmi.FahmiAppStory.databinding.ActivityHomeNoteBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeNoteBinding
    private lateinit var adapter: NoteAdapter


    // karena kita sudah membuat halaman NoteAddUpdateActivity untuk menambah dan meng-edit data dengan Intent Result.
// Di MainActivity kita akan memanggil activity tersebut dan mendapatkan nilai result darinya.
    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            // Akan dipanggil jika request codenya ADD
            when (result.resultCode) {
                NoteAddUpdateActivity.RESULT_ADD -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note

                    // KODE DATA URUTAN PALING BAWAH
//                    adapter.addItem(note)  // Menambahkan data ke adapter RecyclerView
//                    binding.rvNotes.smoothScrollToPosition(adapter.itemCount - 1) // Mengurutkan data setelah ditambah
//                    showSnackbarMessage("Satu item berhasil ditambahkan")

                    // UNTUK RECYCVIEW TAMPIL DI PALING ATAS SETELAH TAMBAH DATA UBAH SCROLL POSISION KE 0 DARI KODE MATI SEBELUMNYA
                    // KODE DATA URUTAN PALING ATAS
                    adapter.addItem(note) // Menambahkan data ke adapter RecyclerView
                    adapter.notifyDataSetChanged() // pastikan adapter diupdate sebelum mengatur urutan data
                    binding.rvNotes.scrollToPosition(0) // scroll ke posisi 0 (paling atas)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }

                NoteAddUpdateActivity.RESULT_UPDATE -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                    val position = result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, note)
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
                NoteAddUpdateActivity.RESULT_DELETE -> {
                    val position = result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                }
            }
        }
    }

    // UNTUK MENJAGA DATA TETAP AMAN SAAT DI ROTASI
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // SETTING KEGUNAAN RECYCLE VIEW NYA
        supportActionBar?.title = "Notes"
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)

        // ADAPTER dari NoteAdapter
        adapter = NoteAdapter(object : NoteAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedNote: Note?, position: Int?) {
                val intent = Intent(this@HomeNoteActivity, NoteAddUpdateActivity::class.java)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, selectedNote)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })

        binding.rvNotes.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@HomeNoteActivity, NoteAddUpdateActivity::class.java)
            resultLauncher.launch(intent)
        }
        // proses ambil data
        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotes = list
            }
        }
    }

    //mengambil data dari database dengan menggunakan background thread dan memanfaatkan NoteHelper yang sudah kita buat.
    // Sekarang di MainActivity mari kita inisialisasi terlebih dahulu dan panggil metode open()
    // untuk memulai interaksi database dan close() saat Activity ditutup supaya tidak terjadi memory leak.
    private fun loadNotesAsync() {
        lifecycleScope.launch {
            binding.progressbar.visibility = View.VISIBLE
            val noteHelper = NoteHelper.getInstance(applicationContext)
            noteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listNotes = notes
            } else {
                adapter.listNotes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
            noteHelper.close()
        }
    }

    // UNTUK MENJAGA DATA TETAP AMAN SAAT DI ROTASI
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }




}