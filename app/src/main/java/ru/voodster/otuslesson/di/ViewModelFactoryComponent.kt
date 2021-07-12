package ru.voodster.otuslesson.di

import androidx.fragment.app.Fragment
import dagger.Component
import ru.voodster.otuslesson.MainActivity

@Component(modules = [ViewModelFactoryModule::class,ApiModule::class,DbModule::class])
interface ViewModelFactoryComponent {
    fun inject(fragment: Fragment)
    fun inject(activity: MainActivity)
}