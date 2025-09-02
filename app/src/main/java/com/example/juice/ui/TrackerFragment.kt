package com.example.juice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.juice.databinding.FragmentTrackerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrackerFragment : Fragment() {

    private var _binding: FragmentTrackerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: JuiceViewModel by viewModels()
    private lateinit var adapter: JuiceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupFab()
        observeJuices()
    }

    private fun setupRecyclerView() {
        adapter = JuiceListAdapter(
            onEdit = { juice ->
                EntryDialogFragment().apply {
                    arguments = Bundle().apply { putLong("itemId", juice.id) }
                }.show(parentFragmentManager, "EntryDialog")
            },
            onDelete = { juice -> viewModel.deleteJuice(juice) }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TrackerFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            EntryDialogFragment().show(parentFragmentManager, "EntryDialog")
        }
    }

    private fun observeJuices() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.juicesStream.collectLatest { juices ->
                    adapter.submitList(juices)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
