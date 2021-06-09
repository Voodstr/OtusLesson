package ru.voodster.otuslesson.db
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FilmModel::class, UserFavorites::class], version = 3,exportSchema = false)
abstract class FilmsRoomDatabase : RoomDatabase() {
    abstract fun getFilmsDao(): FilmDao
}