package ru.voodster.otuslesson.db

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

// Data class для Room
//
 data class FilmRow(
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "img")val img:Int,
    @ColumnInfo(name = "title")val title : String,
    @ColumnInfo(name = "description")val description:String,
    @ColumnInfo(name = "likes")var likes:Long = 0)