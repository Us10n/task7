package com.example.task7.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task7.R
import com.example.task7.databinding.ListItemBinding
import com.example.task7.databinding.NumericItemBinding
import com.example.task7.databinding.TextItemBinding
import com.example.task7.listener.StateListener

class FormRecyclerViewAdapter(private val stateListener: StateListener) :
    RecyclerView.Adapter<FormRecyclerViewHolder>() {
    var items = listOf<FormRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormRecyclerViewHolder {
        return when (viewType) {
            R.layout.text_item -> FormRecyclerViewHolder.TextFieldViewHolder(
                TextItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.numeric_item -> FormRecyclerViewHolder.NumericFieldViewHolder(
                NumericItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.list_item -> FormRecyclerViewHolder.ListFieldViewHolder(
                ListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: FormRecyclerViewHolder, position: Int) {
        when (holder) {
            is FormRecyclerViewHolder.TextFieldViewHolder -> holder.bind(
                items[position] as FormRecyclerViewItem.TextItem,
                stateListener
            )
            is FormRecyclerViewHolder.NumericFieldViewHolder -> holder.bind(
                items[position] as FormRecyclerViewItem.NumericItem,
                stateListener
            )
            is FormRecyclerViewHolder.ListFieldViewHolder -> holder.bind(
                items[position] as FormRecyclerViewItem.ListItem,
                stateListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is FormRecyclerViewItem.TextItem -> R.layout.text_item
            is FormRecyclerViewItem.NumericItem -> R.layout.numeric_item
            is FormRecyclerViewItem.ListItem -> R.layout.list_item
        }
    }

    override fun getItemCount(): Int = items.size
}