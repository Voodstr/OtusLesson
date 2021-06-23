package ru.voodster.otuslesson.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    companion object{
        const val DATABASE_NAME = "db-name.db"
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): FilmsRoomDatabase {
        return Room.databaseBuilder(
            context,
            FilmsRoomDatabase::class.java,
            DATABASE_NAME).fallbackToDestructiveMigration().build()
    }
}