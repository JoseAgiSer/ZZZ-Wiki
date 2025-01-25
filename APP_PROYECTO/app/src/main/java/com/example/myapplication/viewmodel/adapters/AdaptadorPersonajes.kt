package com.example.myapplication.viewmodel.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Personaje
import kotlin.text.lowercase

class AdaptadorPersonajes(private val personajes: MutableList<Personaje>) :
    RecyclerView.Adapter<AdaptadorPersonajes.PersonajeViewHolder>() {

    class PersonajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.txtPersonaje)
        val imgPersonaje: ImageView = view.findViewById(R.id.imageView)
        var isBackgroundColorChanged = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        //Inflamos el layout de cada elemento
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_personaje, parent, false)
        return PersonajeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personajes.size
    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        //Inicializamos la lista de personajes
        val personaje = personajes[position]
        holder.textView.text = personaje.Nombre
        // Load the image
        val imageName = personaje.Nombre.lowercase().replace("\\s".toRegex(), "")
        val imageResId = getDrawableResourceId(holder.itemView.context, imageName)
        if (imageResId != 0) {
            holder.imgPersonaje.setImageResource(imageResId)
        }
        //Al hacer clic, el fondo cambia de color
        holder.itemView.setOnClickListener {
            if (!holder.isBackgroundColorChanged) {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT)
                holder.isBackgroundColorChanged = false
            } else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT)
                holder.isBackgroundColorChanged = true

            }
        }
    }
    private fun getDrawableResourceId(context: Context, imageName: String): Int {
        val packageName = context.packageName
        val resources = context.resources
        val resourceId = resources.getIdentifier(imageName, "drawable", packageName)
        return if (resourceId != 0) {
            resourceId
        } else {
            0
        }
    }
}