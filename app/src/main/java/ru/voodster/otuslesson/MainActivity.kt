package ru.voodster.otuslesson

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.film.FilmAdapter


class MainActivity : AppCompatActivity() {


    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerView)
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
        setClickListeners()
    }

    private fun initRecycler(){
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager
            recyclerView.adapter = FilmAdapter(FilmList)

    }


    private fun setClickListeners(){

        findViewById<Button>(R.id.favBtn).setOnClickListener {
            val intent = Intent(this, FavoriteFilmsActivity()::class.java)
            startActivity(intent)
        }

    }



}