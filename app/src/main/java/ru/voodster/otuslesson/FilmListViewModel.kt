package ru.voodster.otuslesson

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.voodster.otuslesson.api.FilmModel
import ru.voodster.otuslesson.db.Db
import ru.voodster.otuslesson.api.FilmsInteractor
import ru.voodster.otuslesson.db.FilmEntity

class FilmListViewModel : ViewModel() {
    init {
        Log.d("viewModel",this.toString())
       // listFilms = LivePagedListBuilder(Db.f)
    }

    companion object {
        const val TAG = "FilmListViewModel"
    }


    private val filmListLiveData = MutableLiveData<List<FilmEntity>>()
    private val favoriteLiveData = MutableLiveData<List<FilmEntity>>()

    private val errorLiveData = MutableLiveData<String>()



    private val filmsInteractor = App.instance!!.filmsInteractor

    val favorites : LiveData<List<FilmEntity>>
        get() = favoriteLiveData

    val films : LiveData<List<FilmEntity>>
        get() = filmListLiveData

    val error: LiveData<String>
        get() = errorLiveData


    /**
     * On update from server
     *
     */


    fun onGetFromServer() {
        Log.d(TAG,"onGetFromServer")

        filmsInteractor.getInitial( object : FilmsInteractor.GetFilmsCallBack {
            override fun onSuccess(filmList: List<FilmModel>) {
                Log.d(TAG ,"success")
                filmListLiveData.postValue(Db.cachedOrFakeFilmList)
            }
            override fun onError(error: String) {
                errorLiveData.postValue(error)
                Log.d(TAG , "Data Error")
            }
        })
    }

    fun onGetMoreFromServer() {
        Log.d(TAG,"onGetMoreFromServer")

        Log.d(TAG,"${Db.currentFilmList.size}, ${Db.currentFilmList.size.plus(10)}")
        filmsInteractor.getMore( Db.currentFilmList.size, Db.currentFilmList.size.plus(10)  ,object : FilmsInteractor.GetMoreFilmsCallBack {
            override fun onSuccess(filmList: List<FilmModel>) {
                Log.d("filmsInteractor", "onSuccessMore")
                filmListLiveData.postValue(Db.cachedOrFakeFilmList)
            }
            override fun onError(error: String) {
                //
                errorLiveData.postValue(error)
                Log.d("filmsInteractor", "Data Error")
            }
        })
    }

    private fun onGetDataFromDatabase(){
        Log.d(TAG,"onGetDataFromDatabase")
        Db.loadInitialFromDatabase()
        filmListLiveData.postValue(Db.cachedOrFakeFilmList)
    }

     private fun loadMoreFromDatabase(){
        Log.d(TAG,"loadMore")
        Db.loadMoreFromDatabase()
        filmListLiveData.postValue(Db.cachedOrFakeFilmList)
    }



    fun onGetFavFromDatabase(){
        Log.d(TAG,"onGetFavFromDatabase")
        favoriteLiveData.postValue(Db.currentFilmList.filter { it.fav })
    }

    fun saveDb(){
        Log.d(TAG,"saveDb")
        Db.saveFavorites()
        Db.saveCachedFilms()

    }

    fun loadDb(){
        Log.d(TAG,"loadDb")
        Db.loadInitialFromDatabase()
    }

    fun saveFav(){
        Log.d(TAG,"saveFav")
        Db.saveFavorites()
    }



}