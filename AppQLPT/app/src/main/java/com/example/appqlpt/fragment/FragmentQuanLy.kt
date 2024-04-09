package com.example.appqlpt.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appqlpt.R
import com.example.appqlpt.activity.ActivityManHinhChinhChuTro
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.databinding.FragmentQuanLyBinding
import com.example.appqlpt.model.HopDong

class FragmentQuanLy : Fragment() {
    private lateinit var binding:FragmentQuanLyBinding
     var listHopDong=listOf<HopDong>()
    private var maKhu=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentQuanLyBinding.inflate(inflater,container,false)
        val srf=binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu=srf.getString(MA_KHU_KEY,"")!!
        binding.taoHopDong.setOnClickListener {
            val intent= Intent(context,ActivityManHinhChinhChuTro::class.java)
            startActivity(intent)
        }
        binding.quanLyDichVu.setOnClickListener {
            val intent= Intent(context,ActivityManHinhChinhChuTro::class.java)
            startActivity(intent)
        }
        binding.xuLyHopDong.setOnClickListener {
            val intent= Intent(context,ActivityManHinhChinhChuTro::class.java)
            startActivity(intent)
        }
        binding.taoHoaDon.setOnClickListener {
            val intent= Intent(context,ActivityManHinhChinhChuTro::class.java)
            startActivity(intent)
        }
        binding.dsPhongThue.setOnClickListener {
            val intent= Intent(context,ActivityManHinhChinhChuTro::class.java)
            startActivity(intent)
        }
        binding.dsKhachThue.setOnClickListener {
            val intent= Intent(context,ActivityManHinhChinhChuTro::class.java)
            startActivity(intent)
        }
        binding.dsHoaDon.setOnClickListener {
            val intent= Intent(context,ActivityManHinhChinhChuTro::class.java)
            startActivity(intent)
        }
        binding.dsHopDong.setOnClickListener {
            val intent= Intent(context,ActivityManHinhChinhChuTro::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}