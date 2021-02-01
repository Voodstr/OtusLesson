package ru.voodster.otuslesson.film

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.R

class FilmVH(FilmItem: View) : RecyclerView.ViewHolder(FilmItem) {

    private val img: ImageView = itemView.findViewById(R.id.img)
    private val title: TextView = itemView.findViewById(R.id.titleTv)
    private val likeBtn: ImageView = itemView.findViewById(R.id.likeBtn)

    private fun pressLike(film: FilmItem){
        if (film.fav) {
            likeBtn.setBackgroundResource(R.drawable.baseline_favorite_red_a200_24dp)
        } else likeBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_24dp)
    }


    fun bind(film: FilmItem) {
        img.setImageResource(film.img)
        title.text = film.name

        pressLike(film)

        likeBtn.setOnClickListener {
            film.clickFav()
            pressLike(film)
        }

        title.setOnClickListener() {

        }

    }
}