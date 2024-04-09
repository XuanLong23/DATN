package com.example.appqlpt.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appqlpt.fragment.FragmentTrangChu

class ViewPagerManHinhChinhAdapter(fragmentManager: FragmentManager,lifecycle:Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                FragmentTrangChu()
            }
            1->{
                FragmentTrangChu()
            }
            2->{
                FragmentTrangChu()
            }
            3->{
                FragmentTrangChu()
            }
            else->{
                FragmentTrangChu()
            }
        }
    }

}