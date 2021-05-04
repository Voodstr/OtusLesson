package ru.voodster.otuslesson.film

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import ru.voodster.otuslesson.FilmItem
import ru.voodster.otuslesson.FilmList
import ru.voodster.otuslesson.R
import java.lang.Exception

class FilmListFragment:Fragment()  {

    //var listener : OnFilmClickListener?=null

    private val viewModel: FilmsViewModel by viewModels()


    private val filmAdapter by lazy {
        FilmAdapter(object : FilmAdapter.O {
            override fun onFilmClick(filmItem: FilmItem) {
                viewModel.onFavBtnClick()
            }
        })


        override fun onAttach(context: Context) {
            super.onAttach(context)
            if (activity is OnFilmClickListener) {
                listener = activity as OnFilmClickListener
            } else {
                throw Exception("Activity must implement onFilmClickListener")
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_filmlist, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            viewModel.films.observe(
                viewLifecycleOwner,
                Observer<ArrayList<FilmItem>> { repos -> adapter.setItems(repos) })
            viewModel.film.observe
            view.findViewById<RecyclerView>(R.id.filmListRV)
                .adapter = FilmAdapter(FilmList) {
                //(activity as MainActivity).openAboutFilm(it.id)
                listener?.onFilmClick(it)
            }
        }
    }
    class FilmVH(FilmItem: View) : RecyclerView.ViewHolder(FilmItem) {

        private val img: ImageView = itemView.findViewById(R.id.filmImg)
        private val title: TextView = itemView.findViewById(R.id.filmTitleTv)
        private val likeBtn: ImageView = itemView.findViewById(R.id.filmLikeBtn)


        companion object{
            const val EXTRA_ID = 0
        }


        private fun pressLike(film: FilmItem, context: Context){
            if (film.fav) {
                likeBtn.startAnimation(AnimationUtils.loadAnimation(itemView.context, R.anim.image_btn_click))
                likeBtn.setBackgroundResource(R.drawable.baseline_favorite_red_a200_24dp)
                Toast.makeText(context,R.string.addFavToast, Toast.LENGTH_SHORT).show()
            } else {
                likeBtn.startAnimation(AnimationUtils.loadAnimation(itemView.context, R.anim.image_btn_click))
                likeBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_24dp)
                Toast.makeText(context,R.string.rmFavToast, Toast.LENGTH_SHORT).show()
            }
        }

        private fun setLike(film: FilmItem){
            if (film.fav) {
                likeBtn.setBackgroundResource(R.drawable.baseline_favorite_red_a200_24dp)
            } else {
                likeBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_24dp)
            }
        }


        fun bind(film: FilmItem) {
            img.setImageResource(film.img)
            title.text = film.name
            setLike(film)

            likeBtn.setOnClickListener {
                film.clickFav()
                pressLike(film,it.context)
            }
        }

    }


    class FilmHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView)





    class FilmAdapter(private val listener:((filmItem:FilmItem)->Unit)?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        companion object{
            const val TAG = "FilmAdapter"

            const val VIEW_TYPE_FILM = 1
            const val VIEW_TYPE_FILM_HEADER = 0
        }
        private val filmsList = ArrayList<FilmItem>()


        fun setItems(repos: ArrayList<FilmItem>){
            filmsList.clear()
            filmsList.addAll(repos)

            notifyDataSetChanged()
        }

        override fun getItemViewType(position: Int)
                = if (position == 0) VIEW_TYPE_FILM else  VIEW_TYPE_FILM_HEADER


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType==0) {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
                FilmVH(view)
            }
            else {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
                FilmHeaderVH(view)
            }

        }

        override fun getItemCount() = filmsList.size + 1 // +1 = header]


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is FilmVH) {
                holder.bind(filmsList[position-1])
            }

            holder.itemView.setOnClickListener {
                listener?.invoke(filmsList[position-1])
            }
        }

        interface OnFilmClickListener{
            fun onFilmClick(filmItem: FilmItem)
        }
    }

    }


