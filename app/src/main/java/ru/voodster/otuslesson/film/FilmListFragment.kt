package ru.voodster.otuslesson.film

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.R
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.voodster.otuslesson.api.FilmModel
import java.lang.Exception

class FilmListFragment : Fragment()  {

    private val filmListViewModel : FilmListViewModel by activityViewModels()

    var listener : OnFilmClickListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
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


        // recyclerView init
        view.findViewById<RecyclerView>(R.id.filmListRV)
            .adapter = FilmAdapter(LayoutInflater.from(context)) {
            listener?.onFilmClick(it)
        }

        //data update
        //
        filmListViewModel.onGetDataFromDatabase()

        //update after top swipe
        view.findViewById<SwipeRefreshLayout>(R.id.swipeUpdate).setOnRefreshListener {
            filmListViewModel.onGetDataFromDatabase()
            view.findViewById<SwipeRefreshLayout>(R.id.swipeUpdate).isRefreshing=false
        }

        view.findViewById<Button>(R.id.updateBtn).setOnClickListener {
            filmListViewModel.onUpdateFromServer()
        }
        //subscribe to data
        filmListViewModel.films.observe(viewLifecycleOwner, { list ->
            (view.findViewById<RecyclerView>(R.id.filmListRV).adapter as FilmAdapter).setItems(list)
        })

        //subscribe to error message
        filmListViewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })
    }



    interface OnFilmClickListener{
        fun onFilmClick(filmItem: FilmModel)
    }
}