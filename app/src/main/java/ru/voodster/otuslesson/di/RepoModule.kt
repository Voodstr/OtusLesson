package ru.voodster.otuslesson.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import ru.voodster.otuslesson.FilmsRepository

@Module
interface RepoModule {
    @Binds
    @Reusable
    fun filmsRepository(repository: FilmsRepository):FilmsRepository
}