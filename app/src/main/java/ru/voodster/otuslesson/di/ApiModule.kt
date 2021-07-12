package ru.voodster.otuslesson.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.voodster.otuslesson.App
import ru.voodster.otuslesson.BuildConfig
import ru.voodster.otuslesson.api.FilmsApi

@Module
class ApiModule {
    @Provides
    @Reusable
    fun provideApi(): FilmsApi {

        val provideLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor)
            .build()

        val rxAdapter = RxJava3CallAdapterFactory.create()

        return Retrofit.Builder()
            .baseUrl(App.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(rxAdapter)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(FilmsApi::class.java)
    }
}