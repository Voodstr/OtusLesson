package ru.voodster.otuslesson

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.voodster.otuslesson.api.*
import java.util.concurrent.Executors

class App:Application() {


    lateinit var filmsApi: FilmsService
    lateinit var filmsUpdater : FilmsUpdater
    lateinit var filmsInteractor: FilmsInteractor

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate")
        instance = this

        initRetrofit()
        initInteractor()
        initDatabase()

    }

    private fun initDatabase(){
        Log.d(TAG,"initDatabase")
        Db.getInstance()?.queryExecutor?.execute(
            Runnable {
                Db.getFilmList()
            }
        )
    }


    private fun initInteractor() {
        Log.d(TAG,"initInteractor")
        filmsInteractor = FilmsInteractor(filmsApi)
        Log.d(TAG,"success")
    }

    private fun initRetrofit() {

        Log.d(TAG,"initRetrofit")

        val okHttpClient = OkHttpClient.Builder()
            .build()

        filmsApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(FilmsService::class.java)

        filmsUpdater = FilmsUpdater(filmsApi)
        Log.d(TAG,"success")
    }

    companion object{
        const val BASE_URL = "https://run.mocky.io/v3/"
        const val TAG = "App"

        var instance: App? = null
            private set
    }


}