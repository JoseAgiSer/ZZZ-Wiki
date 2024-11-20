package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.FragmentWeaponBinding

/**
 * Fragmento para mostrar información detallada de un arma seleccionada.
 */
class weapon : Fragment() {

    private var _binding: FragmentWeaponBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeaponBinding.inflate(inflater, container, false)

        val intent = requireActivity().intent
        val arma = intent.getStringExtra("wengine")

        // Recuperar el valor del nombre del arma enviado a través del intent

        binding.nombre.text = arma
        // Aqui imprimiriamos una descripción corta del arma, por falta de tiempo usamos el Lorem ipsum
        binding.desscripcion.text = "Lorem ipsum description"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}