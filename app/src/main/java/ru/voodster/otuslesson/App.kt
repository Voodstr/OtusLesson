package ru.voodster.otuslesson

import android.app.Application
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.voodster.otuslesson.api.FilmsData
import ru.voodster.otuslesson.api.FilmsService
import ru.voodster.otuslesson.api.FilmsInteractor
import ru.voodster.otuslesson.api.FilmsUpdater

class App:Application() {


    lateinit var filmsApi: FilmsService
    lateinit var filmsUpdater : FilmsUpdater
    lateinit var filmsInteractor: FilmsInteractor
    private var filmsData = FilmsData()



    override fun onCreate() {
        super.onCreate()
        instance = this

        initRetrofit()
        initInteractor()

    }

    private fun initInteractor() {
        filmsInteractor = FilmsInteractor(filmsApi, filmsData)
    }

    private fun initRetrofit() {

        val okHttpClient = OkHttpClient.Builder()
            .build()

        filmsApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(FilmsService::class.java)

        filmsUpdater = FilmsUpdater(filmsApi)
    }

    companion object{
        const val  BASE_URL = "https://run.mocky.io/v3/"

        var instance: App? = null
            private set
    }


}