package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivityMainBinding

/**
 * Clase `login` que representa la actividad de inicio de sesión de la aplicación.
 * Permite al usuario ingresar su nombre de usuario y contraseña, y valida las credenciales.
 */
class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene las preferencias compartidas.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Recupera y muestra el nombre de usuario guardado en las preferencias.
        val username = sharedPreferences.getString("pref_username", "")
        binding.editTxtUser.setText(username)

        // Recupera el color de fondo guardado en las preferencias y lo aplica.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val backgroundColor = sharedPreferences.getString("pref_background_color", "white")

        binding.root.setBackgroundColor(Color.parseColor(backgroundColor))

        // Configura el botón de inicio de sesión.
        binding.btnLogin.setOnClickListener {
            validarInicioSesion(binding.txtPass.text.toString(), binding.editTxtUser.text.toString())
        }

        // Configura el checkbox para mostrar u ocultar la contraseña.
        binding.seePass.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                binding.txtPass.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                // Hide password
                binding.txtPass.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

    }
    /**
     * Valida las credenciales de inicio de sesión.
     * Si las credenciales son correctas, navega a la pantalla principal.
     *
     * @param pass Contraseña ingresada por el usuario.
     * @param user Nombre de usuario ingresado por el usuario.
     */
    private fun validarInicioSesion(pass: String, user: String) {
        if(pass == "1234" && user == "User"){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            binding.txtPass.error = "Contraseña o usuario incorrecto"
        }

    }


}