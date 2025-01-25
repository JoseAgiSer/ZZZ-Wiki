package com.example.myapplication.model.conexion

import com.example.myapplication.model.Usuario
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.model.AdminSQLIteConexion

object UsuarioConexionHelper{

    fun addPersona(contexto: AppCompatActivity, u: Usuario):Long{
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.writableDatabase //habilito la BBDD para escribir en ella, también deja leer.
        val registro = ContentValues() //objeto de kotlin, contenido de valores, un Map.
        registro.put("Usuario", u.NombreUsuario)
        registro.put("Contraseña",u.password)
        val returnData = bd.insert("usuario", null, registro)
        bd.close()
        return returnData
    }

    fun buscarPersona(contexto: AppCompatActivity, nombreusuario:String): Usuario? {
        var u: Usuario? = null //si no encuentra ninguno vendrá null, por eso la ? y también en la devolución de la función.
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.readableDatabase
        val fila =bd.rawQuery(
            "SELECT Password FROM usuario WHERE NombreUsuario=?",
            arrayOf(nombreusuario)
        )
        //en fila es un CURSOR con el ResultSet
        if (fila.moveToFirst()) {//si no hay datos el moveToFirst, devuelve false, si hay devuelve true.
            u = Usuario(nombreusuario, fila.getString(1))
        }
        bd.close()
        return u
    }

    fun obtenerPersonas(contexto: AppCompatActivity):ArrayList<Usuario>{
        var usuarios:ArrayList<Usuario> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.readableDatabase
        val fila = bd.rawQuery("select NombreUsuario,Password from usuario", null)
        while (fila.moveToNext()) {
            var u: Usuario = Usuario(fila.getString(0),fila.getString(1))
            usuarios.add(u)
        }
        bd.close()
        return usuarios //este arrayList lo puedo poner en un adapter de un RecyclerView por ejemplo.
    }

}