package ru.voodster.otuslesson.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favorites")
class UserFavorites {
    @PrimaryKey(autoGenerate = true) var id = 0
    @ColumnInfo(name = "filmID") var filmID =0

}