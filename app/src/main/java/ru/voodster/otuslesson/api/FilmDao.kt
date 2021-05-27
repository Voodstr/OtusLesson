package ru.voodster.otuslesson.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.voodster.otuslesson.api.FilmModel

@Dao
interface FilmDao {
    @Query(
        "SELECT * " +
                "FROM films_table")
    fun getAll(): List<FilmModel>

    @Query(
        "SELECT * " +
                "FROM films_table " +
                "WHERE fav " +
                "LIKE true"
    )
    fun getFav(): List<FilmModel>

    @Query(
        "UPDATE films_table " +
                "SET fav = true " +
                "WHERE id = :id ")
    fun fav(id : Int)

    @Query(
        "UPDATE films_table " +
                "SET Fav = false " +
                "WHERE id = :id "
    )
    fun unFav(id : Int)



    @Query(
        "DELETE FROM films_table")
    fun deleteAll()


    @Insert
    fun insertAll(vararg films: FilmModel)

    @Delete
    fun delete(film: FilmModel)

}