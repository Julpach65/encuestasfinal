package com.example.practica7

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica7.models.User
import com.example.practica7.repositories.SupabaseProvider
import com.example.practica7.repositories.UserRepository
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val userRepo = UserRepository(SupabaseProvider.client)
    private val _selectedUser = MutableLiveData<User?>()
    val selectedUser: LiveData<User?> get() = _selectedUser

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    init {
        loadUsers()
        Log.i("SharedViewModel","initStore")
    }

    fun loadUsers() {
        viewModelScope.launch {
            try {
                val result = userRepo.getUsers()
                _users.value = result
                Log.i("SharedViewModel", "Usuarios cargados: $result")
            } catch (e: Exception) {
                Log.i("SharedViewModel", "Fallo al cargar usuarios ${e.localizedMessage}")
            }
        }
    }

    fun setUser(user: User) {
        _selectedUser.value = user
    }

    fun clearUser() {
        _selectedUser.value = null
    }
}