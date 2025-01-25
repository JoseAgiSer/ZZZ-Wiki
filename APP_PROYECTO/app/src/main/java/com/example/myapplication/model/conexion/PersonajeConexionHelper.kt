package com.example.myapplication.model.conexion

import com.example.myapplication.model.Personaje
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.model.AdminSQLIteConexion

object PersonajeConexionHelper {

    fun addPersonaje(contexto: AppCompatActivity, p: Personaje):Long{
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.writableDatabase //habilito la BBDD para escribir en ella, también deja leer.
        val registro = ContentValues() //objeto de kotlin, contenido de valores, un Map.
        registro.put("idPersonaje", p.idPersonaje.toString())
        registro.put("NombrePersonaje",p.Nombre)
        registro.put("Elemento", p.Elemento)
        registro.put("Arma", p.Arma)
        registro.put("disc4", p.disc4)
        registro.put("disc5", p.disc5)
        registro.put("disc6", p.disc6)
        val returnData = bd.insert("personaje", null, registro)
        bd.close()
        return returnData
    }

    fun delPersonaje(contexto: AppCompatActivity, idPersonaje: Int):Int{
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.writableDatabase
        //val cant = bd.delete("persona", "dni='${dni}'", null)
        val cant = bd.delete("personaje", "idPersonaje=?", arrayOf(idPersonaje.toString()))
        bd.close()
        return cant
    }

    fun buscarPersonaje(contexto: AppCompatActivity, idPersonaje:Int): Personaje? {
        var p: Personaje? = null //si no encuentra ninguno vendrá null, por eso la ? y también en la devolución de la función.
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.readableDatabase
        /*Esta funciona pero es mejor como está hecho justo debajo con ?
        val fila = bd.rawQuery(
            "select nombre,edad from personas where dni='${dni}'",
            null
        )*/
        val fila =bd.rawQuery(
            "SELECT NombrePersonaje, idElemento, idArma, disc4, disc5, disc6 FROM personaje WHERE idPersonaje=?",
            arrayOf(idPersonaje.toString())
        )
        //en fila es un CURSOR con el ResultSet
        if (fila.moveToFirst()) {//si no hay datos el moveToFirst, devuelve false, si hay devuelve true.
            p = Personaje(idPersonaje, fila.getString(0), fila.getString(1), fila.getString(2),
                fila.getString(3), fila.getString(4), fila.getString(5))
        }
        bd.close()
        return p
    }

    fun obtenerPersonajes(contexto: AppCompatActivity):ArrayList<Personaje>{
        var personajes:ArrayList<Personaje> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.readableDatabase
        val fila = bd.rawQuery("select idPersonaje,NombrePersonaje,Elemento,Arma,disc4,disc5,disc6 from personaje", null)
        while (fila.moveToNext()) {
            var p: Personaje = Personaje(fila.getInt(0),fila.getString(1),fila.getString(2),
                fila.getString(3),fila.getString(4),fila.getString(5),fila.getString(6))
            personajes.add(p)
        }
        bd.close()
        return personajes //este arrayList lo puedo poner en un adapter de un RecyclerView por ejemplo.
    }

}