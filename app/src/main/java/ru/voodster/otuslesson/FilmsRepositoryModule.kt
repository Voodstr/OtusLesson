package ru.voodster.otuslesson

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FilmsRepositoryModule  {


    @Provides
    @Singleton
    fun provideRepository():FilmsRepository{
        return FilmsRepository(db)
    }
}