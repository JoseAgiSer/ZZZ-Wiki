package com.example.myapplication.model.conexion

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.model.AdminSQLIteConexion
import com.example.myapplication.model.ListaPersonajes
import com.example.myapplication.model.Personaje

object ListaPersonajeConexionManager {
    fun addListaPersonaje(contexto: AppCompatActivity, lp: ListaPersonajes):Long{
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.writableDatabase //habilito la BBDD para escribir en ella, también deja leer.
        val registro = ContentValues() //objeto de kotlin, contenido de valores, un Map.
        registro.put("NombreUsuario", lp.NombreUsuario)
        registro.put("idPersonaje", lp.idPersonaje.toString())
        val returnData = bd.insert("listapersonajes", null, registro)
        bd.close()
        return returnData
    }

    fun delListaPersonaje(contexto: AppCompatActivity, idPersonaje: Int, nombreUsuario: String): Int {
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.writableDatabase
        val cant = bd.delete("listapersonajes", "idPersonaje=? and NombreUsuario=?",
            arrayOf(idPersonaje.toString(), nombreUsuario))
        bd.close()
        return cant
    }

    fun buscarListaPersonaje(contexto: AppCompatActivity, nombreUsuario: String, idPersonaje: Int): Boolean {
        var p: Boolean = false
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.readableDatabase
        val fila = bd.rawQuery(
            "SELECT NombreUsuario, idPersonaje FROM listapersonajes WHERE NombreUsuario=? AND idPersonaje=?",
            arrayOf(nombreUsuario, idPersonaje.toString())
        )
        if (fila.moveToFirst()) {
            p = true
        }
        bd.close()
        return p
    }

    fun obtenerListaPersonajes(contexto: AppCompatActivity, nombreUsuario: String): ArrayList<Personaje> {
        val personajes: ArrayList<Personaje> = ArrayList()
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.readableDatabase
        val fila = bd.rawQuery(
            "SELECT p.idPersonaje, p.NombrePersonaje, p.Elemento, p.Arma, p.disc4, p.disc5, p.disc6 FROM personaje AS p INNER JOIN " +
                    "listapersonajes AS lp ON p.idPersonaje = lp.idPersonaje WHERE lp.NombreUsuario = ?",
            arrayOf(nombreUsuario)
        )
        while (fila.moveToNext()) {
            val p = Personaje(
                fila.getInt(0), // idPersonaje
                fila.getString(1), // NombrePersonaje
                fila.getString(2), // idElemento
                fila.getString(3), // idArma
                fila.getString(4), // disc4
                fila.getString(5), // disc5
                fila.getString(6)  // disc6
            )
            personajes.add(p)
        }
        bd.close()
        return personajes
    }

}