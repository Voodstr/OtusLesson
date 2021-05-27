package ru.voodster.otuslesson.favoriteFilms

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.R
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.voodster.otuslesson.FilmListViewModel
import ru.voodster.otuslesson.api.FilmModel
import ru.voodster.otuslesson.films.FilmAdapter
import ru.voodster.otuslesson.films.FilmListFragment
import java.lang.Exception

class FavFilmListFragment : Fragment()  {

    private val viewModel : FilmListViewModel by activityViewModels()

    var listener : OnFilmClickListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("FavFilmListFragment","$viewModel")
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
        return inflater.inflate(R.layout.fragment_favorite,container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        // recyclerView init
        initRecycler()

        //data update
        viewModel.onGetDataFromDatabase()

        //update after top swipe
        view.findViewById<SwipeRefreshLayout>(R.id.favSwipeUpdate).setOnRefreshListener {
            viewModel.onGetDataFromDatabase()
            view.findViewById<SwipeRefreshLayout>(R.id.favSwipeUpdate).isRefreshing=false
        }

        //subscribe to data
        viewModel.films.observe(viewLifecycleOwner, { list ->
            Log.d("FavList","$list")
            (view.findViewById<RecyclerView>(R.id.favoriteRV).adapter as FavFilmAdapter).setItems(list)
        })

        //subscribe to error message
        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initRecycler() {
        Log.d(FilmListFragment.TAG, "initRecycler")
        view?.findViewById<RecyclerView>(R.id.favoriteRV)
            ?.adapter = FavFilmAdapter(LayoutInflater.from(context)) {
            listener?.onFilmClick(it)
        }
    }

    interface OnFilmClickListener{
        fun onFilmClick(filmItem: FilmModel)
    }
}