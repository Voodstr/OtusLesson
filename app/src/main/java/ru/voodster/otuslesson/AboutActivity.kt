package ru.voodster.otuslesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class AboutActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val filmID = intent.extras?.getInt("EXTRA_ID")
        filmID?.let {
            findViewById<TextView>(R.id.aboutTitle).text = FilmList[it].name
            findViewById<TextView>(R.id.aboutDescription).text = FilmList[it].description
            findViewById<ImageView>(R.id.aboutImg).setImageResource(FilmList[it].img)
        }

    }
}