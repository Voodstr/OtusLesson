package ru.voodster.otuslesson.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import kotlinx.coroutines.delay
import ru.voodster.otuslesson.App

object Db {

    const val TAG = "Db"

    private var INSTANCE: FilmsRoomDatabase? = null

    private val fakeFilm = FilmModel()
    private val fakeList = arrayListOf(fakeFilm)

    var currentFilmList = ArrayList<FilmModel>()
    val cachedOrFakeFilmList: List<FilmModel> // если данных нет то возвращает пустой список
        get() = if (currentFilmList.size > 0)
            currentFilmList
        else
            fakeList

    var currentFavList = ArrayList<FilmModel>()
    val FakeOrFavoriteList: List<FilmModel> // если данных нет то возвращает пустой список
        get() = if (currentFavList.size > 0)
            currentFavList
        else
            fakeList




    /**
     * Get instance
     *
     * @return FilmsRoomDatabase?
     */
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

    /**
     * Update all from server
     *
     * @param filmList
     */

    fun updateAllFromServer(filmList: List<FilmModel>) {
        Log.d(TAG, "updateAllFromServer : $filmList")
        this.currentFilmList.clear()
        this.currentFilmList.addAll(filmList)
        INSTANCE?.transactionExecutor?.execute {
            getInstance()?.getFilmsDao()?.deleteAll()
            getInstance()?.getFilmsDao()?.insertAll(*filmList.toTypedArray())
        }
    }


    /**
     * Change favorite in Database
     *
     * @param film
     */

    private fun changeDbFav(film: FilmModel) {
        Log.d(TAG, "changeDbFav")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.update(film)
        }
    }


    /**
     * Press favorite
     *
     * @param film
     */
    fun pressFav(film: FilmModel) {
        Log.d(TAG, "pressFav")
        currentFilmList.forEachIndexed { index, _ ->
            if (currentFilmList[index].rowID == film.rowID) {
                currentFilmList[index].fav = !currentFilmList[index].fav
                changeDbFav(film)
            }

        }
    }


    /**
     * Load initial
     *   // загрузка из базы  стартовых данных
     */


    fun loadInitial() {
        Log.d(TAG, "loadInitial")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getInitial()?.let {
                currentFilmList.clear()
                currentFilmList.addAll(it.toTypedArray())
            }
        }

    }

    /**
     * Load more
     * загрузка из базы дополнительных данных
     */
    fun loadMore() {
        Log.d(TAG, "loadMore")
        val size = 8 // размер запроса из базы
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getRange(currentFilmList.size, size)?.let {
                currentFilmList.addAll(it.toTypedArray())
            }
        }
    }

    /**
     * Load initial favorites
     *
     */

    fun loadInitialFav() {
        Log.d(TAG, "loadInitialFav: ")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getFavInitial()?.let {
                currentFavList.clear()
                Log.d(TAG, it.toString())
                currentFavList.addAll(it.toTypedArray())
            }
        }
        Log.d(TAG, currentFavList.toString())
    }

    /**
     * Load more favorites
     *
     */
    fun loadMoreFav() {
        Log.d(TAG, "loadMoreFav")
        val size = 8 //  размер запроса из базы
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getFavRange(currentFavList.size, size)?.let {
                currentFavList.addAll(it.toTypedArray())
            }
        }
    }


    fun destroyInstance() {
        INSTANCE?.close()
        INSTANCE = null
    }


    // Получить из базы весь список
    /*
    fun getFilmList() {
        Log.d(TAG, "getFilmList")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getAll()?.let {
                currentFilmList.clear()
                currentFilmList.addAll(it.toTypedArray()) }
        }
    }


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

}