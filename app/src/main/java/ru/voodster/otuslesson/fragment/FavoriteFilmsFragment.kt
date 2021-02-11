package ru.voodster.otuslesson.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.film.FilmAdapter
import ru.voodster.otuslesson.filmfavorite.FavFilmAdapter

class FavoriteFilmsFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_films,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.filmListRV)
            .adapter =  FavFilmAdapter(FilmList.favList())
    }

}