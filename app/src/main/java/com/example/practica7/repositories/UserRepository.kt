package com.example.practica7.repositories

import android.util.Log
import com.example.practica7.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class UserRepository(val supabase: SupabaseClient) {
    // suspend fun getUsers() = supabase.postgrest["user"].select().decodeList<User>()
    suspend fun getUsers() : List<User> {
        Log.i("SupabaseRepository","Obteniendo lista de usuarios")
        return supabase.postgrest["Usuarios"]
            .select()
            .decodeList()
    }
    suspend fun addUser(user: User) = supabase.postgrest["Usuarios"].insert(user)

    suspend fun updateUser(user: User) = supabase.postgrest["Usuarios"].update(user) {
        filter {
            eq("id", user.id!!)
        }
    }
    suspend fun deleteUser(userId: Long) = supabase.postgrest["Usuarios"].delete {
        filter {
            eq("id", userId)
        }
    }
}