package com.example.myapplication

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplication.databinding.ActivityInfoPersonajeBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Actividad `infoPersonaje` que muestra información sobre un personaje utilizando un ViewPager
 * y un TabLayout para gestionar las pestañas.
 */
class infoPersonaje : AppCompatActivity() {
    private lateinit var binding: ActivityInfoPersonajeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoPersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
                tab, index -> tab.text = when(index){
            0 -> {"Informacion"}
            1 -> {"W-Engine"}
            2 -> {"Discos"}
            else -> {throw Resources.NotFoundException("Position not found")}
        }
        }.attach()
    }
}