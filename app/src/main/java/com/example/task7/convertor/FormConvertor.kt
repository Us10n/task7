package com.example.task7.convertor

import com.example.task7.api.entity.Form
import com.example.task7.api.entity.FormField
import com.google.gson.JsonObject

class FormConvertor {
    companion object {
        val instance = FormConvertor()
        private const val TITLE_MEMBER = "title"
        private const val IMAGE_MEMBER = "image"
        private const val FIELDS_MEMBER = "fields"
        private const val TYPE_MEMBER = "type"
        private const val NAME_MEMBER = "name"
        private const val VALUES_MEMBER = "values"
        private const val LIST_TYPE = "LIST"
    }

    fun jsonObjectToForm(jsonObject: JsonObject): Form? {
        val title = jsonObject.get(TITLE_MEMBER)
        val image = jsonObject.get(IMAGE_MEMBER)
        val fields = mutableListOf<FormField>()
        val fieldsMember = jsonObject.getAsJsonArray(FIELDS_MEMBER)
        if (title == null || image == null || fieldsMember == null) {
            return null
        }
        fieldsMember.forEach {
            val fieldObject = it.asJsonObject
            val fieldTitle = fieldObject.get(TITLE_MEMBER)
            val fieldName = fieldObject.get(NAME_MEMBER)
            val fieldType = fieldObject.get(TYPE_MEMBER)
            if (fieldTitle == null || fieldName == null || fieldType == null) {
                return@forEach
            }
            val values = when (fieldType.asString) {
                LIST_TYPE -> fieldObject.getAsJsonObject(VALUES_MEMBER).map()
                else -> null
            }
            fields.add(
                FormField(
                    fieldTitle.asString,
                    fieldName.asString,
                    fieldType.asString,
                    values
                )
            )
        }
        return Form(title.asString, image.asString, fields)
    }


}

private fun JsonObject.map(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    this.keySet().forEach { key ->
        map[key] = this.get(key).asString
    }
    return map
}
