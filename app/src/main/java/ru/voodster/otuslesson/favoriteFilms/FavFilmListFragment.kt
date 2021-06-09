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
import ru.voodster.otuslesson.db.FilmModel
import java.lang.Exception

class FavFilmListFragment : Fragment()  {

    private val viewModel : FilmListViewModel by activityViewModels()

    var listener : OnFilmClickListener?=null

    companion object{
        const val TAG = "FavFilmListFragment"
    }

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
        if(activity is OnFilmClickListener){
            listener = activity as OnFilmClickListener
        }else{
            throw Exception("Activity must implement onFilmClickListener")
        }
    }

    override fun onPause() {
        Log.d(TAG,"onPause")
        super.onPause()
        viewModel.saveFav()
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
        viewModel.onGetFavFromDatabase()

        //update after top swipe
        view.findViewById<SwipeRefreshLayout>(R.id.favSwipeUpdate).setOnRefreshListener {
            viewModel.saveFav()
            viewModel.onGetFavFromDatabase()
            view.findViewById<SwipeRefreshLayout>(R.id.favSwipeUpdate).isRefreshing=false
        }

        //subscribe to data
        viewModel.favorites.observe(viewLifecycleOwner, { list ->
            Log.d("FavList","observe")
            (view.findViewById<RecyclerView>(R.id.favoriteRV).adapter as FavFilmAdapter).setItems(list)
        })

        //subscribe to error message
        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initRecycler() {
        Log.d(TAG, "initRecycler")
        view?.findViewById<RecyclerView>(R.id.favoriteRV)
            ?.adapter = FavFilmAdapter(LayoutInflater.from(context)) {
            listener?.onFilmClick(it)
        }

        /*
        view?.findViewById<RecyclerView>(R.id.favoriteRV)
            ?.addOnScrollListener(object :RecyclerView.OnScrollListener(){

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(isLastItemDisplaying(recyclerView)){
                        view?.findViewById<SwipeRefreshLayout>(R.id.favSwipeUpdate)?.isRefreshing = true
                        viewModel.loadMoreFav()
                        view?.findViewById<SwipeRefreshLayout>(R.id.favSwipeUpdate)?.isRefreshing = false
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

         */

    }

    interface OnFilmClickListener{
        fun onFilmClick(filmItem: FilmModel)
    }
}