package com.example.appqlpt.fragment

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appqlpt.activity.*
import com.example.appqlpt.database.ChuTroDao
import com.example.appqlpt.database.KhuTroDao
import com.example.appqlpt.databinding.FragmentCaNhanBinding
import com.example.appqlpt.model.ChuTro

class FragmentCaNhan:Fragment() {
    private lateinit var binding: FragmentCaNhanBinding
    private var username = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCaNhanBinding.inflate(inflater,container,false)
//        val tenDao = AdminDao(binding.root.context).getHoTenAdmin()
//        val sdtDao = AdminDao(binding.root.context).getSDTAdmin()
//        binding.tvTenChuNha.text = tenDao
//        binding.tvSDT.text = "SĐT: "+sdtDao
        onResume()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val pref  = binding.root.context.getSharedPreferences(
            THONG_TIN_DANG_NHAP,
            AppCompatActivity.MODE_PRIVATE
        )
        username =pref.getString(USERNAME_KEY,"")!!
        var admin  = ChuTroDao(binding.root.context).getChuTro(username) as ChuTro
        binding.tvTenChuNha.setText(""+admin?.ho_ten)
        binding.tvSDT.setText(""+admin?.sdt)
        binding.tvDangXuat.setOnClickListener {
            val bundle = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            bundle.setTitle("Thông Báo")
            bundle.setMessage("Bạn có chắc chắn muốn đăng xuất không?")
            bundle.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(activity, ActivityDangNhap::class.java)
                activity?.finish()
                startActivity(intent)
            })
            bundle.setPositiveButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            bundle.show()
        }
        binding.tvThongTinChuNha.setOnClickListener {
            val intent = Intent(activity, ActivityThongTinChuNha::class.java)
            intent.putExtra("admin",admin)
            startActivity(intent)
        }
        binding.tvCapNhat.setOnClickListener {
            val intent = Intent(activity, ActivityCapNhatThongTinChuNha::class.java)
            intent.putExtra("admin",admin)
            startActivity(intent)
        }

    }
}