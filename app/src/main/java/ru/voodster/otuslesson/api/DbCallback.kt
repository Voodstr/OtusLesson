package ru.voodster.otuslesson.api

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

class DbCallback() : RoomDatabase.Callback() {

    companion object{
        const val TAG = "DbCallback"
        val fakeList =  arrayListOf<FilmModel>(FilmModel())
    }

    //Действие при создании базы
    override fun onCreate(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onCreate")

        Db.getInstance()?.queryExecutor?.execute(Runnable {
            Db.getInstance()?.getFilmsDao()?.deleteAll()
            Db.getInstance()?.getFilmsDao()?.insertAll(*fakeList.toTypedArray())
        })
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onOpen")

    }

}

