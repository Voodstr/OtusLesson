package ru.voodster.otuslesson.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.MainActivity
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.R.layout.fragment_favorite
import ru.voodster.otuslesson.filmfavorite.FavFilmAdapter
import java.lang.Exception

class FavoriteFilmsFragment:Fragment() {

    var listener : FavoriteFilmsFragment.onFilmClickListener?=null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(activity is FavoriteFilmsFragment.onFilmClickListener){
            listener = activity as onFilmClickListener
        }else{
            throw Exception("Activity must implement onFilmClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(fragment_favorite,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.favoriteRV)
            .adapter =  FavFilmAdapter(LayoutInflater.from(requireContext()),FilmList.favList()){
            listener?.onFavFilmClick(it)
        }
    }

    interface onFilmClickListener{
        fun onFavFilmClick(filmItem: FilmItem)
    }

}