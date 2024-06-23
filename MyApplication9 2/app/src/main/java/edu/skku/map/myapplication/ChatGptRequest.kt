package edu.skku.map.myapplication

data class ChatGptRequest(
    val model: String,
    val messages: List<Message>,
    val maxTokens: Int
)

data class Message(
    val role: String,
    val content: String
)
