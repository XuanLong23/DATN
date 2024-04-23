package com.example.appqlpt.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.HopDongHetHieuLucAdapter
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.database.HopDongDao
import com.example.appqlpt.databinding.FragmentHopDongConHieuLucBinding
import com.example.appqlpt.databinding.FragmentHopDongHetHieuLucBinding
import com.example.appqlpt.model.HopDong
import java.text.SimpleDateFormat

class FragmentHopDongHetHieuLuc : Fragment() {
    private lateinit var binding: FragmentHopDongHetHieuLucBinding
    var listHopDongHetHieuLuc = listOf<HopDong>()
    private var maKhu=""
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHopDongHetHieuLucBinding.inflate(LayoutInflater.from(context))
        reloadDS()
        return binding.root
    }

    private fun reloadDS() {
        val srf = this.binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        listHopDongHetHieuLuc = HopDongDao(requireContext()).getAllInHopDongByMaKhu(maKhu,0)
        //listHopDong=HopDongDao(this@ActivityDanhSachHopDong).getAllInHopDong()
        val hopDongAdapter = HopDongHetHieuLucAdapter(listHopDongHetHieuLuc)
        this.binding.rcyPhongHetHieuLuc.adapter = hopDongAdapter
        this.binding.rcyPhongHetHieuLuc.layoutManager = LinearLayoutManager(requireContext())
    }

}