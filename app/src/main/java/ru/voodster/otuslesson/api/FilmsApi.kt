package ru.voodster.otuslesson.api

import retrofit2.Call
import retrofit2.http.*
import ru.voodster.otuslesson.db.FilmModel

interface FilmsApi {


    @GET("/get")
    fun getInitial():Call<List<FilmModel>>

    @GET("/get?")
    fun getMore(@Query("start")start:Int,@Query("end") end:Int):Call<List<FilmModel>>
}