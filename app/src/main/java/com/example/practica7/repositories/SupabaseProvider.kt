package com.example.practica7.repositories

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseProvider {
    val client: SupabaseClient by lazy {
        // IMPORTANTE: Reemplaza estos valores con los de tu propio proyecto en Supabase.
        // Puedes encontrarlos en: https://supabase.com/dashboard -> tu proyecto -> Settings -> API
        // No subas tus credenciales reales a repositorios publicos.
        createSupabaseClient(
            supabaseUrl = "TU_URL_DE_SUPABASE_AQUI", // Ejemplo: https://xyzabcdef.supabase.co
            supabaseKey = "TU_ANON_KEY_DE_SUPABASE_AQUI"
        ) {
            install(Postgrest)
        }
    }
}