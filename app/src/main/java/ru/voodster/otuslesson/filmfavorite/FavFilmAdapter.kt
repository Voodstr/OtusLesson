package ru.voodster.otuslesson.filmfavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.R

class FavFilmAdapter (private val filmsList : ArrayList<FilmItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG = "FilmAdapter"

        const val VIEW_TYPE_FAV_FILM = 1
        const val VIEW_TYPE_FAV_FILM_HEADER = 0
    }

    override fun getItemViewType(position: Int) =
        if (position == 0) VIEW_TYPE_FAV_FILM else VIEW_TYPE_FAV_FILM_HEADER


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Log.d(TAG,"onCreateViewHolder $viewType")
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == 0) {
            val view = layoutInflater.inflate(R.layout.item_fav_film, parent, false)
            FavFilmVH(view)
        } else {
            val view = layoutInflater.inflate(R.layout.item_fav_header, parent, false)
            FavFilmHeaderVH(view)
        }
    }


    override fun getItemCount() = filmsList.size + 1 // +1 = header

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavFilmVH) {
            holder.bind(filmsList[position - 1])
        }
    }
}