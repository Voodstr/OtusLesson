package ru.voodster.otuslesson.about

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.voodster.otuslesson.FilmListViewModel
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.Receiver
import ru.voodster.otuslesson.db.FilmEntity
import java.util.*


class AboutFragmentStatic :Fragment() {

    private lateinit var filmEntity: FilmEntity

    private var pIntentOnce: PendingIntent? = null
    private var am: AlarmManager? = null

    private val viewModel: FilmListViewModel by activityViewModels()
    private var FAB_STATUS = false



    companion object {
        const val TAG = "AboutFragment"
        private const val FILM_DATA = "FILM_DATA"
        fun newInstance(film: FilmEntity): AboutFragmentStatic {
            return AboutFragmentStatic().apply {
                arguments = Bundle().apply {
                    putParcelable(FILM_DATA, film)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        am = getSystemService(requireContext(), AlarmManager::class.java)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_about_static, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<FilmEntity>(FILM_DATA)?.let {
            filmEntity = it
        }
        setViews(view,filmEntity)
        setClickListeners(view,filmEntity)
    }


    private fun setViews(view:View,film:FilmEntity) {



        Glide
            .with(this)
            .load(film.img)
            .placeholder(R.drawable.filmlogo)
            .into(view.findViewById(R.id.aboutImg))


        view.findViewById<CollapsingToolbarLayout>(R.id.aboutTitleTv).title = film.title
        view.findViewById<CollapsingToolbarLayout>(R.id.aboutTitleTv).setExpandedTitleColor(view.context.getColor(R.color.pal_2))
        view.findViewById<CollapsingToolbarLayout>(R.id.aboutTitleTv).setCollapsedTitleTextColor(view.context.getColor(R.color.pal_4))
        view.findViewById<TextView>(R.id.aboutDescriptionTv).text = film.description.plus("\n")


    }


    private fun setClickListeners(view: View, film: FilmEntity) {




        view.findViewById<ImageView>(R.id.fab_share).setOnClickListener {
            share(film)
        }
        view.findViewById<ImageView>(R.id.fab_watch).setOnClickListener {
            Log.d(TAG,"pickDate: ")
            pickDateTime(view,film)
            viewModel.itemChanged(filmEntity)
        }
        view.findViewById<ImageView>(R.id.fab_fav).setOnClickListener {
            film.fav = !film.fav
            setFav(view,film)
            viewModel.itemChanged(filmEntity)
        }

    }

    private fun share(film: FilmEntity) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        Log.d(TAG, "")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            film.title + "\n" + film.description
        ) //передаю название фильма
        startActivity(intent)
    }

    private fun pickDateTime(view: View,film: FilmEntity) {



        if (film.watch){
            film.watch = false
            setLater(view,film)
            stopAlarms()
        }else{
            val currentDateTime = Calendar.getInstance()
            val startYear = currentDateTime.get(Calendar.YEAR)
            val startMonth = currentDateTime.get(Calendar.MONTH)
            val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
            val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentDateTime.get(Calendar.MINUTE)
            DatePickerDialog(requireContext(), { _, year, month, day ->
                TimePickerDialog(requireContext(), { _, hour, minute ->
                    val pickedDateTime = Calendar.getInstance()
                    pickedDateTime.set(year, month, day, hour, minute)
                    if (pickedDateTime> Calendar.getInstance()){
                        film.watchDate = pickedDateTime.time
                        film.watch = true
                        setAlarm(film,pickedDateTime.timeInMillis)
                        setLater(view,film)
                    }else Toast.makeText(requireContext(),"It can't be in the past",Toast.LENGTH_SHORT).show()
                }, startHour, startMinute, false).show()
            }, startYear, startMonth, startDay).show()
        }




    }

    private fun stopAlarms(){
        pIntentOnce?.let { am?.cancel(it) }
    }


    private fun setAlarm(film: FilmEntity, timeInMillis: Long){
        Log.d(TAG,"setAlarm")

        val bundle = Bundle()
        bundle.putParcelable("film",film)

        val alarmIntent = Intent("setAlarm",null,requireContext(),Receiver::class.java)
            .putExtra("bundle",bundle)
        pIntentOnce = PendingIntent.getBroadcast(requireContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        am?.set(AlarmManager.RTC_WAKEUP, timeInMillis, pIntentOnce)
    }


    private fun setFav(view:View, film: FilmEntity){
        Log.d(TAG,"setLike")
        if (film.fav){
            with(view) {
                findViewById<ImageView>(R.id.fab_fav)
                    .setBackgroundResource(R.drawable.baseline_favorite_red_a200_24dp)
                findViewById<ImageView>(R.id.fab_fav).setTag(R.drawable.baseline_favorite_border_black_24dp,"")
            }
        }else {
            view.findViewById<ImageView>(R.id.fab_fav)
                .setBackgroundResource(R.drawable.baseline_favorite_border_black_24dp)
            view.findViewById<ImageView>(R.id.fab_fav).setTag(R.drawable.baseline_favorite_border_black_24dp,"")
        }
    }

    private fun setLater(view:View,film: FilmEntity){
        Log.d(TAG,"setLater")
        if (film.watch){
            view.findViewById<ImageView>(R.id.fab_watch).
            background.setTint(getColor(requireContext(),R.color.pal_2))
        }else  view.findViewById<ImageView>(R.id.fab_watch).
        background.setTint(getColor(requireContext(),R.color.pal_3))
    }





}