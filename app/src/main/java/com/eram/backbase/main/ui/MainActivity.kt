package com.eram.backbase.main.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.eram.backbase.databinding.ActivityMainBinding
import com.eram.backbase.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        setupObservers()
        viewModel.onCreated()
    }

    private fun setupObservers() {
    }

    private fun initUI() {

        binding.searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}