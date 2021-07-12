package ru.voodster.otuslesson.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.voodster.otuslesson.FilmsRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val filmsRepository: FilmsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmListViewModel::class.java)){
            return modelClass.getConstructor(FilmsRepository::class.java).newInstance(filmsRepository)
        }else throw IllegalArgumentException("Unknown ViewModel class")
    }
}