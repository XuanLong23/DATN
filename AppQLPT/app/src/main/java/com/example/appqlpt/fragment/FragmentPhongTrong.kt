package com.example.appqlpt.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.R
import com.example.appqlpt.activity.ActivityThemPhong
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.adapter.PhongTroAdapter
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.FragmentPhongTrongBinding
import com.example.appqlpt.model.Phong


class FragmentPhongTrong : Fragment() {
    private  var _binding: FragmentPhongTrongBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            ""
        }

    var listPhong= listOf<Phong>()
    var maKhu=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhongTrongBinding.inflate(LayoutInflater.from(context))
        val phongDao= activity?.let { PhongDao(it) }!!
        val srf=activity?.getSharedPreferences(FILE_NAME, AppCompatActivity.MODE_PRIVATE)
        maKhu=srf?.getString(MA_KHU_KEY, "")!!
        listPhong=phongDao.getAllInPhongByMaKhu(maKhu).filter { it.trang_thai_phong==0 }
        binding.imgAddPhong.setOnClickListener {
            val intent= Intent(activity, ActivityThemPhong::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val phongTroAdapter= PhongTroAdapter(listPhong)
        binding.rcyPhongTrong.adapter=phongTroAdapter
        binding.rcyPhongTrong.layoutManager= LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onResume() {
        super.onResume()
        reload()
    }
    private fun reload(){
        val phongDao= activity?.let { PhongDao(it) }!!
        listPhong=phongDao.getAllInPhongByMaKhu(maKhu).filter { it.trang_thai_phong==0 }
        val phongTroAdapter= PhongTroAdapter(listPhong)
        binding.rcyPhongTrong.adapter=phongTroAdapter
        binding.rcyPhongTrong.layoutManager= LinearLayoutManager(context)
    }
}