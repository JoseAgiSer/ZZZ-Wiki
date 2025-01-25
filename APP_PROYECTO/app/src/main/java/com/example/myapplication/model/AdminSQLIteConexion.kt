package com.example.myapplication.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class AdminSQLIteConexion(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    //Si hay algún cambio en la BBDD, se cambia el número de versión y así automáticamente
    companion object {
        val DATABASE_VERSION: Int = 1
        val DATABASE_NAME: String = "zzzwiki.db3"
    }

    constructor(context: Context): this(context, DATABASE_NAME, null, DATABASE_VERSION){
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.e("SQLLite","Paso por onCreate del AdminSQLIteConexion")
        /*
        Este método se lanza automáticamente cuando se ejecuta SQLite por primera vez (función sobrrescrita ya que es obigatoria)
        Aquí crearíamos las tablas y claves si son más de una.
        Se crean la primera vez si no existe. Pero tener en cuenta que aquí se guardarán configuraciones
        y pequeñas cosas, por lo tanto tampoco se metarán grandes sentencias yq que SQLite no está pensado para eso
        Para BBDD más complejas, ya usarmeos servicios externos.
        */
        db.execSQL("CREATE TABLE USUARIO(NombreUsuario text PRIMARY KEY, Password text)")
        db.execSQL("CREATE TABLE PERSONAJE(idPersonaje INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", NombrePersonaje text, Elemento text, Arma text, disc4 text, disc5 text, disc6 text)")
        db.execSQL("CREATE TABLE LISTAPERSONAJES(NombreUsuario text, idPersonaje INTEGER, " +
                "PRIMARY KEY (NombreUsuario, idPersonaje)," +
                "FOREIGN KEY (NombreUsuario) REFERENCES USUARIO(NombreUsuario), " +
                "FOREIGN KEY (idPersonaje) REFERENCES PERSONAJE(idPersonaje))")

        db.execSQL("INSERT INTO USUARIO VALUES ('admin','admin')")
        db.execSQL("INSERT INTO USUARIO VALUES ('User','1234')")

        db.execSQL("INSERT INTO PERSONAJE VALUES (1,'Burnice','Fuego','Prueba','ATK','DEF','CRIT')")
        db.execSQL("INSERT INTO PERSONAJE VALUES (2,'Jane Doe','Hielo','Prueba2','ATK','DEF','CRIT')")
        db.execSQL("INSERT INTO PERSONAJE VALUES (3,'Billy','Fisico','Prueba3','ATK','DEF','CRIT')")
        db.execSQL("INSERT INTO LISTAPERSONAJES VALUES ('User',1)")
        db.execSQL("INSERT INTO LISTAPERSONAJES VALUES ('User',2)")
        db.execSQL("INSERT INTO LISTAPERSONAJES VALUES ('User',3)")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e("SQLLite","Paso por OnUpgrade del AdminSQLIteConexion")
        //si la BBDD ya existe, se modificaria con el SQL que aquí pongamos.
        //Si no existe se va al OnCreate, si existe, viene aquí.
        //para venir aquí has tenido que pasar una versión diferente y se detecta automáticamente y pasa por aquí.
        //por ejemplo podríamos borrar una tabla con DROP y luego crearla si interesa que una tabla esté siempre vacía
        //o le hago un truncate y me cargo sus datos directamente. (por ejemplo la típica tabla de registro de bitácora de la sesión)
    }
}