package com.example.appqlpt.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.R
import com.example.appqlpt.adapter.*
import com.example.appqlpt.database.HopDongDao
import com.example.appqlpt.databinding.ActivityPhongDangThueBinding
import com.example.appqlpt.databinding.ActivityPhongSapHetHopDongBinding
import com.example.appqlpt.model.HopDong

class ActivityPhongSapHetHopDong : AppCompatActivity() {
    private lateinit var binding: ActivityPhongSapHetHopDongBinding
    var listHopDongSapHetHan = listOf<HopDong>()
    private var maKhu=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhongSapHetHopDongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tbDanhSachPhong
        setSupportActionBar(binding.tbDanhSachPhong)
        val ab = getSupportActionBar()
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)

        val srf = binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        listHopDongSapHetHan = HopDongDao(this@ActivityPhongSapHetHopDong).getHopDongSapHetHanByMaKhu(maKhu,2,1)
        //listHopDong=HopDongDao(this@ActivityDanhSachHopDong).getAllInHopDong()
        val hopDongAdapter= HopDongPhongSapHetHanTongQuanAdapter(listHopDongSapHetHan)
        binding.rcyPhongSapHetHopDong.adapter=hopDongAdapter
        binding.rcyPhongSapHetHopDong.layoutManager= LinearLayoutManager(this)
    }

    fun chuyenActivity(){
        val intent = Intent(this@ActivityPhongSapHetHopDong, ActivityManHinhChinhChuTro::class.java)
        startActivity(intent)
        finish()
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.getItemId();
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }


}