package ru.voodster.otuslesson

import androidx.fragment.app.FragmentActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class ActivityStartTest {





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