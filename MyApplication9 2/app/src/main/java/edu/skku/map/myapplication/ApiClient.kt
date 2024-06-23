package edu.skku.map.myapplication

import okhttp3.*
import java.io.IOException
import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object ApiClient {
    private const val API_URL = "https://api.openai.com/v1/chat/completions"
    private const val API_KEY = BuildConfig.CHATGPT_API_KEY
    private const val TAG = "ApiClient"

    fun getRecommendation(model: String, prompt: String, maxTokens: Int, callback: (String) -> Unit) {
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = """
            {
                "model": "$model",
                "messages": [{"role": "user", "content": "$prompt"}],
                "max_tokens": $maxTokens
            }
        """.trimIndent().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(API_URL)
            .post(requestBody)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.e(TAG, "API call failed: ${e.message}")
                callback("Failed to get recommendation")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d(TAG, "API call successful: $responseBody")
                    if (responseBody != null) {
                        callback(responseBody)
                    } else {
                        callback("Failed to get recommendation")
                    }
                } else {
                    Log.e(TAG, "API call unsuccessful: ${response.code} - ${response.message}")
                    when (response.code) {
                        401 -> callback("Unauthorized: Check your API key")
                        else -> callback("Failed to get recommendation")
                    }
                }
            }
        })
    }
}
