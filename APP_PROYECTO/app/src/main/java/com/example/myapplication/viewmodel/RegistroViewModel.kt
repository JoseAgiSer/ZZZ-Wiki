package com.example.myapplication.viewmodel

import android.app.Application
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.AdminSQLIteConexion

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val admin = AdminSQLIteConexion(application)
    private val db: SQLiteDatabase = admin.writableDatabase

    // LiveData para observar los mensajes de error o éxito
    val registrationMessage = MutableLiveData<String>()

    // Función para registrar al usuario
    fun registrarUsuario(nombreUsuario: String, password: String) {
        if (nombreUsuario.isEmpty() || password.isEmpty()) {
            registrationMessage.value = "Por favor, complete todos los campos"
            return
        }

        val values = ContentValues().apply {
            put("NombreUsuario", nombreUsuario)
            put("Password", password)
        }

        val result = db.insert("USUARIO", null, values)
        if (result == -1L) {
            registrationMessage.value = "Error al registrar usuario"
        } else {
            registrationMessage.value = "Usuario registrado con éxito"
        }
    }

    override fun onCleared() {
        super.onCleared()
        db.close() // Cierra la conexión cuando el ViewModel se destruye
    }
}