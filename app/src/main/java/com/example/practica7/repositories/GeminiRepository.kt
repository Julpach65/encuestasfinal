package com.example.practica7.repositories

import com.example.practica7.models.GeminiContent
import com.example.practica7.models.GeminiPart
import com.example.practica7.models.GeminiRequest
import com.example.practica7.models.GeminiResponse
import com.example.practica7.providers.HttpProvider
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GeminiRepository {
    private val client = HttpProvider.client
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models"
    // IMPORTANTE: Reemplaza este valor con tu propia API Key de Google AI Studio.
    // Puedes obtener una en: https://aistudio.google.com/app/apikey
    // No subas tu clave real a repositorios publicos.
    private val apiKey = "TU_API_KEY_DE_GEMINI_AQUI"

    suspend fun askGemini(prompt: String) : String {
        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(parts = listOf(GeminiPart(text = prompt)))
            )
        )

        val response : HttpResponse = client.post("$baseUrl/gemini-2.5-flash:generateContent") {
            contentType(ContentType.Application.Json)
            header("x-goog-api-key", apiKey)
            setBody(request)
        }

        val body : GeminiResponse = response.body()

        return body.candidates?.
        firstOrNull()?.
        content?.parts?.
        firstOrNull()?.
        text ?: "Sin respuesta"
    }
}