package ru.voodster.otuslesson

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.voodster.otuslesson.db.FilmEntity
import ru.voodster.otuslesson.di.DaggerViewModelComponent
import ru.voodster.otuslesson.ext.SingleLiveEvent


class FilmListViewModel  : ViewModel() {
    private val filmsComponent= DaggerViewModelComponent.builder().build()
    private val filmsRepository = filmsComponent.repos()

    init {
        Log.d("viewModel",this.toString())
    }

    companion object {
        const val TAG = "FilmListViewModel"
    }



    private val filmListLiveData = MutableLiveData<List<FilmEntity>>()
    private val favoriteLiveData = MutableLiveData<List<FilmEntity>>()



    val errorMsg = SingleLiveEvent<String>()


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
        filmsRepository.rxGetFilmFromDb(filmid){
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
        filmsRepository.rxGetNetMore( object: FilmsRepository.GetFilmsCallBack{

            override fun onSuccess(films: List<FilmEntity>) {
                filmListLiveData.postValue(films)
            }

            override fun onError(error: String?) {
                errorMsg.postValue("Network error")
                filmsRepository.rxGetDbMore(object: FilmsRepository.GetFilmsCallBack{
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
        filmsRepository.saveCachedFilms()
        filmsRepository.filmListClear()
        filmListLiveData.postValue(listOf(fakeFilm))
    }




    fun onGetFavFromCache(){
        Log.d(TAG,"onGetFavFromDatabase")
        favoriteLiveData.postValue(filmsRepository.currentFilmList.filter { it.fav })
    }

    fun saveDb(){
        Log.d(TAG,"saveDb")
        filmsRepository.saveCachedFavorites()
        filmsRepository.saveCachedFilms()

    }


    fun saveFav(){
        Log.d(TAG,"saveFav")
        filmsRepository.saveCachedFavorites()
    }




    fun itemChanged(film: FilmEntity) {
        filmsRepository.itemChange(film)
    }


}