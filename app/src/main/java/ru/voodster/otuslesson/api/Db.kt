package ru.voodster.otuslesson.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import ru.voodster.otuslesson.App

object Db {

    const val TAG = "Db"

    private var INSTANCE: FilmsRoomDatabase? = null
    private var isUpdated = false

    private val fakeFilm = FilmModel()
    var currentFilmList = ArrayList<FilmModel>()
    private val fakeList  = arrayListOf(fakeFilm)


    val cachedOrFakeFilmList: List<FilmModel> // если данных нет то возвращает пустой список
        get() = if (currentFilmList.size > 0 )
            currentFilmList
        else
            fakeList


    fun getInstance(): FilmsRoomDatabase? {
        Log.d(TAG, "getInstance")
        Log.d(TAG, "$INSTANCE")
        if (INSTANCE == null) {
            synchronized(FilmsRoomDatabase::class) {

                INSTANCE = Room.databaseBuilder(
                    App.instance!!.applicationContext,
                    FilmsRoomDatabase::class.java, "db-name.db"
                )
                    /*.allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)*/
                    .addCallback(DbCallback())
                    .build()
            }
        }
        return INSTANCE
    }
    // Добавление списка в кеш
    // p.s вызывается при ответе от серверва
    fun addToCache(_filmList : List<FilmModel>){
        Log.d(TAG, "addToCache : $_filmList")
        this.currentFilmList.clear()
        this.currentFilmList.addAll(_filmList)
    }

    // Добавляем список в БД
    fun writeToDbFromCache(){
        Log.d(TAG, "writeToDbFromCache")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.deleteAll()
            getInstance()?.getFilmsDao()?.insertAll(*currentFilmList.toTypedArray())
        }
    }


    //Список избранного

    //fun favFilmList() = currentFilmList.filter { it.fav }


    // однозначно не оптимально перебирать весь список когда нужен один конкретный элемент
    // меняет значение поля fav(Boolean) на обратное
    fun pressFav(film: FilmModel){
        Log.d(TAG, "pressFav")
        currentFilmList.forEachIndexed { index, _ ->
            if (currentFilmList[index].rowID == film.rowID)
            currentFilmList[index].fav = !currentFilmList[index].fav
        }
        Log.d(TAG, "${currentFilmList.filter { it.fav }}")
        writeToDbFromCache()
    }

    fun getFilmList() {
        Log.d(TAG, "getFilmList")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getAll()?.let {
                currentFilmList.clear()
                currentFilmList.addAll(it.toTypedArray()) }
        }
    }

    fun destroyInstance() {
        INSTANCE?.close()
        INSTANCE = null
    }

}