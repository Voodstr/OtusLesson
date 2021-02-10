package ru.voodster.otuslesson.film

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.AboutActivity
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.R

class FilmVH(FilmItem: View) : RecyclerView.ViewHolder(FilmItem) {

    private val img: ImageView = itemView.findViewById(R.id.filmImg)
    private val title: TextView = itemView.findViewById(R.id.filmTitleTv)
    private val likeBtn: ImageView = itemView.findViewById(R.id.filmLikeBtn)
    private val aboutBtn : ImageView = itemView.findViewById(R.id.filmAboutBtn)

    companion object{
        const val EXTRA_ID = 0
    }

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

        aboutBtn.setOnClickListener{
            val intent = Intent(it.context,AboutActivity::class.java)
            intent.putExtra("EXTRA_ID",film.id)
            startActivity(it.context,intent, Bundle.EMPTY)
        }

    }
}