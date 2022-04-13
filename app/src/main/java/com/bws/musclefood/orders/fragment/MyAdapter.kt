package com.bws.musclefood.orders.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bws.musclefood.orders.fragment.fragmentCurrent.CurrentOrderFragment

@Suppress("DEPRECATION")
internal class MyAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CurrentOrderFragment()
            }
            1 -> {
                RecentOrderFragment()
            }

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}