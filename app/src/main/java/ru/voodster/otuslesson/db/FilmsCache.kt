package ru.voodster.otuslesson.db

import android.util.Log
import androidx.room.Room
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.voodster.otuslesson.App
import ru.voodster.otuslesson.api.FilmModel

object FilmsCache {

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

    private var currentFilm:FilmEntity? = null



    private val favorites = ArrayList<Int>()

    val obs: SingleObserver<List<FilmEntity>> by lazy{
        object: SingleObserver<List<FilmEntity>>{
            override fun onSubscribe(d: Disposable?) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(t: List<FilmEntity>?) {
                TODO("Not yet implemented")
            }

            override fun onError(e: Throwable?) {
                TODO("Not yet implemented")
            }

        }
    }

    private val favoritesObserver by lazy {
        object: Observer<List<UserFavorites>>{

            override fun onComplete() {
                saveCachedFavorites()
            }

            override fun onNext(t: List<UserFavorites>?) {
                if (t != null) {
                    favorites.clear()
                    t.forEach { favorites.add(it.filmID) }
                }
            }

            override fun onError(e: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun onSubscribe(d: Disposable?) {
                TODO("Not yet implemented")
            }
        }
    }




    fun init(){

        INSTANCE?.getFilmsDao()?.getUserFavoritesRx()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({result->
                favorites.clear()
                result.forEach { uf->
                    favorites.add(uf.filmID)
                }
            },{error->
                Log.d(TAG,"favorites error: $error")
                throw (error)
            },{

            })

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


    fun saveCachedFilms(){
        Log.d(TAG, "saveCachedFilms")
        INSTANCE?.queryExecutor?.execute {
            getInstance()?.getFilmsDao()?.insertAll(*currentFilmList.toTypedArray())
        }
    }

    fun saveCachedFavorites(){
        Log.d(TAG, "saveFavorites")
        val favoritesList = ArrayList<UserFavorites>()
        if (!currentFilmList.isNullOrEmpty()){
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







    fun rxGetNetMore( callback: GetFilmsCallBack){
        App.instance!!.filmsApi.getMoreRx(currentFilmList.size+1,20)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                    result ->
                        val mapper = FilmMapper()
                        currentFilmList.addAll(mapper.map(result))
                        callback.onSuccess(currentFilmList)
                        Log.d("Result", "Is === $result ")
            }, { error ->
                callback.onError(error.localizedMessage)
            })

    }



    fun rxGetDbMore(callback: GetFilmsCallBack){
        Log.d(TAG,"rxGetDbMore")
        INSTANCE!!.getFilmsDao().getRangeRx(currentFilmList.size,10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    result ->
                        currentFilmList.addAll(result)
                        callback.onSuccess(currentFilmList)
                        Log.d("Result", "Is === $result ")
            }, { error ->
                callback.onError(error.localizedMessage)
            })

    }
    fun  rxGetFilm(filmid: Int,callback: (FilmEntity?) -> Unit){
        Log.d(TAG,"rxGetFilm")
        INSTANCE?.getFilmsDao()!!.getFilmRx(filmid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                    r->
                run {
                    callback(r)
                }
            }
    }



    fun filmListClear(){
        currentFilmList.clear()
    }



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



    fun destroyInstance() {
        INSTANCE?.close()
        INSTANCE = null
    }


    interface GetFilmsCallBack {
        fun onSuccess(films:List<FilmEntity>)
        fun onError(error: String?)
    }

}