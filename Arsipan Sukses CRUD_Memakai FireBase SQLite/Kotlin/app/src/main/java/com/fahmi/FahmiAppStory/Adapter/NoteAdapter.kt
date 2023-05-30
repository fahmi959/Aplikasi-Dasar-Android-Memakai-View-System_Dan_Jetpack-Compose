package com.fahmi.FahmiAppStory.Adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahmi.FahmiAppStory.DetailActivity
import com.fahmi.FahmiAppStory.Note
import com.fahmi.FahmiAppStory.R
import com.fahmi.FahmiAppStory.databinding.ActivityHomeNoteBinding
import com.fahmi.FahmiAppStory.databinding.ItemNoteBinding
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class NoteAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    // Deklarasi variabel listNotes sebagai ArrayList<Note> sebagai properti kelas
    var listNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])

        // TOMBOL BUTTON LIHAT DETAIL JIKA DI KLIK MAKA MUNCUL DETAIL
        holder.itemView.findViewById<Button>(R.id.tombolUntukLihatDetail).setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_note", listNotes[position])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = this.listNotes.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)

        fun bind(note: Note) {
            binding.apply {
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                nama.text = note.nama


                // Mengambil foto dari SQLite
                val fotoByteArray = note.model_foto_url // Menggantikan dengan cara Anda mengambil byte array foto dari SQLite

                if (fotoByteArray != null && fotoByteArray.isNotEmpty()) {
                    // Mengurangi ukuran byte array gambar
                    val compressedByteArray = reduceByteArrayImage(fotoByteArray)

                    // Mendekode byte array menjadi Bitmap
                    val bitmap = BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.size)

                    // Mengubah Bitmap menjadi Drawable
                    val drawable = BitmapDrawable(itemView.resources, bitmap)

                    // Memuat Drawable ke dalam ImageView menggunakan Glide
                    Glide.with(itemView)
                        .load(drawable)
                        .into(modelFotoUrl)


                } else {
                    // Handle jika foto tidak ada atau ukuran foto tidak valid
                    // Misalnya, bisa menampilkan placeholder atau menghilangkan ImageView
                    modelFotoUrl.setImageDrawable(null)
                }

                cvItemNote.setOnClickListener {
                    onItemClickCallback.onItemClicked(note, adapterPosition)
                }
            }
        }
    }

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

    interface OnItemClickCallback {
        fun onItemClicked(selectedNote: Note?, position: Int?)
    }

    // FUNGSI METODE MENAMBAHKAN MENGUBAH DAN MENGHAPUS ITEM CATATAN (CRUD) DI RECYCLE VIEW

    // KODE DATA URUTAN PALING BAWAH
//    fun addItem(note: Note) {
//        this.listNotes.add(note)
//        notifyItemInserted(this.listNotes.size - 1)
//    }

    // KODE DATA URUTAN PALING ATAS
    fun addItem(note: Note) {
        this.listNotes.add(0, note) // Menambahkan data di awal list
        notifyItemInserted(0) // Memberitahu adapter bahwa item baru telah ditambahkan di posisi 0
    }
    fun updateItem(position: Int, note: Note) {
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }
    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }


}