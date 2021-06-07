package ru.voodster.otuslesson

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.voodster.otuslesson.api.Db
import ru.voodster.otuslesson.api.FilmModel
import ru.voodster.otuslesson.api.FilmsInteractor

class FilmListViewModel() : ViewModel() {
    init {
        Log.d("viewModel",this.toString())
       // listFilms = LivePagedListBuilder(Db.f)
    }

    companion object {
        const val TAG = "FilmListViewModel"
    }


    private val filmListLiveData = MutableLiveData<List<FilmModel>>()
    private val favoriteLiveData = MutableLiveData<List<FilmModel>>()
    private val errorLiveData = MutableLiveData<String>()


    private val filmsInteractor = App.instance!!.filmsInteractor

    val favorites : LiveData<List<FilmModel>>
        get() = favoriteLiveData

    val films : LiveData<List<FilmModel>>
        get() = filmListLiveData

    val error: LiveData<String>
        get() = errorLiveData


    /**
     * On update from server
     *
     */
    fun onUpdateFromServer() {
        Log.d(TAG,"onUpdateFromServer")
        filmsInteractor.getFilms( object : FilmsInteractor.GetFilmsCallBack {
            override fun onSuccess(filmList: List<FilmModel>) {
                Log.d("filmsInteractor", "success")
                filmListLiveData.postValue(Db.cachedOrFakeFilmList)
            }
            override fun onError(error: String) {
                errorLiveData.postValue(error)
                Log.d("filmsInteractor", "Data Error")
            }
        })
    }

    /**
     * On get data from database
     *  Get Initial Data to Cached LiveData
     *
     */
    fun onGetDataFromDatabase(){
        Log.d(TAG,"onGetDataFromDatabase")
        Db.loadInitial()
        filmListLiveData.postValue(Db.cachedOrFakeFilmList)
    }

    /**
     * Load more
     * Get more Data to Cached LiveData
     */
    fun loadMore(){
        Log.d(TAG,"loadMore")
        Db.loadMore()
        filmListLiveData.postValue(Db.cachedOrFakeFilmList)
    }


    fun onGetFavFromDatabase(){
        Log.d(TAG,"onGetDataFromDatabase")
        Db.loadInitialFav()
        favoriteLiveData.postValue(Db.FakeOrFavoriteList)
    }
    fun loadMoreFav(){
        Log.d(TAG,"loadMore")
        Db.loadMoreFav()
        favoriteLiveData.postValue(Db.FakeOrFavoriteList)
    }




}