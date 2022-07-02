package com.eram.backbase.main.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.eram.backbase.databinding.ActivityMainBinding
import com.eram.backbase.main.viewmodel.MainViewModel
import com.eram.backbase.model.view.CityItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter = CityAdapter(onItemClicked = ::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        setupObservers()
        viewModel.onCreated()
    }

    private fun onItemClicked(cityItem: CityItem) {

    }

    private fun setupObservers() {
        viewModel.displayLiveData.observe(this) { cities ->
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

    override fun onDestroy() {
        super.onDestroy()
        _binding?.rv?.adapter = null
        _binding = null
    }
}