package ru.voodster.otuslesson.db

import android.util.Log
import androidx.room.Room
import ru.voodster.otuslesson.App
import ru.voodster.otuslesson.api.FilmModel

object Db {

    const val TAG = "Db"

    private var INSTANCE: FilmsRoomDatabase? = null

    private val fakeFilm = FilmEntity(0,"empty","empty","empty",false,0,false)
    private val fakeList = arrayListOf(fakeFilm)

    var currentFilmList = ArrayList<FilmEntity>()
    val cachedOrFakeFilmList: List<FilmEntity> // если данных нет то возвращает пустой список
        get() = if (currentFilmList.size > 0)
            currentFilmList
        else
            fakeList

    private val favorites = ArrayList<Int>()


    class FilmMapper: BaseMapper<FilmEntity, FilmModel>() {
        override fun reverseMap(entity: FilmEntity?): FilmModel? {
            return if (entity != null){
                (FilmModel(
                    entity.id,
                    entity.img,
                    entity.title,
                    entity.description,
                    entity.likes
                ))
            }else null
        }

        override fun map(model: FilmModel?): FilmEntity? {
            return if (model!=null){
                if (favorites.contains(model.id)){
                    FilmEntity(model.id,model.img,model.title,model.description,true,model.likes)
                }else FilmEntity(model.id,model.img,model.title,model.description,false,model.likes)
            }else null
        }

    }

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
                    /*
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
        favorites.clear()
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getUserFavorites()?.let { UF ->
                UF.forEach { favorites.add(it.filmID) }
            }
        }
        Log.d(TAG, "$favorites")
    }



    fun saveInitialFromServer(filmList: List<FilmModel>){
        Log.d(TAG, "saveInitialFromServer : $filmList")
        val mapper = FilmMapper()
        currentFilmList.clear()
        currentFilmList.addAll(mapper.map(filmList))
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.insertAll(*mapper.map(filmList).toTypedArray())
        }
        Log.d(TAG, "saveCachedFilms : $currentFilmList")
    }

    fun saveMoreFromServer(filmList: List<FilmModel>){
        Log.d(TAG, "saveMoreFromServer : $filmList")
        val mapper = FilmMapper()
        currentFilmList.addAll(mapper.map(filmList))
        Log.d(TAG, "saveMoreFilms")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.insertAll(*mapper.map(filmList).toTypedArray())
        }
    }

    fun saveCachedFilms(){
        Log.d(TAG, "saveCachedFilms")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.insertAll(*currentFilmList.toTypedArray())
        }
    }

    fun saveFavorites(){
        Log.d(TAG, "saveFavorites")
        val favoritesList = ArrayList<UserFavorites>()
        favoritesList.clear()
        favorites.clear()
        currentFilmList.forEach {
            if (it.fav){
                favoritesList.add(UserFavorites(it.id))
                favorites.add(it.id)
            }
        }
        Log.d(TAG, "$favoritesList")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.deleteAllFavorites()
            getInstance()?.getFilmsDao()?.insertFavorites(favoritesList)
        }
    }

    /*
    fun saveFromFavList(){
        Log.d(TAG, "saveFromFavList")
        val favoritesList = ArrayList<UserFavorites>()
        favoritesList.clear()
        currentFavList.forEach {  if (it.fav) favoritesList.add(UserFavorites(it.id))}
        Log.d(TAG, "$favoritesList")
        INSTANCE?.transactionExecutor?.execute {
            getInstance()?.getFilmsDao()?.deleteAllFavorites()
            getInstance()?.getFilmsDao()?.insertFavorites(favoritesList)
        }
    }

     */

/*
    private fun updateFavorites(film: FilmEntity){
        Log.d(TAG, "updateFavorites")
        if (favorites.contains(film.id)){
            favorites.remove(film.id)
        }else{
            favorites.add(film.id)
        }
    }

    fun pressHeart(film: FilmEntity) {
        Log.d(TAG, "pressHeart")
        Log.d(TAG, film.id.toString())
        Log.d(TAG, "updateList")
        /*

        currentFilmList.forEachIndexed { index, _ ->
            if (currentFilmList[index].id == film.id) {
                //Log.d(TAG, index.toString())
                currentFilmList[index].fav = !currentFilmList[index].fav
               // Log.d(TAG, currentFilmList[index].fav.toString())
            }
        }

         */
        //updateFavorites(film)
    }


    fun pressHeartFav(film: FilmEntity){
        Log.d(TAG, "pressHeartFav")
        Log.d(TAG, film.id.toString())
        /*
        currentFavList.forEachIndexed { index, _ ->
            if (currentFavList[index].id == film.id) {
                currentFavList[index].fav = !currentFavList[index].fav
            }
        }

         updateFavorites(film)

         */

    }
*/


    /**
     * Load initial
     *   // загрузка из базы  стартовых данных
     */

    fun loadInitialFromDatabase() {
        Log.d(TAG, "loadInitialFromDatabase")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getInitial()?.let {
                currentFilmList.clear()
                currentFilmList.addAll(it)
            }
        }

    }

    /**
     * Load more
     * загрузка из базы дополнительных данных
     */
    fun loadMoreFromDatabase() {  //TODO не работает, неизвестно почему
        Log.d(TAG, "loadMoreFromDatabase")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.getRange(currentFilmList.size, 10)?.let {
                Log.d("Db getRange","$it")
                currentFilmList.addAll(it)
            }
        }
    }

    /**
     * Load initial favorites
     *
     */
/*
    fun loadFav() {
        Log.d(TAG, "loadFav: ")
        currentFavList.clear()
        currentFavList.addAll(currentFilmList.filter { it.fav })

        Log.d(TAG, "currentFavList: $currentFavList")
    }

 */




    fun destroyInstance() {
        INSTANCE?.close()
        INSTANCE = null
    }

    fun itemChange(film: FilmEntity) {
        currentFilmList.find { it.id == film.id }.apply {
            if (this !=null){
                watch = film.watch
                watchDate = film.watchDate
                fav = film.fav
            }
        }
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