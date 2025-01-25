package com.example.myapplication.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPersonajeBinding

class PersonajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding = ItemPersonajeBinding.bind(itemView)

    val textView: TextView = binding.txtPersonaje
    var isBackgroundColorChanged = false // Nueva propiedad para rastrear el cambio de color
}