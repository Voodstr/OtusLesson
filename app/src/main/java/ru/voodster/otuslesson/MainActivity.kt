package ru.voodster.otuslesson

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.voodster.otuslesson.about.AboutFragment
import ru.voodster.otuslesson.db.Db
import ru.voodster.otuslesson.db.FilmEntity
import ru.voodster.otuslesson.filmsFavorite.FavFilmListFragment
import ru.voodster.otuslesson.films.FilmListFragment
import ru.voodster.otuslesson.search.SearchFragment


class MainActivity : AppCompatActivity(), FilmListFragment.OnFilmClickListener,FavFilmListFragment.OnFilmClickListener {

    private val filmListFragment = FilmListFragment()
    private val searchFragment = SearchFragment()
    private val favFilmListFragment = FavFilmListFragment()

    private val viewModel : FilmListViewModel by viewModels()

    companion object{
        const val TAG = "MainActivity"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)

        openList()

        val  bundle = intent.getBundleExtra("bundle")
        val film = bundle?.getParcelable<FilmEntity>("film")
        if(film!=null){
            openAboutFilm(film)
        }
        val watchfilm = bundle?.getString("filmid")
        Log.d(TAG, "watchFilm = $watchfilm")
        if (watchfilm!=null){
            viewModel.getFilm(watchfilm.toInt())
        }

        viewModel.watchFilm.observe(this,{
            openAboutFilm(it)
        })



        //lifecycle.addObserver(App.instance!!.filmsUpdater)


        setClickListeners()
        setNavigationBar()
    }


    private fun setClickListeners(){
        filmListFragment.listener = this
    }



    override fun onFilmClick(filmItem: FilmEntity) {
        openAboutFilm(filmItem)
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



    private fun openAboutFilm(film: FilmEntity) {
        Log.d(TAG,"openAboutFilm")
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_toptobottom,R.anim.exit_bottomtotop,R.anim.enter_bottomtotop,R.anim.exit_toptobottom)
            .replace(R.id.fragmentContainer, AboutFragment.newInstance(film))
            .addToBackStack(null)
            .commit()
    }

    private fun openList(){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_right_toleftt,R.anim.exit_left_toright)
            .replace(R.id.fragmentContainer, filmListFragment.apply {
                listener = this@MainActivity
            })
            .commit()
    }

    //так и не могу понять как сделать анимацию для центрального элемена
    // надо откуда-то знать какой предыдущий был элемент, правый или левый

    private fun openFav(){ //
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_right_toleftt,R.anim.exit_left_toright)
            .replace(R.id.fragmentContainer, favFilmListFragment.apply {
                listener = this@MainActivity
            })
            .commit()
    }

    private fun openSearch(){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_right_toleftt,R.anim.exit_left_toright)
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

    override fun onDestroy() {
        Db.saveFavorites()
        Db.saveCachedFilms()
        super.onDestroy()
    }
}

