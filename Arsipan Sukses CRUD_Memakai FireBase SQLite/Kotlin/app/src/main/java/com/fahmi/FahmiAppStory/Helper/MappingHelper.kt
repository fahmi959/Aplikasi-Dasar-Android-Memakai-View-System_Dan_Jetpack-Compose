package com.fahmi.FahmiAppStory.Helper

import android.database.Cursor
import com.fahmi.FahmiAppStory.DB_SQLite.DatabaseContract
import com.fahmi.FahmiAppStory.Note


//Setelah ini kita akan mengambil data dari method queryAll() yang ada di NoteHelper.
// Namun, karena nanti di adapter kita akan menggunakan arraylist, sedangkan di sini objek yang di kembalikan berupa Cursor,
// maka dari itu kita harus mengonversi dari Cursor ke Arraylist. Kita akan membuat kelas pembantu untuk menangani hal ini.

class MappingHelper {

    object MappingHelper {

        fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Note> {
            val notesList = ArrayList<Note>()
            notesCursor?.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                    val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
                    val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
                    val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
                    val nama = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.NAMA))
                    val model_foto_url = getBlob(getColumnIndexOrThrow(DatabaseContract.NoteColumns.MODEL_FOTO_URL))
                    notesList.add(Note(id, title, description, date, nama, model_foto_url))
                }
            }
            return notesList
        }
    }
}