package ru.voodster.otuslesson.db

import androidx.room.*
import com.google.gson.annotations.SerializedName
@Entity(tableName = "films_table")
data class FilmEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "img") var img: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "fav") var fav: Boolean,
    @ColumnInfo(name = "likes") var likes: Int
)