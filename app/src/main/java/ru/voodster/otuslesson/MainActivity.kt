package ru.voodster.otuslesson

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    private var mFilmList: ArrayList<Film>? = null
    var mSelectedFilm: Int = 0


    companion object {
        private const val DESCRIPTION_TEXT = "TEXT"
        private const val EXTRA_ID = 0
        const val REQUEST_CODE_SELECTED_FILM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFilmList = Constants.getFilmList()
        Log.i("SELECTED FILM: ","$mSelectedFilm")
        setList()
        setButtons()
        setTextColor(mSelectedFilm)


    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("EXTRA_ID", mSelectedFilm)
        Log.i("onSaveInstanceState", " SELECTED :$mSelectedFilm");

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mSelectedFilm = savedInstanceState.getInt("EXTRA_ID", 0)
        setTextColor(mSelectedFilm)
        Log.i("onRestoreInstanceState", " SELECTED :$mSelectedFilm");
    }

    private fun setList() {
        mFilmList?.let { filmList ->
            findViewById<ImageView>(R.id.img1).setImageResource(filmList[0].img)
            findViewById<ImageView>(R.id.img2).setImageResource(filmList[1].img)
            findViewById<ImageView>(R.id.img3).setImageResource(filmList[2].img)

            findViewById<TextView>(R.id.txt1).text = filmList[0].name
            findViewById<TextView>(R.id.txt2).text = filmList[1].name
            findViewById<TextView>(R.id.txt3).text = filmList[2].name

            findViewById<TextView>(R.id.btn1).text = filmList[0].description
            findViewById<TextView>(R.id.btn2).text = filmList[1].description
            findViewById<TextView>(R.id.btn3).text = filmList[2].description
        }


    }

    private fun setTextColor(selected: Int) {
        when (selected) {
            1 -> {
                setDefaultTextColor()
                findViewById<TextView>(R.id.txt1).setBackgroundColor(Color.GRAY)

            }
            2 -> {
                setDefaultTextColor()
                findViewById<TextView>(R.id.txt2).setBackgroundColor(Color.GRAY)
            }
            3 -> {
                setDefaultTextColor()
                findViewById<TextView>(R.id.txt3).setBackgroundColor(Color.GRAY)
            }

        }
    }

    private fun setDefaultTextColor() {
        findViewById<TextView>(R.id.txt1).setBackgroundColor(Color.WHITE)
        findViewById<TextView>(R.id.txt3).setBackgroundColor(Color.WHITE)
        findViewById<TextView>(R.id.txt2).setBackgroundColor(Color.WHITE)
    }

    /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == REQUEST_CODE_SELECTED_FILM){
             if (resultCode == Activity.RESULT_OK){
                 setTextColor(mSelectedFilm)
             }
         }
     }*/

    private fun setButtons() {

        findViewById<View>(R.id.btn1).setOnClickListener {
            setTextColor(1)
            mSelectedFilm = 1
            Log.i("SELECTED FILM: ", "$mSelectedFilm")
            val intent = Intent(this, AboutActivity::class.java)
            intent.putExtra("EXTRA_ID", mSelectedFilm - 1)
            startActivity(intent)

        }
        findViewById<View>(R.id.btn2).setOnClickListener {
            setTextColor(2)
            mSelectedFilm = 2
            val intent = Intent(this, AboutActivity::class.java)
            intent.putExtra("EXTRA_ID", mSelectedFilm - 1)
            startActivity(intent)
        }
        findViewById<View>(R.id.btn3).setOnClickListener {
            setTextColor(3)
            mSelectedFilm = 3
            val intent = Intent(this, AboutActivity::class.java)
            intent.putExtra("EXTRA_ID", mSelectedFilm - 1)
            startActivity(intent)
        }

    }
}