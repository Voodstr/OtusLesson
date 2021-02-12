package ru.voodster.otuslesson.film

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.R

class FilmVH(FilmItem: View) : RecyclerView.ViewHolder(FilmItem) {

    private val img: ImageView = itemView.findViewById(R.id.filmImg)
    private val title: TextView = itemView.findViewById(R.id.filmTitleTv)
    private val likeBtn: ImageView = itemView.findViewById(R.id.filmLikeBtn)

    var listener : FilmVH.OnFavClickListener?=null

    companion object{
        const val EXTRA_ID = 0
    }

    interface OnFavClickListener{
        fun onFavClick(film:FilmItem)
    }

    private fun pressLike(film: FilmItem, context: Context){
            if (film.fav) {
                likeBtn.setBackgroundResource(R.drawable.baseline_favorite_red_a200_24dp)
                Toast.makeText(context,R.string.addFavToast,Toast.LENGTH_SHORT).show()
            } else {
                likeBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_24dp)
                Toast.makeText(context,R.string.rmFavToast,Toast.LENGTH_SHORT).show()
            }
    }

    private fun pressLike(film: FilmItem){
        if (film.fav) {
            likeBtn.setBackgroundResource(R.drawable.baseline_favorite_red_a200_24dp)
        } else {
            likeBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_24dp)
        }
    }


    fun bind(film: FilmItem) {
        img.setImageResource(film.img)
        title.text = film.name
        pressLike(film)

        likeBtn.setOnClickListener {
            film.clickFav()
            pressLike(film,it.context)
        }
    }

}