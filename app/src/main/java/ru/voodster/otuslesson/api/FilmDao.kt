package ru.voodster.otuslesson.api

import android.util.Log
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface FilmDao {

    @Query("SELECT * FROM films_table ")
    fun getAll(): List<FilmModel>

    @Query("SELECT * FROM films_table WHERE fav ORDER BY rowID ASC LIMIT 8")
    fun getFavInitial():List<FilmModel>

    @Query("SELECT * FROM films_table WHERE fav ORDER BY rowID ASC LIMIT :size OFFSET :start ")
    fun getFavRange(start:Int, size:Int):List<FilmModel>

    @Query("SELECT * FROM films_table ORDER BY rowID ASC LIMIT 8")
    fun getInitial():List<FilmModel>

    @Query("SELECT * FROM films_table ORDER BY rowID ASC LIMIT :size OFFSET :start")
    fun getRange(start:Int, size:Int):List<FilmModel>

    @Update
    fun update(film: FilmModel)




    @Query(
        "DELETE FROM films_table")
    fun deleteAll()


    @Insert
    fun insertAll(vararg films: FilmModel)

    @Delete
    fun delete(film: FilmModel)

}