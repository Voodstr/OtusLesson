package ru.voodster.otuslesson

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ru.voodster.otuslesson.about.AboutFragment
import ru.voodster.otuslesson.db.FilmEntity

open class FlavorGetter:FlavorInterface {
     override fun getAboutFragment(film: FilmEntity): Fragment {
        return AboutFragment.newInstance(film)
    }

    override fun initFireBase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(App.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "Firebase token is $token"
            Log.d(App.TAG, msg)
        })
    }
}