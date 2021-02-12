package ru.voodster.otuslesson.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.film.FilmAdapter
import java.lang.Exception

class FilmListFragment:Fragment()  {

    var listener : OnFilmClickListener?=null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(activity is OnFilmClickListener){
            listener = activity as OnFilmClickListener
        }else{
            throw Exception("Activity must implement onFilmClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filmlist,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.filmListRV)
            .adapter = FilmAdapter(LayoutInflater.from(requireContext()), FilmList) {
            //(activity as MainActivity).openAboutFilm(it.id)
                listener?.onFilmClick(it)
        }
    }


    interface OnFilmClickListener{
        fun onFilmClick(filmItem: FilmItem)
    }
}