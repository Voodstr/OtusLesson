package ru.voodster.otuslesson.di

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.voodster.otuslesson.App
import ru.voodster.otuslesson.db.FilmsRoomDatabase
import javax.inject.Singleton



@Module
class DbModule {


    companion object{
        const val DATABASE_NAME = "db-name.db"
    }

    @Singleton
    @Provides
    fun provideDatabase(): FilmsRoomDatabase {
        return Room.databaseBuilder(
            App.instance!!.applicationContext,
            FilmsRoomDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }


}