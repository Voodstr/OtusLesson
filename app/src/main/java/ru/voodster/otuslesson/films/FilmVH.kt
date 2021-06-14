package ru.voodster.otuslesson.films

import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.db.FilmEntity
import java.net.URI

class FilmVH(FilmView: View) : RecyclerView.ViewHolder(FilmView) {

    private val img: ImageView = itemView.findViewById(R.id.filmImg)
    private val title: TextView = itemView.findViewById(R.id.filmTitleTv)
    private val likeBtn: ImageView = itemView.findViewById(R.id.filmLikeBtn)


    companion object{
        const val TAG = "FilmVH"
    }


    private fun pressLike(film: FilmEntity, context: Context){
            if (film.fav) {
                likeBtn.startAnimation(AnimationUtils.loadAnimation(itemView.context, R.anim.image_btn_click))
                likeBtn.setBackgroundResource(R.drawable.baseline_favorite_red_a200_24dp)
                Toast.makeText(context,R.string.addFavToast,Toast.LENGTH_SHORT).show()
            } else {
                likeBtn.startAnimation(AnimationUtils.loadAnimation(itemView.context, R.anim.image_btn_click))
                likeBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_24dp)
                Toast.makeText(context,R.string.rmFavToast,Toast.LENGTH_SHORT).show()
            }
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

        likeBtn.setOnClickListener {
            film.fav = !film.fav
            pressLike(film,it.context)
        }
    }

}