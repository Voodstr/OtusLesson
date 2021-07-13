package ru.voodster.otuslesson.db
import androidx.room.*

@Database(entities = [FilmEntity::class, UserFavorites::class], version = 3,exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class FilmsRoomDatabase : RoomDatabase() {
    abstract fun getFilmsDao(): FilmDao

}