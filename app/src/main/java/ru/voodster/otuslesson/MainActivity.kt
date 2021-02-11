package ru.voodster.otuslesson

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.voodster.otuslesson.fragment.AboutFragment
import ru.voodster.otuslesson.fragment.FavoriteFilmsFragment
import ru.voodster.otuslesson.fragment.FilmListFragment
import ru.voodster.otuslesson.fragment.SearchFragment


class MainActivity : AppCompatActivity(), FilmListFragment.onFilmClickListener,
    FavoriteFilmsFragment.onFilmClickListener {

    private val filmListFragment = FilmListFragment()
    private val favFilmListFragment = FavoriteFilmsFragment()
    private val searchFragment = SearchFragment()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openList()

        setClickListeners()
        setNavigationBar()
    }




    private fun setClickListeners(){
        filmListFragment.listener = this
        favFilmListFragment.listener = this
    }


    override fun onFilmClick(filmItem: FilmItem) {
        openAboutFilm(filmItem.id)
    }

    override fun onFavFilmClick(filmItem: FilmItem) {
        openAboutFilm(filmItem.id)
    }

    private fun setNavigationBar(){
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_filmList -> openList()
                R.id.nav_favorite -> openFav()
                R.id.nav_search -> openSearch()
            }
            true
        }
    }



    private fun openAboutFilm(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,AboutFragment(id))
            .addToBackStack(null)
            .commit()
    }

    private fun openList(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, filmListFragment.apply {
                listener = this@MainActivity
            })
            .commit()
    }

    private fun openFav(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, favFilmListFragment.apply {
                listener = this@MainActivity
            })
            .commit()
    }

    fun openSearch(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, searchFragment)
            .commit()
    }


    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount>0){
            supportFragmentManager.popBackStack()
        }else{
            val exitDialogBuilder = AlertDialog.Builder(this)
            exitDialogBuilder.setTitle(R.string.exitDialogTitle)
            exitDialogBuilder.setMessage(R.string.exitDialogText)
            exitDialogBuilder.setCancelable(true)
            exitDialogBuilder.setPositiveButton(R.string.yesBtn
            ) { _, _ ->
                super.onBackPressed()
            }
            val b = exitDialogBuilder.create()
            b.show()
        }

    }
}

