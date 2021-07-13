package ru.voodster.otuslesson.ext

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.voodster.otuslesson.MainActivity
import ru.voodster.otuslesson.R
import ru.voodster.otuslesson.db.FilmEntity
import ru.voodster.otuslesson.films.FilmAdapter
import ru.voodster.otuslesson.filmsFavorite.FilmVH


@LargeTest
class FavBtnTest {


    @get:Rule var activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    companion object{

        val e1 = FilmEntity(1,"","","",false,0)
        val e2 = FilmEntity(2,"","","",false,0)
        val e3 = FilmEntity(3,"","","",false,0)
        val e4 = FilmEntity(5,"","","",false,0)
        val mockList =listOf(e1,e2,e3,e4)
    }



    @Before
    fun setup(){
        activityTestRule.scenario.onActivity {
            val rv = it.findViewById<RecyclerView>(R.id.filmListRV)
            (rv.adapter as FilmAdapter).setItems(mockList)
        }



    }

    @Test
    fun testFav(){
        onView(withId(R.id.filmListRV))
            .perform(RecyclerViewActions.actionOnItemAtPosition<FilmVH>(2,click()))
        onView(withId(R.id.fab_fav))
            .perform(click())
        onView(withId(R.id.fab_fav))
            .check(matches(withContentDescription((R.drawable.baseline_favorite_red_a200_24dp).toString())))
    }
}