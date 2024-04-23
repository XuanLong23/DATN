package com.example.appqlpt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.adapter.PhongTroAdapter
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.ActivityPhongDangThueBinding
import com.example.appqlpt.databinding.ActivityPhongTrongBinding
import com.example.appqlpt.model.Phong

class ActivityPhongDangThue : AppCompatActivity() {
    private lateinit var binding: ActivityPhongDangThueBinding
    var listPhongDangThue= listOf<Phong>()
    var maKhu=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhongDangThueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tbPhongDangThue
        setSupportActionBar(binding.tbPhongDangThue)
        val ab = getSupportActionBar()
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)

        val srf=this?.getSharedPreferences(FILE_NAME, AppCompatActivity.MODE_PRIVATE)
        maKhu=srf?.getString(MA_KHU_KEY, "")!!
        reload()
    }

    fun chuyenActivity(){
        val intent = Intent(this@ActivityPhongDangThue, ActivityManHinhChinhChuTro::class.java)
        startActivity(intent)
        finish()
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.getItemId();
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }

    private fun reload(){
        val phongDao= this?.let { PhongDao(it) }!!
        listPhongDangThue=phongDao.getAllInPhongByMaKhu(maKhu).filter { it.trang_thai_phong==1 }
        val phongTroAdapter= PhongTroAdapter(listPhongDangThue)
        binding.recyclerDanhSachPhongDangThue.adapter=phongTroAdapter
        binding.recyclerDanhSachPhongDangThue.layoutManager= LinearLayoutManager(this)
    }
}