package ru.voodster.otuslesson

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.time.LocalDate

// Data class для фильма
//
 class FilmItem(
    val id:Int,
    val img:Int,
    val name : String,
    val description:String,
    var fav:Boolean = false){
    companion object {
        const val TAG = "FilmItem"
    }

    fun clickFav(){
        Log.d(TAG,"fav $fav")
        fav=!fav
    }
 }