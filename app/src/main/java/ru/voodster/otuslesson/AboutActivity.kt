package ru.voodster.otuslesson

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class AboutActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID = 0
        const val EXTRA_TEXT  = "EXTRA_TEXT"
    }

    private var filmID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        intent.extras?.let {
            filmID = it.getInt("EXTRA_ID")
        }

        setViews()
        setClickListeners()

    }

    private fun setClickListeners() {

        findViewById<Button>(R.id.aboutLikeBtn).setOnClickListener {
            FilmList[filmID].like()
            findViewById<TextView>(R.id.aboutLikesTv).text=FilmList[filmID].likes.toString()
        }

        findViewById<Button>(R.id.aboutShareBtn).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,FilmList[filmID].name+"\n"+FilmList[filmID].description) //передаю название фильма
            startActivity(intent)
        }
    }

    private fun setViews(){

        filmID.let {
            findViewById<TextView>(R.id.aboutTitleTv).text = FilmList[it].name
            findViewById<TextView>(R.id.aboutDescriptionTv).text = FilmList[it].description
            findViewById<ImageView>(R.id.aboutImg).setImageResource(FilmList[it].img)
            findViewById<TextView>(R.id.aboutLikesTv).text=FilmList[filmID].likes.toString()
        }
    }
}