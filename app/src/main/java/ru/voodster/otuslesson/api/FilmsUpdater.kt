package ru.voodster.otuslesson.api

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsUpdater(private val service: FilmsService): LifecycleObserver {


    private val handler = Handler(Looper.getMainLooper())



    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onLifecycleResume() {
        Log.d(TAG, "onResume")

        handler.postDelayed(GetFilmListRunnable(), DELAY.toLong())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onLifecyclePaused() {
        Log.d(TAG, "onPause")

        handler.removeCallbacksAndMessages(null)
    }


    inner class GetFilmListRunnable : Runnable {
        override fun run() {

            service.getFilms().enqueue(object : Callback<List<FilmModel>> {
                override fun onResponse(call: Call<List<FilmModel>>, response: Response<List<FilmModel>>) {
                    handler.postDelayed(GetFilmListRunnable(), DELAY.toLong())
                }

                override fun onFailure(call: Call<List<FilmModel>>, t: Throwable) {

                }
            })
        }
    }

    companion object {
        private const val TAG = "FilmsUpdater"
        private const val DELAY = 5000
    }
}