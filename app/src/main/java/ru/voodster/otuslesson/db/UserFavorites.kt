package ru.voodster.otuslesson.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favorites")
data class UserFavorites(
    @PrimaryKey(autoGenerate = false) //TODO Разобраться насчет PrimaryKey & AutoGenerate
    @ColumnInfo(name = "filmID") var filmID: Int = 0
)