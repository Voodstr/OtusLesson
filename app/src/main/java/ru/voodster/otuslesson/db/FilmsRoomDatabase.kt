package ru.voodster.otuslesson.db
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.voodster.otuslesson.DataConverter

@Database(entities = [FilmEntity::class, UserFavorites::class], version = 3,exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class FilmsRoomDatabase : RoomDatabase() {
    abstract fun getFilmsDao(): FilmDao
}