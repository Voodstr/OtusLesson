package ru.voodster.otuslesson.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.R

class AboutFragment(private val filmID:Int) :Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_coordinator,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
        setClickListeners()


    }


    private fun setClickListeners() {

        view?.let {

            it.findViewById<FloatingActionButton>(R.id.aboutShareBtn).setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, FilmList[filmID].name+"\n"+ FilmList[filmID].description) //передаю название фильма
                startActivity(intent)
            }
        }

    }

    private fun setViews(){
        view?.let {
            it.findViewById<CollapsingToolbarLayout>(R.id.aboutTitleTv).title = FilmList[filmID].name
            it.findViewById<CollapsingToolbarLayout>(R.id.aboutTitleTv).setExpandedTitleColor(it.context.getColor(R.color.pal_2))
            it.findViewById<CollapsingToolbarLayout>(R.id.aboutTitleTv).setCollapsedTitleTextColor(it.context.getColor(R.color.pal_4))
            it.findViewById<TextView>(R.id.aboutDescriptionTv).text = FilmList[filmID].description
            it.findViewById<ImageView>(R.id.aboutImg).setImageResource(FilmList[filmID].img)
        }

    }

}