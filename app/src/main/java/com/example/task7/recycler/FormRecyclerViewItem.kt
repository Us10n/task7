package com.example.task7.recycler

sealed class FormRecyclerViewItem {
    data class NumericItem(
        val uniqueName: String,
        val title: String,
    ) : FormRecyclerViewItem()

    data class TextItem(
        val uniqueName: String,
        val title: String,
    ) : FormRecyclerViewItem()

    data class ListItem(
        val uniqueName: String,
        val title: String,
        val values: Map<String, String>
    ) : FormRecyclerViewItem()
}
