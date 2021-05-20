package ru.voodster.otuslesson.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsInteractor(private val filmsApi: FilmsService, private val filmListData: FilmsData) {


    fun getFilms(callback: GetFilmsCallBack) {

        filmsApi.getFilms().enqueue(object : Callback<List<FilmModel>> {
            override fun onResponse(call: Call<List<FilmModel>>, response: Response<List<FilmModel>>) {
                if (response.isSuccessful) {
                    filmListData.addToTable(response.body()!!)

                    callback.onSuccess(filmListData.cachedOrFakeFilmList)
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
        fun onSuccess(filmlist: List<FilmModel>)
        fun onError(error: String)
    }

}