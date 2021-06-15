package ru.voodster.otuslesson.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.voodster.otuslesson.db.FilmEntity
import ru.voodster.otuslesson.db.Db

class FilmsInteractor(private val filmsApi: FilmsApi) {

    companion object{
        const val TAG = "FilmsInteractor"
    }

   /* fun getFilms(callback: GetFilmsCallBack) {

        filmsApi.getFilms().enqueue(object : Callback<List<FilmModel>> {
            override fun onResponse(call: Call<List<FilmModel>>, response: Response<List<FilmModel>>) {
                if (response.isSuccessful) {
                    Db.updateAllFromServer(response.body()!!)
                    callback.onSuccess(Db.cachedOrFakeFilmList)

                    //TODO можно удалять
                } else {
                    callback.onError(response.code().toString() + "")
                }
            }

            override fun onFailure(call: Call<List<FilmModel>>, t: Throwable) {
                callback.onError("Network error probably...")
            }
        })
    }
*/

    fun getInitial(callback: GetFilmsCallBack){
        Log.d(TAG,"getInitial")
        filmsApi.getInitial().enqueue(object : Callback<List<FilmModel>> {
            override fun onResponse(call: Call<List<FilmModel>>, response: Response<List<FilmModel>>) {
                if (response.isSuccessful) {
                    Log.d(TAG,"onResponse")
                    Db.saveInitialFromServer(response.body()!!)
                    callback.onSuccess(response.body()!!)
                } else {
                    Log.d(TAG,"onError")
                    callback.onError(response.code().toString() + "")
                }
            }

            override fun onFailure(call: Call<List<FilmModel>>, t: Throwable) {
                Log.d(TAG,"onFailure")
                callback.onError("Network error probably...")
                // TODO пусть тащит из базы если ответа не было
            }
        })
    }

    fun getMore(start:Int,end:Int,callback: GetMoreFilmsCallBack){
        Log.d(TAG,"getMore")
        filmsApi.getMore(start, end).enqueue(object : Callback<List<FilmModel>> {
            override fun onResponse(call: Call<List<FilmModel>>, response: Response<List<FilmModel>>) {
                if (response.isSuccessful) {
                    Log.d(TAG,"onResponse : ${response.body()}")
                    Db.saveMoreFromServer(response.body()!!)
                    callback.onSuccess(response.body()!!)
                } else {
                    callback.onError(response.code().toString() + "")
                    // TODO пусть тащит из базы если ответа не было
                }
            }

            override fun onFailure(call: Call<List<FilmModel>>, t: Throwable) {
                callback.onError("Network error probably...")
                // TODO пусть тащит из базы если ответа не было
            }
        })
    }



    interface GetFilmsCallBack {
        fun onSuccess(filmList: List<FilmModel>)
        fun onError(error: String)
    }
    interface GetMoreFilmsCallBack{
        fun onSuccess(filmList: List<FilmModel>)
        fun onError(error: String)
    }

}