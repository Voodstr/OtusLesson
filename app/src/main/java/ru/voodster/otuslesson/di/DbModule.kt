package ru.voodster.otuslesson.di

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.voodster.otuslesson.App
import ru.voodster.otuslesson.db.FilmsRoomDatabase


@Module
class DbModule {

    companion object{
        const val DATABASE_NAME = "db-name.db"
    }

    @Reusable
    @Provides
    fun provideDatabase(): FilmsRoomDatabase {
        return Room.databaseBuilder(
            App.instance!!.applicationContext,
            FilmsRoomDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }


}