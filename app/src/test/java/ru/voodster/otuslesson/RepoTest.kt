package ru.voodster.otuslesson

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import ru.voodster.otuslesson.db.FilmEntity


class RepoTest {

    val mockRepo = mock(FilmsRepository::class.java)
    val e1 = FilmEntity(3,"","","",false,0)
    val e2 = FilmEntity(2,"","","",false,0)
    val e3 = FilmEntity(1,"","","",false,0)
    val e4 = FilmEntity(7,"","","",false,0)
    val mockList = listOf(e1,e2,e3,e4)

    @Before
    fun setup(){
        Mockito.`when`(mockRepo.rxGetFilmFromDb(1) { e ->
            assertEquals(1, e?.id)
        }).thenAnswer {
            val argument = it.arguments[1]
            val completion = argument as ((FilmEntity) -> Unit)
            completion.invoke(e3)
        }
        Mockito.`when`(mockRepo.rxGetDbMore(GetFilmsCallBack())).thenAnswer {
            val argument = it.arguments[1]
            val completion = argument as ((List<FilmEntity>) -> Unit)
            completion.invoke(mockList)
        }
        Mockito.`when`(mockRepo.rxGetNetMore(GetFilmsCallBack())).thenAnswer {
            val argument = it.arguments[1]
            val completion = argument as ((List<FilmEntity>) -> Unit)
            completion.invoke(mockList)
        }
    }
    @After
    fun tearDown(){

    }




    @Test
    fun testDbRead(){
        mockRepo.rxGetDbMore(GetFilmsCallBack())
    }


    @Test
    fun testApiRead(){
        mockRepo.rxGetNetMore(GetFilmsCallBack())
    }

    @Test
    fun testDbFilmRead(){
        mockRepo.rxGetFilmFromDb(1) { e ->
            assertEquals(1, e?.id)
        }

    }


    class GetFilmsCallBack:FilmsRepository.GetFilmsCallBack{
        override fun onError(error: String?) {
            assert(!error.isNullOrEmpty())
        }

        override fun onSuccess(films: List<FilmEntity>) {
            assert(films.isNotEmpty())
        }
    }

}