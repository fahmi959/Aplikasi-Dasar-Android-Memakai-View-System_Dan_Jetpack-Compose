package com.fahmi.fahmipundamentalandroid

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FavoriteDbHelper private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "favorite_db"
        private const val TABLE_NAME = "favorite_table"
        private const val COLUMN_ID = "id"
        private const val COLUMN_LOGIN = "login"
        private const val COLUMN_AVATAR_URL = "avatar_url"
        private const val COLUMN_HTML_URL = "html_url"
        private const val COLUMN_BOOKMARKED = "bookmarked"

        @Volatile
        private var instance: FavoriteDbHelper? = null

        fun getInstance(context: Context): FavoriteDbHelper =
            instance ?: synchronized(this) {
                instance ?: FavoriteDbHelper(context.applicationContext).also { instance = it }
            }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSql = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_LOGIN TEXT," +
                "$COLUMN_AVATAR_URL TEXT," +
                "$COLUMN_HTML_URL TEXT," +
                "$COLUMN_BOOKMARKED INTEGER" +
                ")"
        db.execSQL(createTableSql)


    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }




    fun insertFavorite(penggunaGithub: PenggunaGithub) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, penggunaGithub.id)
        values.put(COLUMN_LOGIN, penggunaGithub.login)
        values.put(COLUMN_AVATAR_URL, penggunaGithub.avatarUrl)
        values.put(COLUMN_HTML_URL, penggunaGithub.htmlUrl)
        values.put(COLUMN_BOOKMARKED, if (penggunaGithub.isBookmarked) 1 else 0)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun removeFavorite(penggunaGithub: PenggunaGithub) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(penggunaGithub.id.toString()))
        db.close()
    }

    fun isFavorite(penggunaGithub: PenggunaGithub): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME, null,
            "$COLUMN_ID = ?", arrayOf(penggunaGithub.id.toString()),
            null, null, null
        )
        val isFavorite = cursor.moveToFirst()
        cursor.close()
        db.close()
        return isFavorite
    }


    fun getFavoriteList(): List<PenggunaGithub> {
        val favoriteList = mutableListOf<PenggunaGithub>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME, null, null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val idColumnIndex = cursor.getColumnIndex(COLUMN_ID)
                val loginColumnIndex = cursor.getColumnIndex(COLUMN_LOGIN)
                val avatarUrlColumnIndex = cursor.getColumnIndex(COLUMN_AVATAR_URL)
                val htmlUrlColumnIndex = cursor.getColumnIndex(COLUMN_HTML_URL)
                val bookmarkedColumnIndex = cursor.getColumnIndex(COLUMN_BOOKMARKED)
                if (idColumnIndex != -1 && loginColumnIndex != -1 && avatarUrlColumnIndex != -1
                    && htmlUrlColumnIndex != -1 && bookmarkedColumnIndex != -1) {
                    val id = cursor.getInt(idColumnIndex)
                    val login = cursor.getString(loginColumnIndex)
                    val avatarUrl = cursor.getString(avatarUrlColumnIndex)
                    val htmlUrl = cursor.getString(htmlUrlColumnIndex)
                    val isBookmarked = cursor.getInt(bookmarkedColumnIndex) != 0
                    val penggunaGithub = PenggunaGithub(id, login, avatarUrl, htmlUrl, isBookmarked)
                    favoriteList.add(penggunaGithub)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return favoriteList
    }


}