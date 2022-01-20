package com.example.sksapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    private val frags = arrayOf(MakananFragment(), MinumanFragment(), OtherFragment())

    override fun getItemCount(): Int = frags.size

    override fun createFragment(position: Int): Fragment {
        return frags[position]
    }
}