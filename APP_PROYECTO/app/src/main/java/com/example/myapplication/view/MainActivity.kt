package com.example.myapplication.view

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.example.myapplication.R
import com.example.myapplication.R.drawable.menu
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar

/**
 * Actividad principal de la aplicación. Actúa como un hub para navegar entre diferentes actividades
 * y gestiona una barra de herramientas con opciones de menú.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la barra de herramientas
        val toolbar: MaterialToolbar = binding.toolBar
        setSupportActionBar(toolbar)

        // Observa el color de fondo desde el ViewModel
        viewModel.backgroundColor.observe(this, Observer { color ->
            binding.root.setBackgroundColor(color)
        })

        // Configuración de botones
        binding.addBtn.setOnClickListener {
            startActivity(Intent(this, RegistrarPersonaje::class.java))
        }
        binding.addBtn2.setOnClickListener {
            startActivity(Intent(this, ListadoPersonajesRecycler::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                showSearchBar()
                true
            }
            R.id.menu_settings -> {
                startActivity(Intent(this, Preferences::class.java))
                true
            }
            R.id.menu_webpage -> {
                openWebPage("https://zenless.hoyoverse.com/es-es/main")
                true
            }
            R.id.about_app -> {
                startActivity(Intent(this, InfoAbout::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSearchBar() {
        binding.searchBar.visibility = View.VISIBLE
        binding.searchBar.requestFocus()

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listOf("Inicio de sesion", "Home", "Personajes", "Registrar personaje"))
        binding.searchBar.setAdapter(adapter)

        binding.searchBar.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            when (selectedItem) {
                "Inicio de sesion" -> startActivity(Intent(this, Login::class.java))
                "Home" -> startActivity(Intent(this, MainActivity::class.java))
                "Personajes" -> startActivity(Intent(this, InfoPersonaje::class.java))
                "Registrar personaje" -> startActivity(Intent(this, RegistrarPersonaje::class.java))
            }
            binding.searchBar.visibility = View.GONE
        }
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}