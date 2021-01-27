package ru.voodster.otuslesson

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class AboutActivity : AppCompatActivity() {

    private var mFilmList:ArrayList<Film>?= null

    companion object{
        private const val  EXTRA_ID = 1
        const val REQUEST_CODE_SELECTED_FILM = 1

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        mFilmList = Constants.getFilmList()
        mFilmList?.let { filmList ->
            intent.getIntExtra("EXTRA_ID",0)?.let {
                findViewById<TextView>(R.id.descriptionText).text = filmList[it].description
            }
        }


    }


}