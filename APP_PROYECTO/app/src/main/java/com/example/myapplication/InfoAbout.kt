package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityInfoAboutBinding
import com.example.myapplication.databinding.ActivityMainBinding

/**
 * Clase para mostrart el 'Acerda de'
 */

class InfoAbout : AppCompatActivity() {

    private lateinit var binding: ActivityInfoAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{startActivity(Intent(this, MainActivity::class.java))}
    }
}