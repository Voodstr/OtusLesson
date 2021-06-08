package ru.voodster.otuslesson

import androidx.recyclerview.widget.DiffUtil
import ru.voodster.otuslesson.db.FilmModel

class FilmDiffUtilCallback(private val oldList: List<FilmModel>,
                           private val newList: List<FilmModel>) : DiffUtil.Callback() {

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