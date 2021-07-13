package ru.voodster.otuslesson

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.*
import ru.voodster.otuslesson.db.FilmEntity

class RepoTest {

    val mockRepo = mock(FilmsRepository::class.java)
    val e1 = FilmEntity(0,"","","",false,0)
    val e2 = FilmEntity(2,"","","",false,0)
    val e3 = FilmEntity(0,"","","",false,0)
    val e4 = FilmEntity(0,"","","",false,0)

    @Before
    fun setup(){

    }
    @After
    fun tearDown(){

    }


    @Test
    fun testDbRead(){
        mockRepo.rxGetDbMore(object : FilmsRepository.GetFilmsCallBack{
            override fun onError(error: String?) {
                assert(!error.isNullOrEmpty())
            }

            override fun onSuccess(films: List<FilmEntity>) {
                assert(mockRepo.currentFilmList.size>0)
            }
        }

        )
    }


    @Test
    fun testApiRead(){
        mockRepo.rxGetNetMore(object : FilmsRepository.GetFilmsCallBack{
            override fun onError(error: String?) {
                assert(!error.isNullOrEmpty())
            }

            override fun onSuccess(films: List<FilmEntity>) {
                assert(mockRepo.currentFilmList.size>0)
            }
        })
    }

    @Test
    fun testDbFilmRead(){
        mockRepo.rxGetFilmFromDb(1) { e ->
            assertEquals(1, e?.id)
        }

    }

}