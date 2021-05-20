package ru.voodster.otuslesson.film

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private var recyclerView: RecyclerView? = null
    private var adapter: FilmAdapter? = null

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



    private fun initRecycler() {
        recyclerView = requireView().findViewById(R.id.filmListRV) // находим RecylerView  в layout XML
        adapter =  FilmAdapter(LayoutInflater.from(context)) // Создаем адаптер для элементов списка
        recyclerView!!.adapter = adapter // передаем адаптер нашему RecyclerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        filmListViewModel.onGetData()

        view.findViewById<SwipeRefreshLayout>(R.id.swipeUpdate).setOnRefreshListener {
            filmListViewModel.onGetData()
            view.findViewById<SwipeRefreshLayout>(R.id.swipeUpdate).isRefreshing=false
        }

        filmListViewModel.films.observe(viewLifecycleOwner, { list ->
            (recyclerView?.adapter as FilmAdapter).setItems(list)
        })

        filmListViewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })
        filmListViewModel.onGetData()
    }



    interface OnFilmClickListener{
        fun onFilmClick(filmItem: FilmModel)
    }
}