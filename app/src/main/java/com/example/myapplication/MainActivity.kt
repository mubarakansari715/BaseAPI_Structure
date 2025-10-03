package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.ProductUiState
import com.example.myapplication.presentation.ProductViewModel
import com.example.myapplication.presentation.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        viewModel.loadProduct()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is ProductUiState.Loading -> {
                        binding.progressBar.visibility = android.view.View.VISIBLE
                        binding.errorText.visibility = android.view.View.GONE
                        binding.recyclerView.visibility = android.view.View.GONE
                    }

                    is ProductUiState.Success -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        binding.errorText.visibility = android.view.View.GONE
                        binding.recyclerView.visibility = android.view.View.VISIBLE
                        adapter.updateProducts(uiState.products)
                    }

                    is ProductUiState.Error -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        binding.errorText.visibility = android.view.View.VISIBLE
                        binding.recyclerView.visibility = android.view.View.GONE
                        binding.errorText.text = uiState.message
                    }
                }
            }
        }
    }
}