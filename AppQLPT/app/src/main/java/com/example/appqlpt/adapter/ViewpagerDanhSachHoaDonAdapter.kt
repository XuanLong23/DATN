package com.example.appqlpt.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appqlpt.fragment.FragmentHoaDonChuaThanhToan
import com.example.appqlpt.fragment.FragmentHoaDonDathanhToan

class ViewpagerDanhSachHoaDonAdapter(fragmentManager: FragmentManager, lifecylce: Lifecycle):
    FragmentStateAdapter(fragmentManager,lifecylce) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                FragmentHoaDonDathanhToan()
            }
            1 -> {
                FragmentHoaDonChuaThanhToan()
            }
            else ->{
                FragmentHoaDonDathanhToan()
            }
        }
    }
}