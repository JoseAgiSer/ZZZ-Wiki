package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentBuildDiscBinding

/**
 * Fragmento `buildDisc` que se utiliza para mostrar información recibida a través de un Intent.
 * Usa View Binding para manejar las vistas del diseño asociado.
 */
class buildDisc : Fragment() {

    private var _binding: FragmentBuildDiscBinding? = null
    private val binding get() = _binding!!

    /**
     * Se llama para crear y devolver la vista jerárquica asociada a este fragmento.
     *
     * @param inflater Objeto LayoutInflater para inflar las vistas en el fragmento.
     * @param container El contenedor padre en el que la vista del fragmento será añadida (puede ser null).
     * @param savedInstanceState Si no es null, contiene el estado previamente guardado del fragmento.
     * @return La raíz de la vista inflada que representa este fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuildDiscBinding.inflate(inflater, container, false)


        val intent = requireActivity().intent
        val nombreDiscos = intent.getStringExtra("discos")
        val disco4 = intent.getStringExtra("statDisc4")
        val disco5 = intent.getStringExtra("statDisc5")
        val disco6 = intent.getStringExtra("statDisc6")

        binding.mostrarNombreDisc.text = nombreDiscos
        binding.mostrarDisco4.text = disco4
        binding.mostrarDisco5.text = disco5
        binding.mostrarDisco6.text = disco6


        return binding.root
    }

    /**
     * Se llama cuando la vista del fragmento está siendo destruida.
     * Aquí se asegura que el enlace a las vistas se limpie para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}