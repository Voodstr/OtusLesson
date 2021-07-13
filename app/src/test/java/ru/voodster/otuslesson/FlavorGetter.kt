package ru.voodster.otuslesson

import androidx.fragment.app.Fragment
import ru.voodster.otuslesson.about.AboutFragmentStatic
import ru.voodster.otuslesson.db.FilmEntity

open class FlavorGetter:FlavorInterface {
     override fun getAboutFragment(film: FilmEntity): Fragment {
        return AboutFragmentStatic.newInstance(film)
    }

    override fun initFireBase() {
    }
}