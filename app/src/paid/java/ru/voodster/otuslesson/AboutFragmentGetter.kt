package ru.voodster.otuslesson

import androidx.fragment.app.Fragment
import ru.voodster.otuslesson.about.AboutFragment
import ru.voodster.otuslesson.db.FilmEntity

class AboutFragmentGetter:IAboutFragmentGetter {
    override fun getAboutFragment(film: FilmEntity): Fragment {
        return AboutFragment.newInstance(film)
    }
}