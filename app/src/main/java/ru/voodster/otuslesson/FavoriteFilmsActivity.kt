package ru.voodster.otuslesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.filmfavorite.FavFilmAdapter

class FavoriteFilmsActivity() : AppCompatActivity() {

    companion object {

    }

    private val mFavFilmList = FilmList.favList()
    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerViewFavorite)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_films)
        initRecycler()
    }


    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
            recyclerView.adapter = FavFilmAdapter(mFavFilmList)

    }
}