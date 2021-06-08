package ru.voodster.otuslesson.db

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

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
            Db.getInstance()?.getFilmsDao()?.addFav(0)
        })
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onOpen")

    }

}

