package ru.voodster.otuslesson.db

import androidx.room.*
import com.google.gson.annotations.SerializedName
@Entity(tableName = "films_table")
class FilmModel {
    @PrimaryKey(autoGenerate = true) var rowID: Int = 0
    @SerializedName("id")
    @ColumnInfo(name = "id") var id: Int=0
    @SerializedName("img")
    @ColumnInfo(name = "img") var img: String = "empty"
    @SerializedName("title")
    @ColumnInfo(name = "title") var title: String = "empty"
    @SerializedName("description")
    @ColumnInfo(name = "description") var description: String = "empty"
    @ColumnInfo(name = "fav")
    @SerializedName("fav")
    var fav: Boolean = false
    @SerializedName("likes")
    @ColumnInfo(name = "likes") var likes: Int = 0

}