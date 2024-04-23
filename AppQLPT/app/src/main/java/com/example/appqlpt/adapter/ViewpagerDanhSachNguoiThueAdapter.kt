package com.example.appqlpt.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appqlpt.fragment.FragmentNguoiDaO
import com.example.appqlpt.fragment.FragmentNguoiDangO

class ViewpagerDanhSachNguoiThueAdapter (fragmentManager: FragmentManager, lifecylce: Lifecycle):
    FragmentStateAdapter(fragmentManager,lifecylce) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                FragmentNguoiDangO()
            }
            1 -> {
                FragmentNguoiDaO()
            }
            else ->{
                FragmentNguoiDangO()
            }
        }
    }
}