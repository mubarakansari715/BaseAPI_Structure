package com.example.myapplication.data.api

import com.example.myapplication.data.model.Product
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

}