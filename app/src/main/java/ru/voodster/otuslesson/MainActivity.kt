package ru.voodster.otuslesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {


    private var mFilmList: ArrayList<FilmItem>? = null
    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
    }

    private fun initRecycler(){
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = layoutManager
        mFilmList = Constants.sampleList()
        mFilmList?.let {
            recyclerView.adapter = FilmAdapter(it)
        }

    }

    class FilmVH(FilmItem:View) : RecyclerView.ViewHolder(FilmItem) {

        private val img: ImageView = itemView.findViewById(R.id.img)
        private val title: TextView = itemView.findViewById(R.id.titleTv)
        private val description: TextView = itemView.findViewById(R.id.descriptionTv)
        private val likeBtn: Button = itemView.findViewById(R.id.likeBtn)


        fun bind(film: FilmItem) {
            img.setImageResource(film.img)
            title.text = film.name
            description.text = film.description

            likeBtn.setOnClickListener {
                film.clickFav()
                if (film.fav) {
                    likeBtn.setBackgroundResource(R.drawable.baseline_favorite_black_24dp)
                } else likeBtn.setBackgroundResource(R.drawable.baseline_favorite_border_black_24dp)
            }
        }
    }


    class FilmAdapter(private val filmsList : ArrayList<FilmItem>) : RecyclerView.Adapter<FilmVH>(){

        companion object{
            const val TAG = "FilmAdapter"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmVH {
           // Log.d(TAG,"onCreateViewHolder $viewType")
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_film,parent,false)
            return FilmVH(view)
        }
        override fun getItemCount() = filmsList.size

        override fun onBindViewHolder(holder: FilmVH, position: Int) {
            holder.bind(filmsList[position])
        }


    }


}