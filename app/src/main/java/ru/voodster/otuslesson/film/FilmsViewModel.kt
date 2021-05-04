package ru.voodster.otuslesson.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.`interface`.FilmsLiveData

class FilmsViewModel : ViewModel() {

    private val filmsLiveData = MutableLiveData<ArrayList<FilmItem>>()
    val films: LiveData<ArrayList<FilmItem>>
        get() = filmsLiveData

    fun onFavBtnClick(){ //TODO метод добавления в избранное

    }

}