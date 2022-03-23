package com.example.task7.listener

interface StateListener {
    fun onStateChanged(fieldName: String, fieldValue: String)
}