package edu.skku.map.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.skku.map.myapplication.databinding.ActivityGptRecommendationBinding
import org.json.JSONObject

class GptRecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGptRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGptRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getRecommendationButton.setOnClickListener {
            getDrinkRecommendation()
        }

        binding.addToOrderButton.setOnClickListener {
            val recommendationText: String = binding.recommendationContentTextView.text.toString()
            if (recommendationText.isNotEmpty()) {
                OrderManager.addOrder(Order(drinkName = "Recommended Drink", price = 5.0))
                Toast.makeText(this, "Added to order", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No recommendation to add", Toast.LENGTH_SHORT).show()
            }
        }

        binding.returnButton.setOnClickListener {
            finish() // This will close the current activity and return to the previous one
        }
    }

    private fun getDrinkRecommendation() {
        // Assume ApiClient.getRecommendation is implemented
        ApiClient.getRecommendation(
            model = "gpt-3.5-turbo",
            prompt = "Give me a smoothie with a fun name and a small description",
            maxTokens = 50
        ) { response ->
            runOnUiThread {
                val content: String? = parseResponseContent(response)
                binding.recommendationContentTextView.text = content ?: "Failed to get recommendation"
            }
        }
    }

    private fun parseResponseContent(response: String): String? {
        return try {
            val jsonObject = JSONObject(response)
            val choicesArray = jsonObject.getJSONArray("choices")
            if (choicesArray.length() > 0) {
                val firstChoice = choicesArray.getJSONObject(0)
                val messageObject = firstChoice.getJSONObject("message")
                messageObject.getString("content")
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
