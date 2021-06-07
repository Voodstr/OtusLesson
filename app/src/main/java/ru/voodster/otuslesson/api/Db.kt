package ru.voodster.otuslesson.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import ru.voodster.otuslesson.App

object Db {

    const val TAG = "Db"

    private var INSTANCE: FilmsRoomDatabase? = null

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


    fun updateAllFromServer(filmList : List<FilmModel>){
        Log.d(TAG, "updateAllFromServer : $filmList")
        this.currentFilmList.clear()
        this.currentFilmList.addAll(filmList)
        INSTANCE?.transactionExecutor?.execute {
            getInstance()?.getFilmsDao()?.deleteAll()
            getInstance()?.getFilmsDao()?.insertAll(*filmList.toTypedArray())
        }
    }

    /*

    // Добавление списка в кеш
    // p.s вызывается при ответе от серверва
    fun addToCache(filmList : List<FilmModel>){
        Log.d(TAG, "addToCache : $filmList")
        this.currentFilmList.clear()
        this.currentFilmList.addAll(filmList)
        Log.d(TAG, "InCache : $currentFilmList")
    }

    // Добавляем список в БД
    fun writeToDbFromCache(){
        Log.d(TAG, "writeToDbFromCache")

    }
*/

    //Список избранного

    //fun favFilmList() = currentFilmList.filter { it.fav }


    // однозначно не оптимально перебирать весь список когда нужен один конкретный элемент
    // меняет значение поля fav(Boolean) на обратное

    private fun changeDbFav(film: FilmModel){
        Log.d(TAG, "changeDbFav")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.update(film)
        }
    }
    fun pressFav(film: FilmModel){
        Log.d(TAG, "pressFav")
        currentFilmList.forEachIndexed { index, _ ->
            if (currentFilmList[index].rowID == film.rowID) {
                currentFilmList[index].fav = !currentFilmList[index].fav
                changeDbFav(film)
            }

        }
    }

    fun loadList(start:Int,size:Int):List<FilmModel>{
        return currentFilmList.subList(start,start.plus(size))
    }


    fun loadInitial(){
        Log.d(TAG, "loadInitial")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getInitial()?.let {
                currentFilmList.clear()
                currentFilmList.addAll(it.toTypedArray()) }
        }
        //TODO загрузка из базы  стартовых данных
    }

    fun loadMore(){
        Log.d(TAG, "loadMore")
        val size = 10 // TODO размер запроса из базы
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getRange(currentFilmList.size, size)?.let {
                currentFilmList.addAll(it.toTypedArray()) }
        }
        //TODO загрузка из базы дополнительных данных
    }




    // Получить из базы весь список
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