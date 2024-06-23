package edu.skku.map.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.skku.map.myapplication.databinding.ActivityOrderSummaryBinding

class OrderSummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderSummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orders = OrderManager.getOrders()
        binding.orderSummaryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.orderSummaryRecyclerView.adapter = OrdersAdapter(orders)

        binding.payButton.setOnClickListener {
            val totalPrice = orders.sumOf { it.price }
            Toast.makeText(this, "Total price: $$totalPrice", Toast.LENGTH_SHORT).show()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}

