package ru.voodster.otuslesson

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.voodster.otuslesson.db.FilmsRoomDatabase
import javax.inject.Singleton

@Module
class AppModule(private val app:App) {

    @Provides
    @Singleton
    fun provideApp():Application{
        return app
    }
    @Provides
    @Singleton
    fun provideContext():Context{
        return app.applicationContext
    }



}