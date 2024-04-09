package com.example.appqlpt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appqlpt.R
import com.example.appqlpt.adapter.ViewPagerTrangChuAdapter
import com.example.appqlpt.databinding.FragmentTrangChuBinding
import com.google.android.material.tabs.TabLayoutMediator


class FragmentTrangChu : Fragment() {
    private lateinit var binding:FragmentTrangChuBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTrangChuBinding.inflate(inflater,container,false)
        val adapter=ViewPagerTrangChuAdapter(parentFragmentManager,lifecycle)
        binding.viewPager2TrangChu.adapter=adapter
        TabLayoutMediator(binding.tabLayoutTrangChu,binding.viewPager2TrangChu){tab,pos->
            when(pos){
                0->{
                    tab.text="Quản lý"
                }
                1->{
                    tab.text="Tổng quan"
                }
                else->{
                    tab.text="Quản lý"
                }
            }
        }.attach()
        return binding.root
    }
}