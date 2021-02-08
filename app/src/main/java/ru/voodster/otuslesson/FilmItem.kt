package ru.voodster.otuslesson

import android.util.Log

// Data class для фильма
//
 class FilmItem(
    val id:Int,
    val img:Int,
    val name : String,
    val description:String,
    var fav:Boolean = false,
    var likes:Long = 0){

    companion object {
        const val TAG = "FilmItem"
    }

    fun clickFav(){
       // Log.d(TAG,"fav $fav")
        fav=!fav
    }
    fun like(){
        likes++
    }
 }