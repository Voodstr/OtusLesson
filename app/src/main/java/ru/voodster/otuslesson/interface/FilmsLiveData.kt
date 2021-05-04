package ru.voodster.otuslesson.`interface`

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.FilmList

class FilmsLiveData : LiveData<ArrayList<FilmItem>>() {

    private var liveDataContext: Context? = null

    private fun NetworkLiveData(context: Context) {
        liveDataContext = context

    }

    override fun onActive() {
        super.onActive() //TODO действие при подписке на данные
        value = FilmList
    }


    override fun onInactive() {
        super.onInactive() // TODO действие когда все отписались
    }

    private fun prepareReciever(context : Context){ // TODO метод получения данных

    }

}