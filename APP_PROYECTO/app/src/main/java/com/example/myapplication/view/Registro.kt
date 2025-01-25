package com.example.myapplication.view

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRegistroBinding
import com.example.myapplication.model.AdminSQLIteConexion
import com.example.myapplication.viewmodel.RegistroViewModel

class Registro : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private val viewModel: RegistroViewModel by viewModels() // Instancia del ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Manejo de los insets para la interfaz
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Observa los mensajes del ViewModel
        viewModel.registrationMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (message == "Usuario registrado con éxito") {
                binding.editTxtUser.setText("")
                binding.txtPass.setText("")
            }
        }

        // Configurar botones
        binding.btnRegistrar.setOnClickListener {
            val nombreUsuario = binding.editTxtUser.text.toString()
            val password = binding.txtPass.text.toString()
            viewModel.registrarUsuario(nombreUsuario, password) // Delegar lógica al ViewModel
        }

        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}