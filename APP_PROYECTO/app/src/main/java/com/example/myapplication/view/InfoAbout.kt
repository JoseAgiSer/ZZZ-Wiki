package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityInfoAboutBinding

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