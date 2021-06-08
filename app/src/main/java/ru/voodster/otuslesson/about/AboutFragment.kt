package ru.voodster.otuslesson.about

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.db.FilmModel

class AboutFragment(private val film: FilmModel) :Fragment() {


    companion object{
        const val TAG = "AboutFragment"
    }

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
                Log.d(TAG, "")
                intent.putExtra(Intent.EXTRA_TEXT, film.title+"\n"+ film.description) //передаю название фильма
                startActivity(intent)
            }
        }

    }

    private fun setViews(){
        view?.let {
            it.findViewById<CollapsingToolbarLayout>(R.id.aboutTitleTv).title = film.title
            it.findViewById<CollapsingToolbarLayout>(R.id.aboutTitleTv).setExpandedTitleColor(it.context.getColor(R.color.pal_2))
            it.findViewById<CollapsingToolbarLayout>(R.id.aboutTitleTv).setCollapsedTitleTextColor(it.context.getColor(R.color.pal_4))
            it.findViewById<TextView>(R.id.aboutDescriptionTv).text = film.description.plus("\n")
            Glide
                .with(this)
                .load("http://developer.alexanderklimov.ru/android/images/android_cat.jpg")
                .into(it.findViewById(R.id.aboutImg))
        }

    }

}