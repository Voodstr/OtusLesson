package ru.voodster.otuslesson

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.*
import ru.voodster.otuslesson.films.FilmListFragment
import  org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ApplicationProvider.getApplicationContext


@RunWith(RobolectricTestRunner::class)
class RobolectricTest {





    @Test fun activityTest(){
        val activity = buildActivity(
            FragmentActivity::class.java
        )
            .create()
            .start()
            .resume()
            .get()


        assert(activity!=null)

    }
}