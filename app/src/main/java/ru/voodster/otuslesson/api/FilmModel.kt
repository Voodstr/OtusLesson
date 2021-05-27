package ru.voodster.otuslesson.api

import androidx.room.*
import com.google.gson.annotations.SerializedName
@Entity(tableName = "films_table")
class FilmModel {
    @SerializedName("rowID")
    @PrimaryKey(autoGenerate = true)
    var rowID: Int = 0
    @SerializedName("id")
    @ColumnInfo(name = "id") var id: String = "empty"
    @SerializedName("img")
    @ColumnInfo(name = "img") var img: String = "empty"
    @SerializedName("title")
    @ColumnInfo(name = "title") var title: String = ""
    @SerializedName("description")
    @ColumnInfo(name = "description") var description: String = "empty"
    @SerializedName("fav")
    @ColumnInfo(name = "fav") var fav: Boolean = false
    @SerializedName("likes")
    @ColumnInfo(name = "likes") var likes: Int = 0

}