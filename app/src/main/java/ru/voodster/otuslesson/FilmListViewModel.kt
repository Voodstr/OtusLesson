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

    // Запрос данных сервера, кладем в БД и кэш
    fun onUpdateFromServer() {
        Log.d(TAG,"onUpdateFromServer")
        filmsInteractor.getFilms( object : FilmsInteractor.GetFilmsCallBack {
            override fun onSuccess(filmList: List<FilmModel>) {
                Log.d("filmsInteractor", "success")
                Db.addToCache(filmList)
                Db.writeToDbFromCache()
                filmListLiveData.postValue(Db.cachedOrFakeFilmList)
            }
            override fun onError(error: String) {
                errorLiveData.postValue(error)
                Log.d("filmsInteractor", "Data Error")
            }
        })
    }

    //Тащим данные из БД в кэш, а потом в LiveData
    fun onGetDataFromDatabase(){
        Log.d(TAG,"onGetDataFromDatabase")
        Db.getFilmList()
        filmListLiveData.postValue(Db.cachedOrFakeFilmList)
    }


}