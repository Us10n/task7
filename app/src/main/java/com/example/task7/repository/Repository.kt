package com.example.task7.repository

import com.example.task7.api.ClevertecApi
import com.example.task7.api.entity.FilledForm
import javax.inject.Inject

class Repository @Inject constructor(private val clevertecApi: ClevertecApi) {
    fun getAllMetaInfo() = clevertecApi.getAllMetaInformation()
    fun uploadData(form: FilledForm) = clevertecApi.sendMetaInformation(form)
}