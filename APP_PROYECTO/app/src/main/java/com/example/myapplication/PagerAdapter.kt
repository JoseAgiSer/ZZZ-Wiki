package com.example.myapplication

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->{information()}
            1 ->{weapon()}
            2 ->{buildDisc()}
            else -> {throw Resources.NotFoundException("Position not found")}}
    }

}