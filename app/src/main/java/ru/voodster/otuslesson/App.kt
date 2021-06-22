package ru.voodster.otuslesson

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import dagger.Component
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.voodster.otuslesson.api.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import ru.voodster.otuslesson.api.DaggerApiComponent.create
import ru.voodster.otuslesson.db.FilmEntity
import ru.voodster.otuslesson.db.FilmsRoomDatabase
import ru.voodster.otuslesson.db.UserFavorites
import javax.inject.Inject
import javax.inject.Singleton

@Component(modules = [ApiModule::class,AppModule::class])
@Singleton
interface AppComponent {
    fun inject(filmsRepository: FilmsRepository)
    fun inject(filmListViewModel: FilmListViewModel)


}

class App:Application() {

    //"http://10.0.2.2/"
    companion object{
        const val BASE_URL = "https://db.shs.com.ru/"

        const val TAG = "App"

        const val CHANNEL_WATCH = "Watch Later"
        const val CHANNEL_FCM = "FCM"
        lateinit var database: FilmsRoomDatabase
        val component = DaggerAppComponent.
        var instance: App? = null
            private set
    }


    lateinit var filmsApi: FilmsApi
    private lateinit var filmsUpdater : FilmsUpdater
    lateinit var filmsInteractor: FilmsInteractor
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate")

        createNotificationChannel()
        initFireBase()

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelWatch = NotificationChannel(CHANNEL_WATCH, name, importance).apply {
                description = descriptionText
            }
            val channelFCM = NotificationChannel(CHANNEL_FCM, "CloudMessage", importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channelWatch)
            notificationManager.createNotificationChannel(channelFCM)
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


}

