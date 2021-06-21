package ru.voodster.otuslesson

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.voodster.otuslesson.db.FilmsCache
import ru.voodster.otuslesson.db.FilmEntity

class FilmListViewModel : ViewModel() {
    init {
        Log.d("viewModel",this.toString())
       // listFilms = LivePagedListBuilder(Db.f)\

    }

    companion object {
        const val TAG = "FilmListViewModel"
    }


    private val filmListObserver by lazy {
        object:Observer<List<FilmEntity>> {

            override fun onChanged(t: List<FilmEntity>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private val filmListLiveData = MutableLiveData<List<FilmEntity>>()
    private val favoriteLiveData = MutableLiveData<List<FilmEntity>>()



    val errorMsg = SingleLiveEvent<String>()

    private val filmsInteractor = App.instance!!.filmsInteractor

    val favorites : LiveData<List<FilmEntity>>
        get() = favoriteLiveData

    val films : LiveData<List<FilmEntity>>
        get() = filmListLiveData


    private val fakeFilm = FilmEntity(0,"empty","empty","empty",false,0,false)
    private val watchFilmLiveData = MutableLiveData<FilmEntity>()
    val watchFilm : LiveData<FilmEntity>
        get() = watchFilmLiveData


    fun getFilmRx(filmid: Int){
        Log.d(TAG,"getFilmRx")
        FilmsCache.rxGetFilm(filmid){
            if (it != null) {
                watchFilmLiveData.postValue(it)
            }
        }
    }

    /**
     * On update from server
     *
     */

    fun getMoreFilmsRx(){
        Log.d(TAG,"getMoreFilmsRx")
        FilmsCache.rxGetNetMore( object: FilmsCache.GetFilmsCallBack{

            override fun onSuccess(films: List<FilmEntity>) {
                filmListLiveData.postValue(films)
            }

            override fun onError(error: String?) {
                errorMsg.postValue("Network error")
                FilmsCache.rxGetDbMore(object: FilmsCache.GetFilmsCallBack{
                    override fun onSuccess(films: List<FilmEntity>) {
                        filmListLiveData.postValue(films)
                    }

                    override fun onError(error: String?) {
                        errorMsg.postValue(error!!)
                    }
                }

                )
            }
        })
    }

    fun update(){
        Log.d(TAG,"update")
        FilmsCache.saveCachedFilms()
        FilmsCache.filmListClear()
        filmListLiveData.postValue(listOf(fakeFilm))
    }




    fun onGetFavFromDatabase(){
        Log.d(TAG,"onGetFavFromDatabase")
        favoriteLiveData.postValue(FilmsCache.currentFilmList.filter { it.fav })
    }

    fun saveDb(){
        Log.d(TAG,"saveDb")
        FilmsCache.saveCachedFavorites()
        FilmsCache.saveCachedFilms()

    }


    fun saveFav(){
        Log.d(TAG,"saveFav")
        FilmsCache.saveCachedFavorites()
    }




    fun itemChanged(film: FilmEntity) {
        FilmsCache.itemChange(film)
    }


}