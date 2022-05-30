package com.example.task7.recycler

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.task7.databinding.ListItemBinding
import com.example.task7.databinding.NumericItemBinding
import com.example.task7.databinding.TextItemBinding
import com.example.task7.listener.StateListener

sealed class FormRecyclerViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val COMMA = ','
        const val DOT = '.'
    }

    class TextFieldViewHolder(private val binding: TextItemBinding) :
        FormRecyclerViewHolder(binding) {
        fun bind(textItem: FormRecyclerViewItem.TextItem, changeValueListener: StateListener) {
            binding.fieldTitle.text = textItem.title
            binding.editText.addTextChangedListener {
                changeValueListener.onStateChanged(
                    textItem.uniqueName,
                    binding.editText.text.toString()
                )
            }
        }
    }

    class NumericFieldViewHolder(private val binding: NumericItemBinding) :
        FormRecyclerViewHolder(binding) {
        fun bind(
            numericItem: FormRecyclerViewItem.NumericItem,
            changeValueListener: StateListener
        ) {
            binding.fieldTitle.text = numericItem.title.replace(COMMA, DOT)
            binding.editTextNumberDecimal.addTextChangedListener {
                changeValueListener.onStateChanged(
                    numericItem.uniqueName,
                    binding.editTextNumberDecimal.text.toString()
                )
            }
        }
    }

    class ListFieldViewHolder(private val binding: ListItemBinding) :
        FormRecyclerViewHolder(binding) {
        fun bind(listItem: FormRecyclerViewItem.ListItem, changeValueListener: StateListener) {
            binding.fieldTitle.text = listItem.title
            val arrayAdapter =
                ArrayAdapter(
                    binding.root.context,
                    android.R.layout.simple_spinner_item,
                    listItem.values.values.toList()
                )
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.listValues.adapter = arrayAdapter
            binding.listValues.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        changeValueListener.onStateChanged(
                            listItem.uniqueName,
                            binding.listValues.selectedItem.toString()
                        )
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }

        }

    }
}