package ru.voodster.otuslesson.films

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.db.FilmEntity
import ru.voodster.otuslesson.di.DaggerViewModelFactoryComponent
import ru.voodster.otuslesson.viewModel.FilmListViewModel
import javax.inject.Inject


class FilmListFragment : Fragment()  {
    companion object{
        const val TAG = "FilmListFragment"
        fun newInstance(): FilmListFragment {
            return FilmListFragment()
        }

    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<FilmListViewModel>{viewModelFactory}
    //private val viewModel : FilmListViewModel by activityViewModels()

    var listener : OnFilmClickListener?=null

    override fun onPause() {
        Log.d(TAG,"onPause")
        viewModel.saveDb()
        super.onPause()
    }

/*
    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume")
        viewModel.loadDb()
    }

 */
    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
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
        DaggerViewModelFactoryComponent.builder().build().inject(this)
        return inflater.inflate(R.layout.fragment_filmlist,container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        // recyclerView init
        initRecycler()


        //update after top swipe
        view.findViewById<SwipeRefreshLayout>(R.id.swipeUpdate).setOnRefreshListener {
            viewModel.saveFav()
            viewModel.update()
            viewModel.getMoreFilmsRx()
            view.findViewById<SwipeRefreshLayout>(R.id.swipeUpdate).isRefreshing=false
        }

        //subscribe to data
        viewModel.films.observe(viewLifecycleOwner, { list ->
            (view.findViewById<RecyclerView>(R.id.filmListRV).adapter as FilmAdapter).setItems(list)
        })

        //subscribe to error message
        viewModel.errorMsg.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initRecycler(){
        Log.d(TAG,"initRecycler")
        view?.findViewById<RecyclerView>(R.id.filmListRV)
            ?.adapter = FilmAdapter(LayoutInflater.from(context)) {
            listener?.onFilmClick(it)
    }
        view?.findViewById<RecyclerView>(R.id.filmListRV)
            ?.addOnScrollListener(object :RecyclerView.OnScrollListener(){

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(isLastItemDisplaying(recyclerView)){
                        view?.findViewById<SwipeRefreshLayout>(R.id.swipeUpdate)?.isRefreshing = true
                        viewModel.getMoreFilmsRx()
                        view?.findViewById<SwipeRefreshLayout>(R.id.swipeUpdate)?.isRefreshing = false
                    }
                }
                private fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
                    if (recyclerView.adapter!!.itemCount != 0) {
                        val lastVisibleItemPosition =
                            (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                        if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!
                                .itemCount - 1
                        ) return true
                    }
                    return false
                }
            })

}



    interface OnFilmClickListener{
        fun onFilmClick(filmItem: FilmEntity)
    }
}


