package com.kevinj1008.samplesearchimage.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageEntity(
    val image: String
) : Parcelable