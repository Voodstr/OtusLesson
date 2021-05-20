package ru.voodster.otuslesson.api

import retrofit2.Call
import retrofit2.http.*

interface FilmsService {

    //83d21407-3a49-4e8a-bc57-ef6586673e8a  --  Одна строчка
    // 88b6c347-be0d-43ac-a99b-2fb30609595d --  десяток
    @GET("88b6c347-be0d-43ac-a99b-2fb30609595d")
    fun getFilms():Call<List<FilmModel>>
}