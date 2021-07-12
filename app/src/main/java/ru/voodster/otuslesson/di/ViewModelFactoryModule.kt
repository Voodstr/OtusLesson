package ru.voodster.otuslesson.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Reusable
import ru.voodster.otuslesson.viewModel.ViewModelFactory

@Module
interface ViewModelFactoryModule {

    @Binds
    @Reusable
    fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}