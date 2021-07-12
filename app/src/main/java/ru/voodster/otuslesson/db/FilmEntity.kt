package ru.voodster.otuslesson.db

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "films_table")
@Parcelize
data class FilmEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "img") var img: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "fav") var fav: Boolean,
    @ColumnInfo(name = "likes") var likes: Int,
    @ColumnInfo(name = "watch") var watch: Boolean=false,
    @ColumnInfo(name = "watchDate") var watchDate:Date = Date(0)
):Parcelable