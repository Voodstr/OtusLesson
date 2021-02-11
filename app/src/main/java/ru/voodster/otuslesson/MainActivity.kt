package ru.voodster.otuslesson

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.voodster.otuslesson.film.FilmAdapter
import ru.voodster.otuslesson.fragment.FavoriteFilmsFragment
import ru.voodster.otuslesson.fragment.FilmListFragment
import ru.voodster.otuslesson.fragment.SearchFragment


class MainActivity : AppCompatActivity() {

    private var  backPressedTime:Long = 0

    private val filmListFragment = FilmListFragment()
    private val favFilmListFragment = FavoriteFilmsFragment()
    private val searchFragment = SearchFragment()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickListeners()
        replaceFragment(filmListFragment)
        setNavigationBar()
    }




    private fun setClickListeners(){

    }

    private fun setNavigationBar(){
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_filmList -> replaceFragment(filmListFragment)
                R.id.nav_favorite -> replaceFragment(favFilmListFragment)
                R.id.nav_search -> replaceFragment(searchFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if(fragment!=null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()
        }
    }


    override fun onBackPressed() {
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

