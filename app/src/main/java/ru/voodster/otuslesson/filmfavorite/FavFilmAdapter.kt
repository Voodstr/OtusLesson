package ru.voodster.otuslesson.filmfavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
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
                if (holder.adapterPosition==itemCount-1){
                    it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.image_btn_click))
                    val exitDialogBuilder = AlertDialog.Builder(it.context)
                    exitDialogBuilder.setTitle(R.string.rmDialTitle)
                    exitDialogBuilder.setMessage(R.string.rmDialMsg)
                    exitDialogBuilder.setCancelable(true)
                    exitDialogBuilder.setPositiveButton(R.string.yesBtn
                    ) { _, _ ->
                        holder.onRmClick()
                        favFilmsList.removeLast()
                        notifyItemRemoved(holder.adapterPosition)
                    }
                    val b = exitDialogBuilder.create()
                    b.show()

                }else{
                    it.startAnimation(AnimationUtils.loadAnimation(it.context, R.anim.image_btn_click))
                    val exitDialogBuilder = AlertDialog.Builder(it.context)
                    exitDialogBuilder.setTitle(R.string.rmDialTitle)
                    exitDialogBuilder.setMessage(R.string.rmDialMsg)
                    exitDialogBuilder.setCancelable(true)
                    exitDialogBuilder.setPositiveButton(R.string.yesBtn
                    ) { _, _ ->
                        holder.onRmClick()
                        favFilmsList.removeAt(holder.adapterPosition)
                        notifyItemRemoved(holder.adapterPosition)
                    }
                    val b = exitDialogBuilder.create()
                    b.show()
                }
            }
        }
        holder.itemView.setOnClickListener { listener?.invoke(favFilmsList[position-1]) }
    }
}