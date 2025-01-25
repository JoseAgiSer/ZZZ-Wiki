package com.example.myapplication.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    // LiveData para observar los cambios en el color de fondo
    val backgroundColor = MutableLiveData<Int>()


    // Actualiza el color de fondo cuando las preferencias cambien
    fun updateBackgroundColor(newColor: String) {
        sharedPreferences.edit().putString("pref_background_color", newColor).apply()
        backgroundColor.value = Color.parseColor(newColor)
    }
}