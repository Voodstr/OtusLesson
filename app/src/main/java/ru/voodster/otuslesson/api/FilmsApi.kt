package ru.voodster.otuslesson.api

import retrofit2.Call
import retrofit2.http.*
import ru.voodster.otuslesson.db.FilmEntity

interface FilmsApi {


    @GET("/films.lsp")
    fun getInitial():Call<List<FilmModel>>

    @GET("/films.lsp?")
    fun getMore(@Query("start")start:Int,@Query("stop") end:Int):Call<List<FilmModel>>
}