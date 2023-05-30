package com.fahmi.FahmiAppStory.DB_SQLite

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
            const val NAMA = "nama"
            const val MODEL_FOTO_URL = "model_foto_url"
        }
    }
}
