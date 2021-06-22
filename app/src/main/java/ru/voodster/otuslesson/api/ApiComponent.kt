package ru.voodster.otuslesson.api

import dagger.Component
import dagger.Subcomponent
import ru.voodster.otuslesson.FilmsRepository

@Component
interface ApiComponent {
    fun inject(filmsRepository: FilmsRepository)
}