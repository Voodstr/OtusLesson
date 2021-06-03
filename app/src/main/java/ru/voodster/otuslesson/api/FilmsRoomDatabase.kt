package ru.voodster.otuslesson.api
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FilmModel::class), version = 2,exportSchema = false)
abstract class FilmsRoomDatabase : RoomDatabase() {
    abstract fun getFilmsDao(): FilmDao
}