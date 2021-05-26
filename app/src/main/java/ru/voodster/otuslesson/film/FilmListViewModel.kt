package ru.voodster.otuslesson.film

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.voodster.otuslesson.App
import ru.voodster.otuslesson.api.Db
import ru.voodster.otuslesson.api.FilmModel
import ru.voodster.otuslesson.api.FilmsInteractor

class FilmListViewModel() : ViewModel() {
    init {
        Log.d("viewModel",this.toString())
    }

    companion object {
        const val TAG = "FilmListViewModel"
    }


    private val filmListLiveData = MutableLiveData<List<FilmModel>>()
    private val errorLiveData = MutableLiveData<String>()

    private val filmsInteractor = App.instance!!.filmsInteractor

    val films : LiveData<List<FilmModel>>
        get() = filmListLiveData

    val error: LiveData<String>
        get() = errorLiveData


    fun onUpdateFromServer() {
        Log.d(TAG,"onUpdateFromServer")
        filmsInteractor.getFilms( object : FilmsInteractor.GetFilmsCallBack {
            override fun onSuccess(filmList: List<FilmModel>) {
                Log.d("filmsInteractor", "success")
                Db.insertFilmList(filmList)
                filmListLiveData.postValue(Db.cachedOrFakeFilmList)
            }
            override fun onError(error: String) {
                errorLiveData.postValue(error)
                Log.d("filmsInteractor", "Data Error")
            }
        })
    }

    fun onGetDataFromDatabase(){
        Log.d(TAG,"onGetDataFromDatabase")
        Db.getFilmList()
          filmListLiveData.postValue(Db.cachedOrFakeFilmList)
    }

    fun setFav(film: FilmModel){

    }



}