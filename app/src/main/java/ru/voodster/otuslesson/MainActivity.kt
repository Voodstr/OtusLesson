package ru.voodster.otuslesson

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.film.FilmAdapter


class MainActivity : AppCompatActivity() {

    private var  backPressedTime:Long = 0


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
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
            recyclerView.adapter = FilmAdapter(FilmList)

    }


    private fun setClickListeners(){

        findViewById<Button>(R.id.favBtn).setOnClickListener {
            val intent = Intent(this, FavoriteFilmsActivity()::class.java)
            startActivity(intent)
        }
    }

   /* override fun onBackPressed() {
        val t = System.currentTimeMillis()
        if(t - backPressedTime > 2000){
            backPressedTime = t
            Toast.makeText(this, "Press back again to logout",
                Toast.LENGTH_SHORT).show()
        } else {    // this guy is serious
            // clean up
            super.onBackPressed();       // bye
        }
    }*/


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

