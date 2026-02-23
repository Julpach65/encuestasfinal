package com.example.practica7

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica7.models.User
import com.example.practica7.repositories.SupabaseProvider
import com.example.practica7.repositories.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class supaBaseActivity1 : AppCompatActivity() {
    private lateinit var txtResultado: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnCargarUsuarios: Button
    private lateinit var btnAgregar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnSeleccionar: Button
    private lateinit var btnLimpiarStore: Button
    private val storeViewModel by lazy { AppViewModelStore.provider.get(SharedViewModel::class.java) }
    private val userRepo = UserRepository(SupabaseProvider.client)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_supa_base1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtResultado = findViewById(R.id.txtResultado)
        btnCargarUsuarios = findViewById(R.id.btnCargarUsuarios)
        progressBar = findViewById(R.id.progressBar2)
        btnAgregar = findViewById(R.id.btnAgregar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnSeleccionar = findViewById(R.id.btnSeleccionar)
        btnLimpiarStore = findViewById(R.id.btnLimpiarStore)



        btnCargarUsuarios.setOnClickListener {
            loadUsers()
        }

        btnAgregar.setOnClickListener {
            val newUser = User (
                user = "upsin",
                Email = "upsin@upsin.com",
                password = "upsin"
            )
            addUser(newUser)
        }

        btnEliminar.setOnClickListener {
            deleteUser(1)
        }

        btnActualizar.setOnClickListener {
            val usuario = User(
                id = 14,
                user = "upsinTEST",
                Email = "upsin@upsin.com",
                password = "123456"
            )
            updateUser(usuario)
        }

        btnSeleccionar.setOnClickListener {
            val user = User(
                id = 1,
                user = "upsin",
                Email = "upsin@upsin.com",
                password = "123456"
            )
            storeViewModel.setUser(user)
            Toast.makeText(this, "Usuario seleccionado: ${user.user}", Toast.LENGTH_SHORT).show()
        }

        btnLimpiarStore.setOnClickListener {
            storeViewModel.clearUser()
        }
    }

    private fun loadUsers(){
        progressBar.visibility = View.VISIBLE
        btnCargarUsuarios.isEnabled = false
        txtResultado.text = "Cargando..."
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val users = userRepo.getUsers()
                val resultText = users.joinToString("\n"){
                    "${it.user}\n(Email: ${it.Email})"
                }

                runOnUiThread {
                    txtResultado.text = resultText
                    progressBar.visibility = View.GONE
                    btnCargarUsuarios.isEnabled = true
                }
            } catch (e: Exception) {
                runOnUiThread {
                    txtResultado.text = "Error ${e.localizedMessage}"
                    progressBar.visibility = View.GONE
                    btnCargarUsuarios.isEnabled = true
                }
            }
        }
    }

    private fun addUser(newUser: User){
        progressBar.visibility = View.VISIBLE
        btnAgregar.isEnabled = false
        txtResultado.text = "Agregando usuario..."
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val result = userRepo.addUser(newUser)
                runOnUiThread {
                    txtResultado.text = "Usuario agregado con exito"
                }
            } catch (e: Exception) {
                runOnUiThread {
                    txtResultado.text = "Error ${e.localizedMessage}"
                }
            } finally {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnAgregar.isEnabled = true
                }
            }
        }
    }

    private fun deleteUser(UserId: Long){
        progressBar.visibility = View.VISIBLE
        btnEliminar.isEnabled = false
        txtResultado.text = "Eliminando usuario..."

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = userRepo.deleteUser(UserId)
                runOnUiThread {
                    txtResultado.text = "Usuario eliminado con exito"
                }
            } catch (e: Exception) {
                runOnUiThread{
                    txtResultado.text = "Error ${e.localizedMessage}"
                }
            } finally {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnEliminar.isEnabled = true
                }
            }
        }
    }

    private fun updateUser(user: User) {
        progressBar.visibility = View.VISIBLE
        btnActualizar.isEnabled = false
        txtResultado.text = "Actualizando Usuario..."
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = userRepo.updateUser(user)
                runOnUiThread {
                    txtResultado.text = "Usuario actualizado con exito"
                }
            } catch (e: Exception) {
                runOnUiThread {
                    txtResultado.text = "Error ${e.localizedMessage}"
                }
            } finally {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    btnActualizar.isEnabled = true
                }
            }
        }
    }
}