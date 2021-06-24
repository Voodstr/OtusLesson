package ru.voodster.otuslesson.ext

import androidx.recyclerview.widget.DiffUtil
import ru.voodster.otuslesson.db.FilmEntity

class FilmDiffUtilCallback(private val oldList: List<FilmEntity>,
                           private val newList: List<FilmEntity>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].fav == newList[newItemPosition].fav
    }
}