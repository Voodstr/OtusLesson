package ru.voodster.otuslesson.filmfavorite

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.AboutActivity
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.R

class FavFilmVH(FilmItem: View) : RecyclerView.ViewHolder(FilmItem) {

    private val img: ImageView = itemView.findViewById(R.id.favImg)
    private val title: TextView = itemView.findViewById(R.id.favTitleTv)
    private val aboutBtn : ImageView = itemView.findViewById(R.id.favAboutBtn)


    fun bind(film: FilmItem) {
        img.setImageResource(film.img)
        title.text = film.name
        aboutBtn.setOnClickListener{
            val intent = Intent(it.context, AboutActivity::class.java)
            intent.putExtra("EXTRA_ID",film.id)
            ContextCompat.startActivity(it.context, intent, Bundle.EMPTY)
        }

    }

    }