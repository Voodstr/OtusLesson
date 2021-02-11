package ru.voodster.otuslesson.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.R

class AboutFragment(private val filmID:Int) :Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
        setClickListeners()


    }


    private fun setClickListeners() {

        view?.let {
            it.findViewById<Button>(R.id.aboutLikeBtn).setOnClickListener { view ->
                FilmList[filmID].like()
                view.findViewById<TextView>(R.id.aboutLikesTv).text= FilmList[filmID].likes.toString()
            }

            it.findViewById<Button>(R.id.aboutShareBtn).setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, FilmList[filmID].name+"\n"+ FilmList[filmID].description) //передаю название фильма
                startActivity(intent)
            }
        }

    }

    private fun setViews(){
        view?.let {
            it.findViewById<TextView>(R.id.aboutTitleTv).text = FilmList[filmID].name
            it.findViewById<TextView>(R.id.aboutDescriptionTv).text = FilmList[filmID].description
            it.findViewById<ImageView>(R.id.aboutImg).setImageResource(FilmList[filmID].img)
            it.findViewById<TextView>(R.id.aboutLikesTv).text= FilmList[filmID].likes.toString()
        }

    }

}