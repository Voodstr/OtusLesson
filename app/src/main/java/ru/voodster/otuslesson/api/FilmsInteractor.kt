package ru.voodster.otuslesson.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsInteractor(private val filmsApi: FilmsService) {


    fun getFilms(callback: GetFilmsCallBack) {

        filmsApi.getFilms().enqueue(object : Callback<List<FilmModel>> {
            override fun onResponse(call: Call<List<FilmModel>>, response: Response<List<FilmModel>>) {
                if (response.isSuccessful) {
                    Db.addToCache(response.body()!!)
                    callback.onSuccess(Db.cachedOrFakeFilmList)
                } else {
                    callback.onError(response.code().toString() + "")
                }
            }

            override fun onFailure(call: Call<List<FilmModel>>, t: Throwable) {
                callback.onError("Network error probably...")
            }
        })
    }



    interface GetFilmsCallBack {
        fun onSuccess(filmList: List<FilmModel>)
        fun onError(error: String)
    }

}