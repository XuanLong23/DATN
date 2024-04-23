package com.example.appqlpt.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appqlpt.R
import com.example.appqlpt.activity.ActivityDoanhThu
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.databinding.FragmentThongKeBinding
import com.example.appqlpt.model.ThongBao

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentThongKe.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentThongKe : Fragment() {
    private lateinit var binding: FragmentThongKeBinding
    lateinit var listThongBao:List<ThongBao>
    private var maKhu=""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThongKeBinding.inflate(inflater,container,false)
        val srf=binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu=srf.getString(MA_KHU_KEY, "")!!

        binding.doanhThu.setOnClickListener {
            val intent = Intent(context, ActivityDoanhThu::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}