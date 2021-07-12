package ru.voodster.otuslesson.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmListViewModel::class.java)){
            return FilmListViewModel as T
        }else throw IllegalArgumentException("Unknown ViewModel class")
    }
}