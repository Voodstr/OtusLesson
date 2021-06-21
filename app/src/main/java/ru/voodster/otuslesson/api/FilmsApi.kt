package ru.voodster.otuslesson.api

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.*
import ru.voodster.otuslesson.db.FilmEntity

interface FilmsApi {


    @GET("/tst/films.lsp")
    fun getInitial():Call<List<FilmModel>>

    @GET("/tst/films.lsp")
    fun getMore(@Query("start")start:Int,@Query("rows") end:Int):Call<List<FilmModel>>


    @GET("/tst/films.lsp")
    fun getMoreRx(@Query("start")start:Int, @Query("rows") end:Int):Single<List<FilmModel>>

}