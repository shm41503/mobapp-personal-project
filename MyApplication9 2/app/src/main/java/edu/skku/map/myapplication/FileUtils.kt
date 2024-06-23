package edu.skku.map.myapplication

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object FileUtils {

    fun loadDrinksFromFile(context: Context, fileName: String): List<Drink> {
        val drinks = mutableListOf<Drink>()
        try {
            val inputStream = context.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val parts = line!!.split(",")
                if (parts.size == 4) {
                    val name = parts[0].trim()
                    val imageUrl = parts[1].trim()
                    val description = parts[2].trim()
                    val rating = parts[3].trim().toDoubleOrNull() ?: 0.0
                    drinks.add(Drink(name, imageUrl, description, rating))
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return drinks
    }
}
