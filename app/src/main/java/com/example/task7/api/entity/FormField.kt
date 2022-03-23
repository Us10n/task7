package com.example.task7.api.entity

data class FormField(
    val title: String,
    val name: String,
    val type: String,
    val values: Map<String, String>?
) {
}