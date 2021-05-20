package ru.voodster.otuslesson.film

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.voodster.otuslesson.App
import ru.voodster.otuslesson.api.FilmModel
import ru.voodster.otuslesson.api.FilmsInteractor

class FilmListViewModel : ViewModel() {
    init {
        Log.d("viewModel",this.toString())
    }


    private val filmListLiveData = MutableLiveData<List<FilmModel>>()
    private val errorLiveData = MutableLiveData<String>()

    private val filmsInteractor = App.instance!!.filmsInteractor

    val films : LiveData<List<FilmModel>>
        get() = filmListLiveData

    val error: LiveData<String>
        get() = errorLiveData

    fun onGetData() {
        filmsInteractor.getFilms( object : FilmsInteractor.GetFilmsCallBack {
            override fun onSuccess(filmlist: List<FilmModel>) {
                Log.d("TABLE onGetTable", "success")
                filmListLiveData.postValue(filmlist)
            }

            override fun onError(error: String) {
                errorLiveData.postValue(error)
                Log.d("Network access", "Data Error")
            }
        })
    }

}