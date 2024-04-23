package com.example.appqlpt.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appqlpt.fragment.*

class ViewpagerDanhSachHopDongXuLyAdapter (fragmentManager: FragmentManager, lifecylce: Lifecycle):
    FragmentStateAdapter(fragmentManager,lifecylce) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                FragmentHopDongConHan()
            }
            1 -> {
                FragmentHopDongSapHetHan()
            }
            2 -> {
                FragmentHopDongDaHetHan()
            }
            else ->{
                FragmentHopDongConHan()
            }
        }
    }
}