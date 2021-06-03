package ru.voodster.otuslesson.films

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.voodster.otuslesson.api.FilmModel


class FilmPagingAdapter (private val inflater: LayoutInflater,private val listener:((filmItem:FilmModel)->Unit)?,diffUtilCallback: DiffUtil.ItemCallback<FilmModel?>) :
    PagedListAdapter<FilmModel?, FilmVH?>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmVH {
        return FilmVH(inflater.inflate(ru.voodster.otuslesson.R.layout.item_film, parent, false))
    }

    override fun onBindViewHolder(holder: FilmVH, position: Int) {
        getItem(position)?.let { holder.bind(it) }


        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> listener?.invoke(it1) }
        }

    }
}