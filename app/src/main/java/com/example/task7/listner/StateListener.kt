package com.example.task7.listner

interface StateListener {
    fun onStateChanged(fieldName: String, fieldValue: String)
}