package ru.voodster.otuslesson.api

import android.util.Log
import androidx.paging.PositionalDataSource

class FilmsPositionalDataSource(): PositionalDataSource<FilmModel>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<FilmModel>) {
        Log.d(
            TAG,
            "loadInitial, requestedStartPosition = " + params.requestedStartPosition.toString() +
                    ", requestedLoadSize = " + params.requestedLoadSize
        )
        val result: List<FilmModel> =
            Db.loadList(params.requestedStartPosition, params.requestedLoadSize)
        callback.onResult(result, 0)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<FilmModel>) {
        Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        val result: List<FilmModel> =
            Db.loadList(params.startPosition, params.loadSize)
        callback.onResult(result)
    }
}