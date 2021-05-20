package ru.voodster.otuslesson.api

class FilmsData {

    private val fakeFilm = FilmModel(1,"fake","fake","fake",1,false)
    private val filmList = ArrayList<FilmModel>()
    private val fakeList  = arrayListOf(fakeFilm, fakeFilm, fakeFilm)


    val cachedOrFakeFilmList: List<FilmModel> // тоже самое но для таблицы(списка)
        get() = if (filmList.size > 0 )
            filmList
        else
            fakeList


    fun addToTable(_filmList : List<FilmModel>){
        this.filmList.clear()
        this.filmList.addAll(_filmList)
    }
}