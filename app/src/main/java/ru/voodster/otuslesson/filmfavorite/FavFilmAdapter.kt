package ru.voodster.otuslesson.filmfavorite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.R

class FavFilmAdapter (private val favFilmsList :ArrayList<FilmItem>,
                      private val listener:((filmItem:FilmItem)->Unit)?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        const val TAG = "FavFilmAdapter"

        const val VIEW_TYPE_FAV_FILM = 1
        const val VIEW_TYPE_FAV_FILM_HEADER = 0
    }



    override fun getItemViewType(position: Int) =
        if (position == 0) VIEW_TYPE_FAV_FILM else VIEW_TYPE_FAV_FILM_HEADER


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fav_film, parent, false)
            FavFilmVH(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fav_header, parent, false)
            FavFilmHeaderVH(view)
        }
    }



    override fun getItemCount() = favFilmsList.size + 1 // +1 = header

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavFilmVH) {
            holder.bind(favFilmsList[position - 1])
            holder.itemView.findViewById<ImageView>(R.id.favRmBtn).setOnClickListener {
                holder.onRmClick()
                if (holder.adapterPosition==itemCount-1){
                    favFilmsList.removeLast()
                    notifyItemRemoved(holder.adapterPosition)
                }else{
                    favFilmsList.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                }
            }
        }
        holder.itemView.setOnClickListener { listener?.invoke(favFilmsList[position-1]) }
    }
}