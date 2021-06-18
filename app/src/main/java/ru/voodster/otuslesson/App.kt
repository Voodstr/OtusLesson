package ru.voodster.otuslesson

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.voodster.otuslesson.api.*
import ru.voodster.otuslesson.db.Db
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import java.util.concurrent.Executors

class App:Application() {


    //"http://10.0.2.2/"
    companion object{
        const val BASE_URL = "https://db.shs.com.ru/"

        const val TAG = "App"

        const val CHANNEL_ID = "Watch Later"
        var instance: App? = null
            private set
    }

    private lateinit var filmsApi: FilmsApi
    private lateinit var filmsUpdater : FilmsUpdater
    lateinit var filmsInteractor: FilmsInteractor

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate")
        instance = this

        createNotificationChannel()
        initFireBase()
        initRetrofit()
        initInteractor()
        initDatabase()

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun initFireBase(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "Firebase token is $token"
            Log.d(TAG, msg)
        })
    }

    private fun initDatabase() {
        Log.d(TAG, "initDatabase")


        Executors.newSingleThreadScheduledExecutor().execute{
            Db.getInstance()?.getFilmsDao()?.getInitial()
        }
    }


    private fun initInteractor() {
        Log.d(TAG,"initInteractor")
        filmsInteractor = FilmsInteractor(filmsApi)
        Log.d(TAG,"success")
    }


    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) BODY else NONE }

    private fun initRetrofit() {

        Log.d(TAG,"initRetrofit")

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor())
            .build()

        filmsApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(FilmsApi::class.java)

        filmsUpdater = FilmsUpdater(filmsApi)
        Log.d(TAG,"success")
    }




}