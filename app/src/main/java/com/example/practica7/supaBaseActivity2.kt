package com.example.practica7

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class supaBaseActivity2 : AppCompatActivity() {
    private lateinit var txtUsuarioSseleccionado: TextView
    private val storeViewModel by lazy { AppViewModelStore.provider.get(SharedViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_supa_base2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtUsuarioSseleccionado = findViewById(R.id.txtUsuarioSeleccionado)
        storeViewModel.selectedUser.observe(this) { user ->
            if(user != null){
                txtUsuarioSseleccionado.text = "Usuario seleccionado: ${user.user}"
            }
            else
                txtUsuarioSseleccionado.text = "No hay usuario seleccionado"
        }
    }
}