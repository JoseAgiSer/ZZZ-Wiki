package com.example.myapplication.viewmodel.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.view.BuildDisc
import com.example.myapplication.view.Information
import com.example.myapplication.view.Weapon

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->{
                Information()
            }
            1 ->{
                Weapon()
            }
            2 ->{
                BuildDisc()
            }
            else -> {throw Resources.NotFoundException("Position not found")}}
    }

}