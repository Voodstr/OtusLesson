package ru.voodster.otuslesson.filmsFavorite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmDiffUtilCallback
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.db.FilmEntity

class FavFilmAdapter(private val inflater: LayoutInflater, private val listener:((filmItem: FilmEntity)->Unit)?) : RecyclerView.Adapter<FilmVH>() {

    companion object {
        const val TAG = "FavFilmAdapter"

        const val VIEW_TYPE_FILM = 1
        const val VIEW_TYPE_FILM_HEADER = 0
    }


    private val fakeFilm = FilmEntity(0,"empty","empty","empty",false,0)
    private val fakeList = arrayListOf(fakeFilm)


    private var filmsList: ArrayList<FilmEntity> = fakeList

    override fun getItemViewType(position: Int) =
        if (position == 0) VIEW_TYPE_FILM else VIEW_TYPE_FILM_HEADER


    fun setItems(films: List<FilmEntity>) {
        Log.d(TAG, "setItems")
        val filmDiffUtilCallback = FilmDiffUtilCallback(filmsList,films)
        val diffResult = DiffUtil.calculateDiff(filmDiffUtilCallback)
        filmsList.clear()
        filmsList.addAll(films)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmVH {
        return FilmVH(inflater.inflate(R.layout.item_film, parent, false))
    }

    override fun getItemCount(): Int { // Метод возвращает размер списка
        return filmsList.size
    }


    override fun onBindViewHolder(holder: FilmVH, position: Int) {
        var xx = filmsList[position]
        holder.bind(xx)

    holder.itemView.setOnClickListener {
        listener?.invoke(xx)
    }
        holder.likeBtn.setOnClickListener {
            xx.fav = !xx.fav
            var newpoz = filmsList.getlistposition(xx)
            filmsList.remove(xx)
            notifyItemRemoved( newpoz)
        }

    }



}

