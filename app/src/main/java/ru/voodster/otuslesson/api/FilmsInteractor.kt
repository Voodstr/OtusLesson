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



    fun getInitial(callback: GetFilmsCallBack){
        Log.d(TAG,"getInitial")
        filmsApi.getInitial().enqueue(object : Callback<List<FilmModel>> {
            override fun onResponse(call: Call<List<FilmModel>>, response: Response<List<FilmModel>>) {
                if (response.isSuccessful) {
                    Log.d(TAG,"onResponse")
                    Db.saveInitialFromServer(response.body()!!)
                    callback.onSuccess()
                } else {
                    Log.d(TAG,"onError")
                    Db.loadMoreFromDatabase()
                    callback.onError(response.code().toString() + "")
                }
            }

            override fun onFailure(call: Call<List<FilmModel>>, t: Throwable) {
                Log.d(TAG,"onFailure")
                callback.onError("Network error probably...")
            }
        })
    }

    fun getMore(start:Int,size:Int,callback: GetMoreFilmsCallBack){
        Log.d(TAG,"getMore")
        filmsApi.getMore(start, size).enqueue(object : Callback<List<FilmModel>> {
            override fun onResponse(call: Call<List<FilmModel>>, response: Response<List<FilmModel>>) {
                if (response.isSuccessful) {
                    Log.d(TAG,"onResponse : ${response.body()}")
                    Db.saveMoreFromServer(response.body()!!)
                    callback.onSuccess()
                } else {
                    Db.loadMoreFromDatabase()
                    callback.onError(response.code().toString() + "")
                }
            }

            override fun onFailure(call: Call<List<FilmModel>>, t: Throwable) {
                callback.onError("Network error probably...")
            }
        })
    }



    interface GetFilmsCallBack {
        fun onSuccess()
        fun onError(error: String)
    }
    interface GetMoreFilmsCallBack{
        fun onSuccess()
        fun onError(error: String)
    }

}