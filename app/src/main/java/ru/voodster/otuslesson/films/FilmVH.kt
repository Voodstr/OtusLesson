package ru.voodster.otuslesson.films

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.db.FilmEntity

class FilmVH(FilmView: View) : RecyclerView.ViewHolder(FilmView) {

    private val img: ImageView = itemView.findViewById(R.id.filmImg)
    private val title: TextView = itemView.findViewById(R.id.filmTitleTv)
    val likeBtn: ImageView = itemView.findViewById(R.id.filmLikeBtn)


    companion object{
        const val TAG = "FilmVH"
    }




    private fun setLike(film: FilmEntity){
        if (film.fav) {
            likeBtn.setBackgroundResource(R.drawable.baseline_favorite_red_a200_24dp)
        } else {
            likeBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_24dp)
        }
    }


    fun bind(film: FilmEntity) {

        Glide
            .with(this.itemView.context)
            .load(film.img)
            .placeholder(R.drawable.filmlogo)
            .into(img)

        title.text = film.title
        setLike(film)
/*
        likeBtn.setOnClickListener {
            //film.fav = !film.fav
            this.bindingAdapter?.notifyItemChanged(this.absoluteAdapterPosition)
        }
 */
    }

}