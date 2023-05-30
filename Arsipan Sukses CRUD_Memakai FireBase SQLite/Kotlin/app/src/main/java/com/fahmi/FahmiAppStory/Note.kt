package com.fahmi.FahmiAppStory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null,
    var nama: String? = null,
    var model_foto_url: ByteArray? = null
) : Parcelable