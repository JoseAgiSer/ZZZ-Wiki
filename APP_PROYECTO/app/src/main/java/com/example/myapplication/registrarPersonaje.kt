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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.myapplication.R.drawable.menu
import com.example.myapplication.databinding.ActivityInfoPersonajeBinding
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityRegistrarPersonajeBinding
import com.google.android.material.appbar.MaterialToolbar


/**
 * Clase para registrar un nuevo personaje.
 * Incluye opciones para seleccionar características y navegar a otras actividades.
 */
class registrarPersonaje : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarPersonajeBinding

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesListener: SharedPreferences.OnSharedPreferenceChangeListener

    val posiblesOpciones = listOf(
        "Inicio de sesion",     // Navega a la actividad de inicio de sesión.
        "Home",                // Navega a la actividad principal (actual).
        "Personajes",          // Navega a la actividad de información de personajes.
        "Registrar personaje"  // Navega a la actividad de registro de personajes.
    )

    // Datos predefinidos para los spinners
    var estadisticas = arrayListOf(
        "ATK","DEF","CRIT RATE","CRIT DMG","HP","PHYSIC DMG","FIRE DMG"
    )

    var personajes = arrayListOf(
        "Burnice","Caesar","Miyabi","Jane Doe","Lycaon","Nicole"
    )

    var wengines = arrayListOf(
        "Flamemaker Shaker","Tusks of Fury","Deep Sea Visitor","Sharpened Stinger","The Restrained","The Vault"
    )

    var coleccionDiscos = arrayListOf(
        "Chaos Jazz","Chaotic Metal","Fanged Metal","Freedom Blues","Inferno Metal","Polar Metal"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrarPersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ocultar el fragmento de configuración inicialmente.
        supportFragmentManager.beginTransaction()
            .hide(supportFragmentManager.findFragmentById(R.id.settingsFragment)!!)
            .commit()

        // Configuración de las preferencias compartidas.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val backgroundColor = sharedPreferences.getString("pref_background_color", "white")

        binding.root.setBackgroundColor(Color.parseColor(backgroundColor))

        // Configurar la barra de herramientas.
        val toolbar : MaterialToolbar = binding.toolBar
        setSupportActionBar(toolbar)
        toolbar.title = "Menu"
        toolbar.setBackgroundColor(Color.DKGRAY)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, menu)

        binding.selectStatDisc4

        // Configurar adaptadores para los spinners
        val spinnerDisc456 = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, estadisticas)

        val coleccionDisc = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, coleccionDiscos)

        val personajespinner = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, personajes)

        val wenginespinner = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, wengines)

        spinnerDisc456.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        binding.selectStatDisc4.adapter = spinnerDisc456
        binding.selectStatDisc5.adapter = spinnerDisc456
        binding.selectStatDisc6.adapter = spinnerDisc456
        binding.selectPersonaje.adapter = personajespinner
        binding.selectWEngine.adapter = wenginespinner
        binding.selectDiscos.adapter = coleccionDisc

        // Configurar el botón de creación de personaje.
        binding.btnCrearPersonaje.setOnClickListener {btnPulsado()}
    }

    /**
     * Infla el menú de opciones de la barra de herramientas.
     *
     * @param menu El menú de opciones.
     * @return `true` si se ha inflado correctamente.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    /**
     * Maneja las acciones del menú de opciones.
     *
     * @param item El elemento seleccionado en el menú.
     * @return `true` si se ha manejado correctamente, `false` en caso contrario.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {

                binding.searchBar.visibility = View.VISIBLE
                binding.searchBar.requestFocus()

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    posiblesOpciones
                )
                binding.searchBar.setAdapter(adapter)

                binding.searchBar.setOnItemClickListener { parent, view, position, id ->
                    val selectedItem = parent.getItemAtPosition(position) as String

                    when (selectedItem) {
                        "Inicio de sesion" -> startActivity(Intent(this, login::class.java))
                        "Home" -> startActivity(Intent(this, MainActivity::class.java))
                        "Personajes" -> startActivity(Intent(this, infoPersonaje::class.java))
                        "Registrar personaje" -> startActivity(
                            Intent(
                                this,
                                registrarPersonaje::class.java
                            )
                        )

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
     * Método para manejar la acción del botón "Crear Personaje".
     * Recupera los valores seleccionados en los spinners y navega a la actividad de información del personaje.
     */
    fun btnPulsado() {
        // Recuperar valores seleccionados de los spinners.
        val selectedPersonaje = binding.selectPersonaje.selectedItem.toString()
        val selectedWengine = binding.selectWEngine.selectedItem.toString()
        val selectedDiscos = binding.selectDiscos.selectedItem.toString()
        val selectedStatDisc4 = binding.selectStatDisc4.selectedItem.toString()
        val selectedStatDisc5 = binding.selectStatDisc5.selectedItem.toString()
        val selectedStatDisc6 = binding.selectStatDisc6.selectedItem.toString()


        // Crear un Intent para lanzar la actividad de información del personaje.
        val intent = Intent(this, infoPersonaje::class.java)

        intent.putExtra("personaje", selectedPersonaje)
        intent.putExtra("wengine", selectedWengine)
        intent.putExtra("discos", selectedDiscos)
        intent.putExtra("statDisc4", selectedStatDisc4)
        intent.putExtra("statDisc5", selectedStatDisc5)
        intent.putExtra("statDisc6", selectedStatDisc6)

        // Navegar a la actividad y mostrar un mensaje de confirmacin con Toast
        startActivity(intent)
        val toast = Toast.makeText(this, "Personaje creado", Toast.LENGTH_SHORT)
        toast.show()
    }

}