package ru.voodster.otuslesson.ext

import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import ru.voodster.otuslesson.MainActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import ru.voodster.otuslesson.R
import androidx.test.espresso.contrib.*
import androidx.test.espresso.assertion.ViewAssertions.*
import ru.voodster.otuslesson.filmsFavorite.FilmVH


@LargeTest
class EspressoTest {

    @get:Rule var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testFav(){
        onView(withId(R.id.filmListRV))
            .perform(RecyclerViewActions.actionOnItemAtPosition<FilmVH>(1,click()))
        onView(withId(R.id.fab_fav))
            .perform(click())
        onView(withId(R.id.fab_fav))
            .check(matches(withContentDescription((R.drawable.baseline_favorite_red_a200_24dp).toString())))
    }
}