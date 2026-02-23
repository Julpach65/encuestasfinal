package com.example.practica7

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica7.repositories.GeminiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class geminiActivity : AppCompatActivity() {
    private lateinit var txtTexto: EditText
    private lateinit var btnMejora: Button
    private lateinit var progressBar: ProgressBar
    private val geminiRepo = GeminiRepository()
    private val storeViewModel by lazy { AppViewModelStore.provider.get(SharedViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gemini)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtTexto = findViewById(R.id.txtTexto)
        btnMejora = findViewById(R.id.btnMejora)
        progressBar = findViewById(R.id.progressBar3)

        storeViewModel.users.observe(this) { user ->
            if(user != null) {
                val resultText = user.joinToString("\n") {
                    "${it.user}\n(Email: ${it.Email})"
                }
                txtTexto.setText("Usuarios: ${resultText}")
            }
            else {
                txtTexto.setText("No existe el usuario")
            }
        }

        btnMejora.setOnClickListener {
            btnMejora.isEnabled = false
            txtTexto.isEnabled = false
            progressBar.visibility = ProgressBar.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val respuesta = geminiRepo.askGemini("Necesito que mejores el siguiente texto. Solo regresame el texto mejorado, no incluyas nada más: ${txtTexto.text.toString()}")
                    runOnUiThread {
                        txtTexto.setText(respuesta)
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        txtTexto.setText("Error: ${e.localizedMessage}")
                    }
                } finally {
                    runOnUiThread {
                        btnMejora.isEnabled = true
                        txtTexto.isEnabled = true
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}