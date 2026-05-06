package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

data class Product(
    val id: String,
    val name: String,
    val row: String,
    val col: String,
    val imageRes: Int,
    val backgroundRes: Int = R.drawable.bg_abs_pcard,
    val isDigital: Boolean = false,
    val price: Double = 0.0,
    val ageRestriction: Int? = null,
    val scaleX: Float = 1f,
    val scaleY: Float = 1f,
    val videoFileName: String? = null,
    val category: String = "General"
)

class ProductAdapter(
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun updateProducts(newList: List<Product>) {
        products = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], onProductClick)
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brand: TextView    = itemView.findViewById(R.id.pcard_brand)
        private val image: ImageView   = itemView.findViewById(R.id.productImage)
        private val name: TextView     = itemView.findViewById(R.id.productDescription)
        private val price: TextView    = itemView.findViewById(R.id.productPrice)

        fun bind(product: Product, onClick: (Product) -> Unit) {
            brand.text = product.category
            name.text  = product.name
            price.text = NumberFormat.getCurrencyInstance(Locale.US).format(product.price)
            image.setImageResource(product.imageRes)
            itemView.setOnClickListener { onClick(product) }
        }
    }
}
