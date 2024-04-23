package com.example.appqlpt.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appqlpt.fragment.*

class ViewpagerDanhSachHopDongDSAdapter (fragmentManager: FragmentManager, lifecylce: Lifecycle):
    FragmentStateAdapter(fragmentManager,lifecylce) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                FragmentHopDongConHieuLuc()
            }
            1 -> {
                FragmentHopDongHetHieuLuc()
            }
            else ->{
                FragmentHopDongConHieuLuc()
            }
        }
    }
}