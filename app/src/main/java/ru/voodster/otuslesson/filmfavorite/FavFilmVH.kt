package ru.voodster.otuslesson.filmfavorite

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.R

class FavFilmVH(FilmItem: View) : RecyclerView.ViewHolder(FilmItem) {

    private val img: ImageView = itemView.findViewById(R.id.favImg)
    private val title: TextView = itemView.findViewById(R.id.favTitleTv)
    private val rmBtn:ImageView = itemView.findViewById(R.id.favRmBtn)
    private var id : Int = 0



    fun bind(film: FilmItem) {
        id = film.id
        img.setImageResource(film.img)
        title.text = film.name
        //rmBtn.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.fade_exit_500)


    }

fun onRmClick(){
    FilmList[id].clickFav()
}


}