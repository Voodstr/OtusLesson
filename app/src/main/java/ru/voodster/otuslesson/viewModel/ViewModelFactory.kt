package ru.voodster.otuslesson.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory @Inject constructor():ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmListViewModel::class.java)){
            return FilmListViewModel() as T
        }else throw IllegalArgumentException("Unknown ViewModel class")
    }
}