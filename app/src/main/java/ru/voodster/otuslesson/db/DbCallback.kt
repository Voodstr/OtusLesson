package ru.voodster.otuslesson.db

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.voodster.otuslesson.FilmsRepository

class DbCallback : RoomDatabase.Callback() {

    companion object{
        const val TAG = "DbCallback"
        private val fakeFilm = FilmEntity(0,"empty","empty","empty",false,0)
        val fakeList =  arrayListOf(fakeFilm)
    }

    //Действие при создании базы
    override fun onCreate(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onCreate")
        /*
        FilmsRepository.getInstance()?.queryExecutor?.execute {
            FilmsRepository.getInstance()?.getFilmsDao()?.deleteAll()
            FilmsRepository.getInstance()?.getFilmsDao()?.insertAll(*fakeList.toTypedArray())
        }

         */
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        Log.d(TAG, "onOpen")

    }

}

