package ru.voodster.otuslesson

import androidx.fragment.app.Fragment
import ru.voodster.otuslesson.db.FilmEntity

interface FlavorInterface {

    fun getAboutFragment(film: FilmEntity):Fragment

    fun initFireBase()
}