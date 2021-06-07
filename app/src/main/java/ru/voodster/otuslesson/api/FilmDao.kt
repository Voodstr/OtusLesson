package ru.voodster.otuslesson.api

import android.util.Log
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface FilmDao {
    @Query(
        "SELECT * FROM films_table")
    fun getAll(): List<FilmModel>

    @Query("SELECT * FROM films_table ORDER BY rowID ASC")
    fun filmsByRowId(): DataSource.Factory<Int?, FilmModel?>?


    @Query("SELECT * FROM films_table LIMIT 10")
    fun getInitial():List<FilmModel>

    @Query("SELECT * FROM films_table LIMIT :size OFFSET :start")
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