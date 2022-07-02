package com.eram.backbase.main.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eram.backbase.R
import com.eram.backbase.databinding.FragmentMainBinding
import com.eram.backbase.main.viewmodel.MainViewModel
import com.eram.backbase.model.view.CityItem

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter = CityAdapter(onItemClicked = ::onItemClicked)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        setupObservers()
        viewModel.onCreated()
    }

    private fun onItemClicked(cityItem: CityItem) {
    }

    private fun setupObservers() {
        viewModel.displayLiveData.observe(viewLifecycleOwner) { cities ->
            if (adapter.currentList.isNotEmpty()) {
                adapter.submitList(null)
            }
            adapter.submitList(ArrayList(cities))
        }
    }

    private fun initUI() {
        with(binding) {
            rv.adapter = adapter
            rv.itemAnimator = null
            searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.onQuerySubmit(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return this.onQueryTextSubmit(newText)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.rv?.adapter = null
        _binding = null
    }
}