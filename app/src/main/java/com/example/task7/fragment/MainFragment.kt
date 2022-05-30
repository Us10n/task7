package com.example.task7.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.task7.R
import com.example.task7.appComponent
import com.example.task7.databinding.FragmentMainBinding
import com.example.task7.listener.StateListener
import com.example.task7.recycler.FormRecyclerViewAdapter
import com.example.task7.recycler.FormRecyclerViewItem
import com.example.task7.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.launch

class MainFragment : Fragment(), StateListener {

    private companion object {
        const val TEXT_TYPE = "TEXT"
        const val NUMERIC_TYPE = "NUMERIC"
        const val LIST_TYPE = "LIST"
    }

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainFragmentViewModel by viewModels {
        requireContext().appComponent.viewModelsFactory()
    }
    private val recyclerAdapter = FormRecyclerViewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)

        //Recycler config
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainFragment.context)
            adapter = recyclerAdapter
        }
        //form load observer
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.form.observe(viewLifecycleOwner) {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.toolBar.title = it.title
                    recyclerAdapter.items = it.fields.map { field ->
                        when (field.type) {
                            TEXT_TYPE -> FormRecyclerViewItem.TextItem(field.name, field.title)
                            NUMERIC_TYPE -> FormRecyclerViewItem.NumericItem(
                                field.name,
                                field.title
                            )
                            LIST_TYPE -> FormRecyclerViewItem.ListItem(
                                field.name,
                                field.title,
                                field.values.orEmpty()
                            )
                            else -> throw NoSuchFieldException("Form display error")
                        }
                    }
                    Glide.with(binding.image.context).load(it.image).into(binding.image)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.whenStarted {
                viewModel.postRequestResult.observe(viewLifecycleOwner) {
                    binding.progressBar.visibility = View.INVISIBLE
                    showDialog(it)
                }
            }
        }
        binding.sendButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.uploadData()
        }

        viewModel.loadAllMetaInfo()
        binding.progressBar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onStateChanged(fieldName: String, fieldValue: String) {
        viewModel.saveFieldState(fieldName, fieldValue)
    }

    private fun showDialog(result: String) {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.request_result))
            setMessage(result)
            setPositiveButton(getString(R.string.ok)) { _, _ -> }
            setCancelable(false)
        }.create().show()
    }
}