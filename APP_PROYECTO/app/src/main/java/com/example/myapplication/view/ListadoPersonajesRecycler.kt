package com.example.myapplication.view

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityListadoPersonajesRecyclerBinding
import com.example.myapplication.model.AdminSQLIteConexion
import com.example.myapplication.model.Personaje
import com.example.myapplication.viewmodel.adapters.AdaptadorPersonajes

class ListadoPersonajesRecycler : AppCompatActivity() {
    private lateinit var binding: ActivityListadoPersonajesRecyclerBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var admin: AdminSQLIteConexion
    private lateinit var bd: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoPersonajesRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.rvPersonaje.requestFocus()
        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        inicializarRecyclerView()
    }

    private fun inicializarRecyclerView() {
        val manager = LinearLayoutManager(this)
        binding.rvPersonaje.layoutManager = manager
        admin = AdminSQLIteConexion(this)
        bd = admin.readableDatabase
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val username = sharedPreferences.getString("pref_username", "")
        val personajesBBDD = obtenerListaPersonajes(this, username!!)
        binding.rvPersonaje.adapter = AdaptadorPersonajes(personajesBBDD)
        Log.d("ListadoPersonajesRecycler", "Nombre de usuario recuperado: $username")

        //Crear un DividerItemDecoration y agregarlo al RecyclerView
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvPersonaje.addItemDecoration(decoration)

        //Agregar un ScrollListener para mostrar/ocultar las flechas
        binding.rvPersonaje.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                //Mostrar la flecha hacia abajo si no esta en el final
                if((firstVisibleItemPosition + visibleItemCount) < totalItemCount){
                    //binding.imDown.visibility = View.VISIBLE
                }else{
                    //binding.imDown.visibility = View.GONE
                }

                //Mostrar la flecha hacia arriba si no esta en el inicio
                if(firstVisibleItemPosition > 0){
                    //binding.imUp.visibility = View.VISIBLE
                }else{
                    //binding.imUp.visibility = View.GONE
                }

            }
        })

    }

    fun obtenerListaPersonajes(contexto: AppCompatActivity, nombreUsuario: String): ArrayList<Personaje> {
        val personajes: ArrayList<Personaje> = ArrayList()
        val admin = AdminSQLIteConexion(contexto)
        val bd: SQLiteDatabase = admin.readableDatabase
        val fila = bd.rawQuery(
            "SELECT p.idPersonaje, p.NombrePersonaje, p.Elemento, p.Arma, p.disc4, p.disc5, p.disc6 FROM personaje AS p INNER JOIN listapersonajes AS lp ON p.idPersonaje = lp.idPersonaje WHERE lp.NombreUsuario = ?",
            arrayOf(nombreUsuario)
        )
        while (fila.moveToNext()) {
            val p = Personaje(
                fila.getInt(0), // idPersonaje
                fila.getString(1), // NombrePersonaje
                fila.getString(2), // Elemento
                fila.getString(3), // Arma
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