package ru.voodster.otuslesson.api

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = arrayOf(FilmModel::class), version = 2,exportSchema = false)
abstract class FilmsRoomDatabase : RoomDatabase() {
    abstract fun getFilmsDao(): FilmDao
}