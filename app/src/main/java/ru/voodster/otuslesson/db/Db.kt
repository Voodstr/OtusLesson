package ru.voodster.otuslesson.db

import android.util.Log
import androidx.room.Room
import ru.voodster.otuslesson.App

object Db {

    const val TAG = "Db"

    private var INSTANCE: FilmsRoomDatabase? = null

    private val fakeFilm = FilmModel(0,"empty","empty","empty",false,0)
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
                favorites.clear()
                favorites.addAll(it.toTypedArray())
            }
        }
        Log.d(TAG, "$favorites")
    }


    private fun parseFav(filmList: List<FilmModel>):List<FilmModel>{
        loadFavoriteIDs()
        Log.d(TAG, "parseFav")
        Log.d(TAG, "favorites = $favorites")
        filmList.forEach {
            it.fav = favorites.contains(it.id)
        }
        return filmList
    }

    fun saveInitialFromServer(filmList: List<FilmModel>){
        Log.d(TAG, "saveInitialFromServer : ${parseFav(filmList)}")
        currentFilmList.clear()
        val parsed = parseFav(filmList)
        currentFilmList.addAll(parsed)
        Log.d(TAG, "saveCachedFilms : $currentFilmList")
        INSTANCE?.transactionExecutor?.execute {
            getInstance()?.getFilmsDao()?.deleteAll()
            getInstance()?.getFilmsDao()?.insertAll(*currentFilmList.toTypedArray())
        }
    }

    fun saveMoreFromServer(filmList: List<FilmModel>){
        Log.d(TAG, "saveMoreFromServer : $filmList")
        val parsed = parseFav(filmList)
        currentFilmList.addAll(parsed)
        Log.d(TAG, "saveMoreFilms")
        INSTANCE?.transactionExecutor?.execute {
            getInstance()?.getFilmsDao()?.insertAll(*filmList.toTypedArray())
        }
    }

    fun saveCachedFilms(){
        Log.d(TAG, "saveCachedFilms")
        INSTANCE?.transactionExecutor?.execute {
            getInstance()?.getFilmsDao()?.deleteAll()
            getInstance()?.getFilmsDao()?.insertAll(*currentFilmList.toTypedArray())
        }
    }
    fun saveMoreFilms(filmList: List<FilmModel>){
        Log.d(TAG, "saveCachedFilms")
        INSTANCE?.transactionExecutor?.execute {
            getInstance()?.getFilmsDao()?.insertAll(*filmList.toTypedArray())
        }
    }

    fun saveFavorites(){
        Log.d(TAG, "saveFavorites")
        val favoritesList = ArrayList<UserFavorites>()
        favorites.forEach { favoritesList.add(UserFavorites(it)) }
        INSTANCE?.transactionExecutor?.execute {
            getInstance()?.getFilmsDao()?.deleteAllFavorites()
            getInstance()?.getFilmsDao()?.insertFavorites(favoritesList)
            Log.d(TAG, "$favoritesList")
        }
    }


    private fun updateFavorites(film: FilmModel){
        Log.d(TAG, "updateFavorites")
        Log.d(TAG, favorites.toString())
        if (film.fav){
            favorites.remove(film.id)
        }else {
            favorites.add(film.id)
        }
        Log.d(TAG, favorites.toString())
    }

    fun pressHeart(film: FilmModel) {
        Log.d(TAG, "pressHeart")
        Log.d(TAG, film.id.toString())
        updateFavorites(film)
        currentFilmList.forEachIndexed { index, _ ->
            if (currentFilmList[index].id == film.id) {
                currentFilmList[index].fav = !currentFilmList[index].fav
            }
        }
    }


    fun pressHeartFav(film: FilmModel){
        Log.d(TAG, "pressHeartFav")
        Log.d(TAG, film.id.toString())
        updateFavorites(film)
        currentFavList.forEachIndexed { index, _ ->
            if (currentFavList[index].id == film.id) {
                currentFavList[index].fav = !currentFavList[index].fav
            }
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

    fun loadFav() {
        Log.d(TAG, "loadFav: ")
        currentFavList.clear()
        currentFavList.addAll(currentFilmList.filter { it.fav })

        Log.d(TAG, "currentFavList: $currentFavList")
    }




    fun destroyInstance() {
        INSTANCE?.close()
        INSTANCE = null
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