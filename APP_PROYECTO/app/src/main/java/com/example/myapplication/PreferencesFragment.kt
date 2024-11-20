package com.example.myapplication

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class PreferencesFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    /**
     * Se llama cuando el fragmento está a punto de ser visible para el usuario.
     * Aquí se registra el listener para detectar cambios en las preferencias.
     */
    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(requireContext()).unregisterOnSharedPreferenceChangeListener(this)
    }

    /**
     * Maneja los cambios en las preferencias compartidas.
     *
     * @param sharedPreferences Objeto que contiene las preferencias compartidas.
     * @param key La clave de la preferencia que ha cambiado.
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "pref_username" -> {
                val username = sharedPreferences?.getString(key, "") ?: ""

            }"pref_background_color" -> {
            val backgroundColor = sharedPreferences?.getString(key, "white") ?: "white"
            try {
                val color = Color.parseColor(backgroundColor)

                requireActivity().window.decorView.setBackgroundColor(color)
            } catch (e: IllegalArgumentException) {

            }
        }
        }
    }
}
