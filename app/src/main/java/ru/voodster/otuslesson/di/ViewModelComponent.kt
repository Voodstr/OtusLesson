package ru.voodster.otuslesson.di

import dagger.Component
import ru.voodster.otuslesson.FilmsRepository
import javax.inject.Singleton


@Component(modules = [ApiModule::class, DbModule::class])
@Singleton
interface ViewModelComponent {

    fun repos(): FilmsRepository

}