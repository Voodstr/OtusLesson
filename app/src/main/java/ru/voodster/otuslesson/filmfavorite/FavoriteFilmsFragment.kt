package ru.voodster.otuslesson.filmfavorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.R.layout.fragment_favorite
import java.lang.Exception

class FavoriteFilmsFragment:Fragment() {

    var listener : OnFavClickListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(activity is OnFavClickListener){
            listener = activity as OnFavClickListener
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
            .adapter =  FavFilmAdapter(FilmList.favList()){
            listener?.onFavFilmClick(it)
        }
    }

    interface OnFavClickListener{
        fun onFavFilmClick(filmItem: FilmItem)
    }

}