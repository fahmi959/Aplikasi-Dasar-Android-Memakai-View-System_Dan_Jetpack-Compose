package com.fahmi.FahmiAppStory.DB_SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.fahmi.FahmiAppStory.DB_SQLite.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.fahmi.FahmiAppStory.DB_SQLite.DatabaseContract.NoteColumns.Companion._ID

class NoteHelper(context: Context) {

    // AKOMODASI KEBUTUHAN DML
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    lateinit var database: SQLiteDatabase

    companion object {
        // VARIABEL KONSTRUKTOR
        private const val DATABASE_TABLE = TABLE_NAME


        // INISIASI DATABASE
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }



    }


    // MEMBUKA DAN MENUTUP AKSES KONEKSI DATABASE
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    // CRUD MENGAMBIL DATA
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    // CRUD - MENGAMBIL DATA DENGAN ID TERTENTU
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    // CRUD _ MENYIMPAN DATA
        fun insert(values: ContentValues?): Long {
            return database.insert(DATABASE_TABLE, null, values)
        }

    // CRUD ~ METODE MEMPERBARUI DATA
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }


    // CRUD .... MENGHAPUS DATA
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

}