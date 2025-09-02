package com.example.juice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.juice.data.JuiceColor
import com.example.juice.databinding.FragmentEntryDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class EntryDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEntryDialogBinding
    private var selectedColor: JuiceColor = JuiceColor.Red

    private val entryViewModel by viewModels<JuiceViewModel> {
        JuiceViewModel.Factory(
            (requireActivity().application as JuiceTrackerApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

//    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupColorSpinner()
        setupButtons()
        loadExistingJuice()
    }

    private fun setupViews() {
        binding.name.doOnTextChanged { _, start, _, count ->
            binding.saveButton.isEnabled = (start + count) > 0
        }
    }

    private fun setupColorSpinner() {
        binding.colorSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            JuiceColor.entries.map { getString(it.label) }
        )

        binding.colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                pos: Int,
                id: Long
            ) {
                selectedColor = JuiceColor.entries[pos]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedColor = JuiceColor.Red
            }
        }
    }

    private fun setupButtons() {
        binding.saveButton.setOnClickListener {
            saveJuice()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun loadExistingJuice() {
        val juiceId = arguments?.getLong("itemId") ?: 0L

        if (juiceId > 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    entryViewModel.getJuiceStream(juiceId).filterNotNull().collect { item ->
                        binding.name.setText(item.name)
                        binding.description.setText(item.description)
                        binding.ratingBar.rating = item.rating.toFloat()
                        val colorIndex = JuiceColor.entries.indexOfFirst { it.name == item.color }
                        if (colorIndex >= 0) {
                            binding.colorSpinner.setSelection(colorIndex)
                            selectedColor = JuiceColor.entries[colorIndex]
                        }
                    }
                }
            }
        }
    }

    private fun saveJuice() {
        val juiceId = arguments?.getLong("itemId") ?: 0L
        val name = binding.name.text.toString().trim()
        val description = binding.description.text.toString().trim()
        val rating = binding.ratingBar.rating.toInt()

        if (name.isBlank()) {
            binding.name.error = "Name is required"
            return
        }

        entryViewModel.saveJuice(
            juiceId, name, description,
            selectedColor.name, rating
        )
        dismiss()
    }
}
