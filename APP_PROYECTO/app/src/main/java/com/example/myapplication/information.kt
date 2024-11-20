package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentInformationBinding

/**
 * Fragmento `information` que muestra la información de un personaje (nombre, elemento y rol)
 * y proporciona un botón para regresar a la pantalla principal.
 */
class information : Fragment() {

    //Lista de elementos y roles
    var roles = arrayListOf(
        "Tank",
        "Damage",
        "Support",
        "Stun"
    )

    var elementos = arrayListOf(
        "Fire",
        "Ice",
        "Ether",
        "Physic"
    )

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    /**
     * Método llamado para crear y devolver la vista jerárquica asociada a este fragmento.
     *
     * @param inflater Objeto LayoutInflater para inflar las vistas del fragmento.
     * @param container El contenedor padre en el que la vista del fragmento será añadida (puede ser null).
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado del fragmento.
     * @return La raíz de la vista inflada que representa este fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)

        val intent = requireActivity().intent
        val personaje = intent.getStringExtra("personaje")

        // Actualiza los TextViews con la información del personaej
        binding.mostrarNombre.text = personaje
        binding.mostrarElemento.text = getElemento(personaje ?: "") // Handle null case
        binding.mostrarRol.text = getRol(personaje ?: "") // Handle null case

        // Configura el botón para regresar a la actividad principal.
        binding.btnHome.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    /**
     * Obtiene el rol asociado a un personaje para poder imprimirlo en la siguiente pantalla
     *
     * @param personaje El nombre del personaje.
     * @return El rol correspondiente al personaje, o "Unknown" si no se encuentra.
     */
    fun getRol(personaje: String): String {
        return when (personaje) {
            "Burnice" -> roles[0]
            "Caesar" -> roles[1]
            "Miyabi" -> roles[2]
            "Jane Doe" -> roles[3]
            "Lycaon" -> roles[1]
            "Nicole" -> roles[2]
            else -> "Unknown"
        }
    }

    /**
     * Obtiene el elemento asociado a un personaje.
     *
     * @param personaje El nombre del personaje.
     * @return El elemento correspondiente al personaje, o "Unknown" si no se encuentra.
     */
    fun getElemento(personaje: String): String {
        return when (personaje) {
            "Burnice" -> elementos[0]
            "Caesar" -> elementos[1]
            "Miyabi" -> elementos[2]
            "Jane Doe" -> elementos[3]
            "Lycaon" -> elementos[1]
            "Nicole" -> elementos[2]
            else -> "Unknown"
        }
    }


}