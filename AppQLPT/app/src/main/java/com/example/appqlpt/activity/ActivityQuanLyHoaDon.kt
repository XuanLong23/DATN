package com.example.appqlpt.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.databinding.ActivityQuanLyHoaDonBinding
import com.example.appqlpt.model.HopDong

class ActivityQuanLyHoaDon : AppCompatActivity() {
    private lateinit var binding: ActivityQuanLyHoaDonBinding
    var listHopDong= listOf<HopDong>()
    private var maKhu = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuanLyHoaDonBinding.inflate(layoutInflater)
        val srf = binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        setContentView(binding.root)

        setSupportActionBar(binding.tbDanhSachPhong)
        val ab = supportActionBar
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)

        binding.dsHoaDon.setOnClickListener {
            val intent = Intent(this, ActivityDanhSachHoaDon::class.java)
            startActivity(intent)
        }

        binding.taoHoaDon.setOnClickListener {
            val intent = Intent(this, ActivityDanhSachPhongTHD::class.java)
            startActivity(intent)
        }
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.itemId;
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }
}