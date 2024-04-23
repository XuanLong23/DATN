package com.example.appqlpt.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.activity.ActivityCapNhatKhachThue
import com.example.appqlpt.activity.THONG_TIN_PHONG
import com.example.appqlpt.adapter.*
import com.example.appqlpt.database.KhachThueDao
import com.example.appqlpt.databinding.FragmentNguoiTrongPhongBinding
import com.example.appqlpt.model.KhachThue


class FragmentNguoiThueTrongPhong : Fragment() {
    private var maKhu=""
    private var maPhong=""
    var listNguoiDung= listOf<KhachThue>()
    private lateinit var binding: FragmentNguoiTrongPhongBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentNguoiTrongPhongBinding.inflate(layoutInflater)
        val srf = binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val srf2=binding.root.context.getSharedPreferences(THONG_TIN_PHONG, Context.MODE_PRIVATE)
        maPhong=srf2.getString(MA_PHONG_TRONG_CHI_TIET_PHONG,"")!!
        binding = FragmentNguoiTrongPhongBinding.inflate(inflater,container,false)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        listNguoiDung= KhachThueDao(requireActivity()).getAllInNguoiDangOByMaKhu(maKhu).filter { it.ma_phong==maPhong }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nguoiThueAdapter = NguoiThueAdapter(listNguoiDung,object : KhachThueInterface {
            override fun OnClickKhachThue(pos: Int) {
                val nguoiDung = listNguoiDung[pos]
                val intent = Intent(requireContext(), ActivityCapNhatKhachThue::class.java)
                intent.putExtra("khachThue",nguoiDung)
                startActivity(intent)
            }

        })
        binding.rcyNguoiDangOTrongPhong.adapter = nguoiThueAdapter
        binding.rcyNguoiDangOTrongPhong.layoutManager = LinearLayoutManager(activity)
    }

    override fun onResume() {
        super.onResume()
        listNguoiDung= KhachThueDao(requireActivity()).getAllInNguoiDangOByMaKhu(maKhu).filter { it.ma_phong==maPhong }
        val nguoiThueAdapter = NguoiThueAdapter(listNguoiDung,object : KhachThueInterface {
            override fun OnClickKhachThue(pos: Int) {
                val nguoiDung = listNguoiDung[pos]
                val intent = Intent(requireContext(), ActivityCapNhatKhachThue::class.java)
                intent.putExtra("khachThue",nguoiDung)
                startActivity(intent)
            }

        })
        binding.rcyNguoiDangOTrongPhong.adapter = nguoiThueAdapter
        binding.rcyNguoiDangOTrongPhong.layoutManager = LinearLayoutManager(activity)
        Log.d("aaaa", "onResume: called ")
    }
}

