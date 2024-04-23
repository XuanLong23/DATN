package com.example.appqlpt.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.activity.ActivityKetThucHopDong
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.HopDongPhongConHanAdapter
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.database.HopDongDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.FragmentHopDongConHanBinding
import com.example.appqlpt.model.HopDong
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class FragmentHopDongConHan : Fragment() {
    private lateinit var binding: FragmentHopDongConHanBinding
    var listHopDongConHan = listOf<HopDong>()
    private var maKhu=""
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHopDongConHanBinding.inflate(LayoutInflater.from(context))

        val srf = binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        reLoadList()
//        //onResume()
        return binding.root
    }

    fun reLoadList(){
        listHopDongConHan = HopDongDao(requireContext()).getHopDongSapHetHanByMaKhu(maKhu, 1,1)
        //listHopDong=HopDongDao(this@ActivityDanhSachHopDong).getAllInHopDong()
        val hopDongAdapter = HopDongPhongConHanAdapter(listHopDongConHan,this)
        binding.rcyConHetHan.adapter = hopDongAdapter
        binding.rcyConHetHan.layoutManager = LinearLayoutManager(requireContext())
        hopDongAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        reLoadList()
    }

    fun ketThucHopDong(hopDong: HopDong) {
        val intent = Intent(context,ActivityKetThucHopDong::class.java)
        val bundle:Bundle
        //bundle.putString("")
        intent.putExtra("hopDong",hopDong)
        startActivity(intent)
    }


}