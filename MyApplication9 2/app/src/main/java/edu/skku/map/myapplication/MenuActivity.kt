package edu.skku.map.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.skku.map.myapplication.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(), DrinkAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drinks: List<Drink> = loadDrinksFromFile()
        binding.drinksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.drinksRecyclerView.adapter = DrinkAdapter(drinks, this)

        binding.getRecommendationButton.setOnClickListener {
            startActivity(Intent(this, GptRecommendationActivity::class.java))
        }

        binding.ordersButton.setOnClickListener {
            startActivity(Intent(this, OrderSummaryActivity::class.java))
        }
    }

    override fun onItemClick(drink: Drink) {
        val intent = Intent(this, DrinkCustomizationActivity::class.java).apply {
            putExtra("drink_name", drink.name)
            putExtra("drink_image_url", drink.imageUrl)
            putExtra("drink_description", drink.description)
            putExtra("drink_rating", drink.rating)
        }
        startActivity(intent)
    }

    private fun loadDrinksFromFile(): List<Drink> {
        val drinks = mutableListOf<Drink>()
        val inputStream = assets.open("smoothie_recommendations.txt")
        inputStream.bufferedReader().forEachLine { line ->
            val parts = line.split(",")
            if (parts.size == 4) {
                val name = parts[0]
                val imageUrl = parts[1]
                val description = parts[2]
                val rating = parts[3].toDoubleOrNull() ?: 0.0
                drinks.add(Drink(name, imageUrl, description, rating))
            } else {
                Log.e("MenuActivity", "Invalid line format: $line")
            }
        }
        return drinks
    }
}
