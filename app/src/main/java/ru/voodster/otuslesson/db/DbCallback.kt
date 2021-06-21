package ru.voodster.otuslesson.db

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class DbCallback : RoomDatabase.Callback() {

    companion object{
        const val TAG = "DbCallback"
        private val fakeFilm = FilmEntity(0,"empty","empty","empty",false,0)
        val fakeList =  arrayListOf(fakeFilm)
    }

    //Действие при создании базы
    override fun onCreate(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onCreate")

        FilmsCache.getInstance()?.queryExecutor?.execute {
            FilmsCache.getInstance()?.getFilmsDao()?.deleteAll()
            FilmsCache.getInstance()?.getFilmsDao()?.insertAll(*fakeList.toTypedArray())
        }
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onOpen")

    }

}

