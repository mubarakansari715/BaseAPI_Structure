package com.example.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.Product
import com.example.myapplication.databinding.ItemProductBinding

class ProductAdapter() : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var products: List<Product> = emptyList()

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                productTitle.text = product.title
                productCategory.text = product.category
                productPrice.text = "$${String.format("%.2f", product.price)}"
                productRating.text = "Rating: ${product.rating.rate} (${product.rating.count})"

                // Load image using Glide or similar library
                // For now, using a placeholder
                productImage.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
