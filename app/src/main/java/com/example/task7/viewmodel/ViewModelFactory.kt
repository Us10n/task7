package com.example.task7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    viewMainModelProvider: Provider<MainFragmentViewModel>
) :
    ViewModelProvider.Factory {
    private val providers =
        mapOf<Class<*>, Provider<out ViewModel>>(
            MainFragmentViewModel::class.java to viewMainModelProvider
        )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}