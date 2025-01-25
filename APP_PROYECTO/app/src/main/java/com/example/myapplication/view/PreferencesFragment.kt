package com.example.myapplication.view

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.ContextThemeWrapper
import androidx.preference.PreferenceFragmentCompat
import com.example.myapplication.R

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
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
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
            }"pref_theme" -> {
                val theme = sharedPreferences?.getString(key, "light") ?: "light"
                showOkCancelAlertDialog(theme)
            }
        }
    }

    private fun showOkCancelAlertDialog(theme: String) {
        AlertDialog.Builder(ContextThemeWrapper(requireContext(), R.style.ThemeMyApplication))
            .setTitle("Confirmacion")
            .setMessage("¿Quieres cambiar el tema de la aplicación?")
            .setPositiveButton("Aceptar") { dialog, which ->
                cambiarModoOscuro(theme)
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                Toast.makeText(requireContext(), "Operación cancelada", Toast.LENGTH_SHORT).show()
            }
            .setIcon(R.drawable.ic_modo_noche)
            .show()
    }

    private fun cambiarModoOscuro(theme: String){
        when (theme) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        requireActivity().recreate() // Recreate the activity to apply the theme
    }
}
