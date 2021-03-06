package ru.voodster.otuslesson

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.voodster.otuslesson.db.FilmEntity
import ru.voodster.otuslesson.ext.FilmDiffUtilCallback


class FilmDiffUtilCallbackTest {


    val e1 = FilmEntity(0,"","","",false,0)
    val e2 = FilmEntity(2,"","","",false,0)
    val e3 = FilmEntity(0,"","","",false,0)
    val e4 = FilmEntity(0,"","","",false,0)

    val testClass_empty = FilmDiffUtilCallback(listOf(), listOf())
    val testClass_equal = FilmDiffUtilCallback(listOf(e1), listOf(e1))
    val testClass_different = FilmDiffUtilCallback(listOf(e1), listOf(e2))

    @Before
    fun setUp() {

    }

    @Test
    fun testGetOldListSize_emptyList() {
        assertEquals(0,testClass_empty.oldListSize)
    }

    @Test
    fun testGetOldListSize_equalLIst() {
        assertEquals(1,testClass_equal.oldListSize)
    }




    @Test
    fun testGetNewListSize_emptyList() {
        assertEquals(0,testClass_empty.newListSize)
    }


    @Test
    fun testGetNewListSize_equalList() {
        assertEquals(1,testClass_equal.newListSize)
    }



    @Test
    fun testAreItemsTheSame() {
        assertEquals(true,testClass_equal.areItemsTheSame(0,0))
    }
    @Test
    fun testAreContentsTheSame() {
        assertEquals(true,testClass_equal.areItemsTheSame(0,0))
    }
}