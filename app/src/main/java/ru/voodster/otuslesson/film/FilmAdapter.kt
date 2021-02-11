package ru.voodster.otuslesson.film

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.R

class FilmAdapter( private val layoutInflater: LayoutInflater,
                   private val filmsList :ArrayList<FilmItem>,
                   private val listener:((filmItem:FilmItem)->Unit)?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        const val TAG = "FilmAdapter"

        const val VIEW_TYPE_FILM = 1
        const val VIEW_TYPE_FILM_HEADER = 0
    }

    override fun getItemViewType(position: Int)
            = if (position == 0) VIEW_TYPE_FILM else  VIEW_TYPE_FILM_HEADER


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType==0) {
                val view = layoutInflater.inflate(R.layout.item_film, parent, false)
                FilmVH(view)
            }
            else {
                val view = layoutInflater.inflate(R.layout.item_header, parent, false)
                FilmHeaderVH(view)
            }

        }

    override fun getItemCount() = filmsList.size + 1 // +1 = header]


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FilmVH) {
            holder.bind(filmsList[position-1])
        }

        holder.itemView.setOnClickListener {
            listener?.invoke(filmsList[position-1])
        }
    }



}

