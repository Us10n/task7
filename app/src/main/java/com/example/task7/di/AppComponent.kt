package com.example.task7.di

import android.app.Application
import com.example.task7.MainActivity
import com.example.task7.viewmodel.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun viewModelsFactory(): ViewModelFactory
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}