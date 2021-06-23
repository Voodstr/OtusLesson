package ru.voodster.otuslesson

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.voodster.otuslesson.AppModule_ProvideAppFactory.create
import ru.voodster.otuslesson.api.ApiModule_ProvideApiFactory.create
import ru.voodster.otuslesson.api.FilmModel
import ru.voodster.otuslesson.api.FilmsApi
import ru.voodster.otuslesson.db.*
import java.lang.annotation.RetentionPolicy
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton


@Singleton
class FilmsRepository @Inject constructor(
    private val db: FilmsRoomDatabase,
    private val api: FilmsApi
) {


    companion object {
        const val TAG = "Db"
    }



    var currentFilmList = ArrayList<FilmEntity>()

    private val favorites = ArrayList<Int>()


    init {
        DaggerAppComponent.create().inject(this)

        db.getFilmsDao().getUserFavoritesRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                favorites.clear()
                result.forEach { uf ->
                    favorites.add(uf.filmID)
                }
            }, { error ->
                Log.d(TAG, "favorites error: $error")
                throw (error)
            }, {

            })

    }



    fun saveCachedFilms() {
        Log.d(TAG, "saveCachedFilms")
        db.queryExecutor.execute {
           db.getFilmsDao().insertAll(*currentFilmList.toTypedArray())
        }
    }

    fun saveCachedFavorites() {
        Log.d(TAG, "saveFavorites")
        val favoritesList = ArrayList<UserFavorites>()
        if (!currentFilmList.isNullOrEmpty()) {
            favoritesList.clear()
            favorites.clear()
            currentFilmList.forEach {
                if (it.fav) {
                    favoritesList.add(UserFavorites(it.id))
                    favorites.add(it.id)
                }
            }
            Log.d(TAG, "$favoritesList")
            db.queryExecutor.execute {
                db.getFilmsDao().deleteAllFavorites()
                db.getFilmsDao().insertFavorites(favoritesList)
            }
        }
    }


    fun itemChange(film: FilmEntity) {
        currentFilmList.find { it.id == film.id }.apply {
            if (this != null) {
                watch = film.watch
                watchDate = film.watchDate
                fav = film.fav
            }
        }
    }


    fun rxGetNetMore(callback: GetFilmsCallBack) {
        api.getMoreRx(currentFilmList.size + 1, 20)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                val mapper = FilmMapper(favorites)
                currentFilmList.addAll(mapper.map(result))
                callback.onSuccess(currentFilmList)
                Log.d("Result", "Is === $result ")
            }, { error ->
                callback.onError(error.localizedMessage)
            })

    }


    fun rxGetDbMore(callback: GetFilmsCallBack) {
        Log.d(TAG, "rxGetDbMore")
        db.getFilmsDao().getRangeRx(currentFilmList.size, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                currentFilmList.addAll(result)
                callback.onSuccess(currentFilmList)
                Log.d("Result", "Is === $result ")
            }, { error ->
                callback.onError(error.localizedMessage)
            })

    }

    fun rxGetFilmFromDb(filmid: Int, callback: (FilmEntity?) -> Unit) {
        Log.d(TAG, "rxGetFilm")
        db.getFilmsDao().getFilmRx(filmid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { r ->
                run {
                    callback(r)
                }
            }
    }


    fun filmListClear() {
        currentFilmList.clear()
    }


    inner class FilmMapper(private val favorites: List<Int>) : BaseMapper<FilmEntity, FilmModel>() {
        override fun reverseMap(entity: FilmEntity?): FilmModel? {
            return if (entity != null) {
                (FilmModel(
                    entity.id,
                    entity.img,
                    entity.title,
                    entity.description,
                    entity.likes
                ))
            } else null
        }

        override fun map(model: FilmModel?): FilmEntity? {
            return if (model != null) {
                if (favorites.contains(model.id)) {
                    FilmEntity(
                        model.id,
                        model.img,
                        model.title,
                        model.description,
                        true,
                        model.likes
                    )
                } else FilmEntity(
                    model.id,
                    model.img,
                    model.title,
                    model.description,
                    false,
                    model.likes
                )
            } else null
        }

    }


    interface GetFilmsCallBack {
        fun onSuccess(films: List<FilmEntity>)
        fun onError(error: String?)
    }

}