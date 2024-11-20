package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import com.example.myapplication.R.drawable.menu
import com.example.myapplication.R.drawable.search
import com.example.myapplication.R.drawable.settings
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar

/**
 * Actividad principal de la aplicación. Actúa como un hub para navegar entre diferentes actividades
 * y gestiona una barra de herramientas con opciones de menú.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Objeto de preferencias compartida
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesListener: SharedPreferences.OnSharedPreferenceChangeListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oculta inicialmente el fragmento de configuración.
        supportFragmentManager.beginTransaction()
            .hide(supportFragmentManager.findFragmentById(R.id.settingsFragment)!!)
            .commit()

        // Configura la barra de herramientas.
        val toolbar : MaterialToolbar = binding.toolBar
        setSupportActionBar(toolbar)

        // Obtiene las preferencias compartidas.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val backgroundColor = sharedPreferences.getString("pref_background_color", "white")

        // Aplica el color de fondo configurado en las preferencias.
        binding.root.setBackgroundColor(Color.parseColor(backgroundColor))

        // Configuración inicial de la barra de herramientas.
        toolbar.title = "Menu"
        toolbar.setBackgroundColor(Color.DKGRAY)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, menu)

        // Configura los botones de la actividad principal.
        binding.addBtn.setOnClickListener{startActivity(Intent(this, registrarPersonaje::class.java))}
        binding.addBtn2.setOnClickListener{startActivity(Intent(this, infoPersonaje::class.java))}
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    /**
     * Infla el menú de opciones de la barra de herramientas.
     *
     * @param menu El objeto de menú.
     * @return `true` si el menú se ha inflado correctamente.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    //Posibles opciones de busqueda para el usuario
    val posiblesOpciones = listOf(
        "Inicio de sesion",
        "Home",
        "Personajes",
        "Registrar personaje"
    )

    /**
     * Maneja los eventos de selección de opciones del menú.
     *
     * @param item El elemento del menú seleccionado.
     * @return `true` si la acción se ha manejado correctamente, `false` en caso contrario.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_search -> {

                binding.searchBar.visibility = View.VISIBLE
                binding.searchBar.requestFocus()

                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, posiblesOpciones)
                binding.searchBar.setAdapter(adapter)

                binding.searchBar.setOnItemClickListener { parent, view, position, id ->
                    val selectedItem = parent.getItemAtPosition(position) as String

                    when (selectedItem) {
                        "Inicio de sesion" -> startActivity(Intent(this, login::class.java))
                        "Home" -> startActivity(Intent(this, MainActivity::class.java))
                        "Personajes" -> startActivity(Intent(this, infoPersonaje::class.java))
                        "Registrar personaje" -> startActivity(Intent(this, registrarPersonaje::class.java))

                    }

                    binding.searchBar.visibility = View.GONE
                }
                true
                    }
            R.id.menu_settings -> {
                supportFragmentManager.beginTransaction()
                    .show(supportFragmentManager.findFragmentById(R.id.settingsFragment)!!)
                    .commit()
                true
            }
            R.id.menu_webpage -> {
                val webpageUrl = "https://zenless.hoyoverse.com/es-es/main"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webpageUrl))
                startActivity(intent)
                true
            }
            R.id.about_app -> {
                startActivity(Intent(this, InfoAbout::class.java))
                true
            }
            else -> false

        }
    }
    /**
     * Maneja el evento de pulsar el botón de retroceso.
     * Si el fragmento de configuración está visible, lo oculta en lugar de cerrar la actividad.
     */
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        val settingsFragment = supportFragmentManager.findFragmentById(R.id.settingsFragment)
        if (settingsFragment != null && settingsFragment.isVisible) {
            supportFragmentManager.beginTransaction()
                .hide(settingsFragment)
                .commit()
        } else {
            super.onBackPressed()
        }
    }


}