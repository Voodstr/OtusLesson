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
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.Receiver
import ru.voodster.otuslesson.db.FilmEntity
import ru.voodster.otuslesson.viewModel.FilmListViewModel
import java.util.*
import javax.inject.Inject


class AboutFragment :Fragment() {

    companion object {
        const val TAG = "AboutFragment"
        private const val FILM_DATA = "FILM_DATA"
        fun newInstance(film: FilmEntity): AboutFragment {
            return AboutFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FILM_DATA, film)
                }
            }
        }
    }

    private lateinit var filmEntity: FilmEntity

    private var pIntentOnce: PendingIntent? = null
    private var am: AlarmManager? = null

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<FilmListViewModel>{viewModelFactory}

    private var FAB_STATUS = false



    override fun onCreate(savedInstanceState: Bundle?) {
        am = getSystemService(requireContext(), AlarmManager::class.java)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_coordinator, container, false)
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

        val fab1 = view.findViewById<FloatingActionButton>(R.id.fab_fav)
        val fab2 = view.findViewById<FloatingActionButton>(R.id.fab_share)
        val fab3 = view.findViewById<FloatingActionButton>(R.id.fab_watch)



        view.findViewById<FloatingActionButton>(R.id.aboutMenuBtn).setOnClickListener {
            if (!FAB_STATUS){
                setLater(view,film)
                setLike(view, film)
                expandFAB(fab1, fab2, fab3)
            }else {
                hideFAB(fab1, fab2, fab3)
            }
            FAB_STATUS=!FAB_STATUS
        }

        view.findViewById<FloatingActionButton>(R.id.fab_share).setOnClickListener {
            share(film)
        }
        view.findViewById<FloatingActionButton>(R.id.fab_watch).setOnClickListener {
            Log.d(TAG,"pickDate: ")
            pickDateTime(view,film)
            viewModel.itemChanged(filmEntity)
        }
        view.findViewById<FloatingActionButton>(R.id.fab_fav).setOnClickListener {
            film.fav = !film.fav
            setLike(view,film)
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


    private fun setLike(view:View,film: FilmEntity){
        Log.d(TAG,"setLike")
        if (film.fav){
            with(view) {
                findViewById<FloatingActionButton>(R.id.fab_fav).
                    background.setTint(getColor(requireContext(),R.color.pal_2))
            }
        }else  view.findViewById<FloatingActionButton>(R.id.fab_fav).
        background.setTint(getColor(requireContext(),R.color.pal_3))
    }

    private fun setLater(view:View,film: FilmEntity){
        Log.d(TAG,"setLater")
        if (film.watch){
            view.findViewById<FloatingActionButton>(R.id.fab_watch).
            background.setTint(getColor(requireContext(),R.color.pal_2))
        }else  view.findViewById<FloatingActionButton>(R.id.fab_watch).
        background.setTint(getColor(requireContext(),R.color.pal_3))
    }




    private fun expandFAB(fab1:FloatingActionButton,fab2:FloatingActionButton,fab3:FloatingActionButton) {

        //Floating Action Button 1
        val layoutParams = fab1.layoutParams as FrameLayout.LayoutParams
        layoutParams.rightMargin += (fab1.width * 1.7).toInt()
        layoutParams.bottomMargin += (fab1.height * 0.25).toInt()
        fab1.layoutParams = layoutParams
        fab1.startAnimation(AnimationUtils.loadAnimation(this.context,R.anim.fab1_show))
        fab1.isClickable = true

        //Floating Action Button 2
        val layoutParams2 = fab2.layoutParams as FrameLayout.LayoutParams
        layoutParams2.rightMargin += (fab2.width * 1.5).toInt()
        layoutParams2.bottomMargin += (fab2.height * 1.5).toInt()
        fab2.layoutParams = layoutParams2
        fab2.startAnimation(AnimationUtils.loadAnimation(this.context,R.anim.fab2_show))
        fab2.isClickable = true

        //Floating Action Button 3
        val layoutParams3 = fab3.layoutParams as FrameLayout.LayoutParams
        layoutParams3.rightMargin += (fab3.width * 0.25).toInt()
        layoutParams3.bottomMargin += (fab3.height * 1.7).toInt()
        fab3.layoutParams = layoutParams3
        fab3.startAnimation(AnimationUtils.loadAnimation(this.context,R.anim.fab3_show))
        fab3.isClickable = true
    }


    private fun hideFAB(fab1:FloatingActionButton,fab2:FloatingActionButton,fab3:FloatingActionButton) {

        //Floating Action Button 1
        val layoutParams = fab1.layoutParams as FrameLayout.LayoutParams
        layoutParams.rightMargin -= (fab1.width * 1.7).toInt()
        layoutParams.bottomMargin -= (fab1.height * 0.25).toInt()
        fab1.layoutParams = layoutParams
        fab1.startAnimation(AnimationUtils.loadAnimation(this.context,R.anim.fab1_hide))
        fab1.isClickable = false

        //Floating Action Button 2
        val layoutParams2 = fab2.layoutParams as FrameLayout.LayoutParams
        layoutParams2.rightMargin -= (fab2.width * 1.5).toInt()
        layoutParams2.bottomMargin -= (fab2.height * 1.5).toInt()
        fab2.layoutParams = layoutParams2
        fab2.startAnimation(AnimationUtils.loadAnimation(this.context,R.anim.fab2_hide))
        fab2.isClickable = false

        //Floating Action Button 3
        val layoutParams3 = fab3.layoutParams as FrameLayout.LayoutParams
        layoutParams3.rightMargin -= (fab3.width * 0.25).toInt()
        layoutParams3.bottomMargin -= (fab3.height * 1.7).toInt()
        fab3.layoutParams = layoutParams3
        fab3.startAnimation(AnimationUtils.loadAnimation(this.context,R.anim.fab3_hide))
        fab3.isClickable = false
    }


}