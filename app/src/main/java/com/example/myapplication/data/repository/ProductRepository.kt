package com.example.myapplication.data.repository

import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(val apiService: ApiService) {

    fun getProductList(): Flow<List<Product>> = flow {

        try {
            val response = apiService.getAllProducts()
            if (response.isSuccessful && response.body() != null) {
                emit(response.body() as List<Product>)
            } else {
                throw Exception("Failed to fetch products: ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}