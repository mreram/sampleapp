package com.eram.backbase.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eram.backbase.databinding.FragmentMainBinding
import com.eram.backbase.main.viewmodel.MainViewModel
import com.eram.backbase.model.view.CityItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter = CityAdapter(onItemClicked = ::onItemClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        setupObservers()
        viewModel.onCreated()
    }

    private fun onItemClicked(cityItem: CityItem) {
        findNavController().navigate(MainFragmentDirections.navigateToMap(cityItem.coordinates))
    }

    private fun setupObservers() {
        viewModel.displayLiveData.observe(viewLifecycleOwner) { cities ->
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
                    adapter.submitList(null)
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