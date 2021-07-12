package ru.voodster.otuslesson.db

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface FilmDao {

   // @Query("SELECT * FROM films_table ")
   // fun getAll(): List<FilmModel>


    @Query("SELECT * FROM films_table ORDER BY rowID ASC LIMIT 10")
    fun getInitial():List<FilmEntity>

    @Query("SELECT * FROM films_table ORDER BY rowID ASC LIMIT :size OFFSET :start")
    fun getRange(start:Int, size:Int):List<FilmEntity>

    @Query("SELECT * FROM films_table where id = :filmid")
    fun getFilm(filmid:Int):FilmEntity


    @Query("SELECT * FROM films_table ORDER BY rowID ASC LIMIT :size OFFSET :start")
    fun getRangeRx(start:Int, size:Int):Single<List<FilmEntity>>

    @Query("SELECT * FROM films_table where id = :filmid")
    fun getFilmRx(filmid: Int):Single<FilmEntity>

    @Query("Select filmID from user_favorites order by filmID")
    fun getUserFavoritesRx():Flowable<List<UserFavorites>>



    @Query("DELETE FROM user_favorites")
    fun deleteAllFavorites()
//entity = UserFavorites::class,
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favorites: List<UserFavorites>)

    @Query("DELETE FROM films_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg films: FilmEntity)

    @Delete
    fun delete(film: FilmEntity)

}