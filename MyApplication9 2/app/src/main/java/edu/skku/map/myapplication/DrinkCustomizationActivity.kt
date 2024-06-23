package edu.skku.map.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import edu.skku.map.myapplication.databinding.ActivityDrinkCustomizationBinding

class DrinkCustomizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrinkCustomizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkCustomizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drinkName = intent.getStringExtra("drink_name") ?: "Unknown Drink"
        val drinkImage = intent.getStringExtra("drink_image_url") ?: ""
        val drinkDescription = intent.getStringExtra("drink_description") ?: "No description available"
        val drinkRating = intent.getDoubleExtra("drink_rating", 0.0)

        binding.drinkNameTextView.text = drinkName
        binding.drinkDescriptionTextView.text = drinkDescription

        val imageUrl = "file:///android_asset/$drinkImage"
        Glide.with(this).load(imageUrl).into(binding.drinkImageView)

        binding.nextButton.setOnClickListener {
            // Add the drink to the order
            val order = Order(drinkName, 5.0)  // Assuming each drink has a fixed price of $5.0
            OrderManager.addOrder(order)
            Toast.makeText(this, "$drinkName added to order", Toast.LENGTH_SHORT).show()

            // Return to MenuActivity or navigate to OrderSummaryActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

    }
}
