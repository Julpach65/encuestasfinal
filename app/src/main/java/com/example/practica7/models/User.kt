package com.example.practica7.models
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long? = null,
    val user: String,
    val password: String,
    val created_at: String? = null,
    val Email: String
)



