package com.example.task7.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task7.api.entity.FilledForm
import com.example.task7.api.entity.Form
import com.example.task7.convertor.FormConvertor
import com.example.task7.repository.Repository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    companion object {
        private const val RESULT_MEMBER = "result"
    }

    private val formLiveData = MutableLiveData<Form>()
    val form: LiveData<Form>
        get() = formLiveData

    private val errorHandlerLiveData = MutableLiveData(false)
    val errorHandler: LiveData<Boolean>
        get() = errorHandlerLiveData

    private val postRequestResultLiveData = MutableLiveData<String>()
    val postRequestResult: LiveData<String>
        get() = postRequestResultLiveData

    private val fieldValues = mutableMapOf<String, String>()

    fun loadAllMetaInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val metaInfo = repository.getAllMetaInfo().await()
            val form = FormConvertor.instance.jsonObjectToForm(metaInfo)
            form?.let { formLiveData.postValue(it) }
                ?: errorHandlerLiveData.postValue(true)
        }
    }

    fun uploadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val tmp = repository.uploadData(FilledForm(fieldValues)).await()
            tmp.get(RESULT_MEMBER)?.let {
                postRequestResultLiveData.postValue(it.asString)
            }

        }

    }

    fun saveFieldState(fieldName: String, fieldCurrentValue: String) {
        fieldValues[fieldName] = fieldCurrentValue
    }
}


