package ru.voodster.otuslesson.db

import android.util.Log
import androidx.room.Room
import ru.voodster.otuslesson.App

object Db {

    const val TAG = "Db"

    private var INSTANCE: FilmsRoomDatabase? = null

    private val fakeFilm = FilmModel()
    private val fakeList = arrayListOf(fakeFilm)

    private var favorites = ArrayList<Int>()

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

    fun loadFavoriteIDs() {
        Log.d(TAG, "loadFavoriteIDs")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getUserFavorites()?.let {
                Log.d(TAG, "getUserFavorites = $it")
                favorites.clear()
                favorites.addAll(it.toTypedArray())
            }
        }
        Log.d(TAG, "$favorites")
    }


    private fun parseFav(filmList: List<FilmModel>):List<FilmModel>{
        Log.d(TAG, "parseFav")
        Log.d(TAG, "favorites = $favorites")
        filmList.forEach { it.fav = favorites.contains(it.id) }
        return filmList
    }

    fun saveInitialFromServer(filmList: List<FilmModel>){
        Log.d(TAG, "saveInitialFromServer : ${parseFav(filmList)}")
        currentFilmList.clear()
        val parsed = parseFav(filmList)
        currentFilmList.addAll(parsed)
        Log.d(TAG, "saveInitialFromServer : $currentFilmList")
    }

    fun saveMoreFromServer(filmList: List<FilmModel>){
        Log.d(TAG, "saveMoreFromServer : $filmList")
        val parsed = parseFav(filmList)
        currentFilmList.addAll(parsed)
    }

    fun saveCachedFilms(){
        INSTANCE?.transactionExecutor?.execute {
            getInstance()?.getFilmsDao()?.deleteAll()
            getInstance()?.getFilmsDao()?.insertAll(*currentFilmList.toTypedArray())
        }
    }



    /**
     * Press favorite
     *
     * @param film
     */
    fun pressFav(film: FilmModel) {
        Log.d(TAG, "pressFav")
        Log.d(TAG, film.id.toString())
        if (film.fav){
            favorites.remove(film.id)
            rmFav(film.id)
            currentFavList.remove(film)
        }else {
            favorites.add(film.id)
            addFav(film.id)
            currentFavList.add(film)
        }
        currentFilmList.forEachIndexed { index, _ ->
            if (currentFilmList[index].id == film.id) {
                currentFilmList[index].fav = !currentFilmList[index].fav
            }

        }
    }


    private fun addFav(id: Int){
        Log.d(TAG, "addFav")
        INSTANCE?.queryExecutor?.execute {
                getInstance()?.getFilmsDao()?.addFav(id)
            }
    }

    private fun rmFav(id: Int){
        Log.d(TAG, "rmFav")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.rmFav(id)
        }
    }





    /**
     * Load initial
     *   // загрузка из базы  стартовых данных
     */

    fun loadInitialFromDatabase() {
        Log.d(TAG, "loadInitial")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getInitial()?.let {
                currentFilmList.clear()
                currentFilmList.addAll(parseFav(it))
            }
        }

    }

    /**
     * Load more
     * загрузка из базы дополнительных данных
     */
    fun loadMoreFromDatabase() {
        Log.d(TAG, "loadMore")
        val size = 8 // размер запроса из базы
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getRange(currentFilmList.size, size)?.let {
                currentFilmList.addAll(parseFav(it))
            }
        }
    }

    /**
     * Load initial favorites
     *
     */

    fun loadInitialFav() {
        loadFavoriteIDs()
        Log.d(TAG, "loadInitialFav: ")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getFilmsJoinFavorites()?.let {
                currentFavList.clear()
                currentFavList.addAll(parseFav(it))
            }
        }
        Log.d(TAG, currentFavList.toString())
    }

    /*
    /**
     * Load more favorites
     *
     */
    fun loadMoreFav() {
        loadFavoriteIDs()
        Log.d(TAG, "loadMoreFav")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getFilmsJoinFavorites(currentFavList.size, 8)?.let {
                currentFavList.addAll(parseFav(it))
            }
        }
    }
    */


    fun destroyInstance() {
        INSTANCE?.close()
        INSTANCE = null
    }



    /*
/**
 * Update all from server
 *
 * @param filmList
 */

fun updateAllFromServer(filmList: List<FilmModel>) {
    Log.d(TAG, "updateAllFromServer : $filmList")
    currentFilmList.clear()
    currentFilmList.addAll(filmList)
    INSTANCE?.transactionExecutor?.execute {
        getInstance()?.getFilmsDao()?.deleteAll()
        getInstance()?.getFilmsDao()?.insertAll(*filmList.toTypedArray())
    }
}
*/


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