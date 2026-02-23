package com.example.practica7

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSupa1 = findViewById<Button>(R.id.btnSupa1)
        val btnSupa2 = findViewById<Button>(R.id.btnSupa2)
        val btnGemini = findViewById<Button>(R.id.btnGemini)


        btnSupa1.setOnClickListener {
            val intent = Intent(this, supaBaseActivity1::class.java)
            startActivity(intent)
        }

        btnSupa2.setOnClickListener {
            val intent = Intent(this, supaBaseActivity2::class.java)
            startActivity(intent)
        }
        btnGemini.setOnClickListener {
            val intent = Intent(this, geminiActivity::class.java)
            startActivity(intent)
        }



    }
}