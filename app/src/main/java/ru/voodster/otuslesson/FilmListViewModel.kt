package ru.voodster.otuslesson

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.voodster.otuslesson.db.Db
import ru.voodster.otuslesson.api.FilmsInteractor
import ru.voodster.otuslesson.db.FilmModel

class FilmListViewModel : ViewModel() {
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


    fun onGetFromServer() {
        Log.d(TAG,"onGetFromServer")
        onGetDataFromDatabase()
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
        loadMoreFromDatabase()
        Log.d(TAG,"${Db.currentFilmList.size}, ${Db.currentFilmList.size.plus(10)}")
        filmsInteractor.getMore( Db.currentFilmList.size, Db.currentFilmList.size.plus(10)  ,object : FilmsInteractor.GetMoreFilmsCallBack {
            override fun onSuccess(filmList: List<FilmModel>) {
                Log.d("filmsInteractor", "onSuccessMore")
                filmListLiveData.postValue(Db.cachedOrFakeFilmList)
            }
            override fun onError(error: String) {
                // TODO пусть тащит из базы если ответа не было
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

    /*

    /**
     * On get data from database
     *  Get Initial Data to Cached LiveData
     *
     */



    /**
     * Load more
     * Get more Data to Cached LiveData
     */
*/

    fun onGetFavFromDatabase(){
        Log.d(TAG,"onGetFavFromDatabase")
        Db.loadFav()
        favoriteLiveData.postValue(Db.FakeOrFavoriteList)
    }
    /*
    fun loadMoreFav(){
        Log.d(TAG,"loadMoreFav")
        Db.loadMoreFav()
        favoriteLiveData.postValue(Db.FakeOrFavoriteList)
    }

     */

    fun saveDb(){
        Log.d(TAG,"saveDb")
        Db.saveCachedFilms()
        Db.saveFavorites()
    }

    fun loadDb(){
        Log.d(TAG,"loadDb")
        Db.loadFavoriteIDs()
        Db.loadInitialFromDatabase()
    }

    fun saveFav(){
        Log.d(TAG,"saveFav")
        Db.saveFavorites()
    }


}