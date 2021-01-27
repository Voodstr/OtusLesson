package ru.voodster.otuslesson

import android.os.Parcel
import android.os.Parcelable

// Data class для фильма
//
data class Film(
    val id:Int,
    val img:Int,
    val name : String,
    val description:String,
    val likes:Int)
