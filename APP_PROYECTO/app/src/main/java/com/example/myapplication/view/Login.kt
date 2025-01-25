package com.example.myapplication.view

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.model.AdminSQLIteConexion

/**
 * Clase `login` que representa la actividad de inicio de sesión de la aplicación.
 * Permite al usuario ingresar su nombre de usuario y contraseña, y valida las credenciales.
 */
class Login : AppCompatActivity() {

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
        //val backgroundColor = sharedPreferences.getString("pref_background_color", "white")

        //binding.root.setBackgroundColor(Color.parseColor(backgroundColor))

        // Configura el botón de inicio de sesión.
        binding.btnLogin.setOnClickListener {
            validarInicioSesion(binding.txtPass.text.toString(), binding.editTxtUser.text.toString())
        }

        // Configura el checkbox para mostrar u ocultar la contraseña.
        binding.seePass.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                binding.txtPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                // Hide password
                binding.txtPass.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
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
        val admin = AdminSQLIteConexion(this)
        val bd: SQLiteDatabase = admin.readableDatabase
        val fila = bd.rawQuery("select NombreUsuario, Password from USUARIO where NombreUsuario=?", arrayOf(user))

        if (!fila.moveToFirst()) {
            // User not found
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        } else {
            // User found, check password
            val nombreUsuario = fila.getString(0)
            val password = fila.getString(1)
            if (password != pass) {
                // Incorrect password
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                binding.txtPass.error = "Contraseña incorrecta"
            } else {
                // Correct password
                Log.e("SQLLite", "Usuario: $nombreUsuario, Password: $password")
                // Save the username in shared preferences
                val editor = sharedPreferences.edit()
                editor.putString("pref_username", nombreUsuario)
                editor.apply()
                startActivity(Intent(this, MainActivity::class.java))
                binding.txtPass.error = null
            }
        }
        bd.close()
    }
}