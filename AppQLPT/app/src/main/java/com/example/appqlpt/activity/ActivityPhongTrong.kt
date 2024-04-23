package com.example.appqlpt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.adapter.PhongTroAdapter
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.ActivityPhongTrongBinding
import com.example.appqlpt.databinding.FragmentPhongTrongBinding
import com.example.appqlpt.model.Phong

class ActivityPhongTrong : AppCompatActivity() {
    private lateinit var binding: ActivityPhongTrongBinding
    var listPhongTrong= listOf<Phong>()
    var maKhu=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhongTrongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tbPhongTrong
        setSupportActionBar(binding.tbPhongTrong)
        val ab = getSupportActionBar()
        if (ab != null){
            ab.setHomeAsUpIndicator(R.drawable.back_left)
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        val srf=this?.getSharedPreferences(FILE_NAME, AppCompatActivity.MODE_PRIVATE)
        maKhu=srf?.getString(MA_KHU_KEY, "")!!
        reload()

    }

    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.itemId;
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }

    private fun reload(){
        val phongDao= this?.let { PhongDao(it) }!!
        listPhongTrong=phongDao.getAllInPhongByMaKhu(maKhu).filter { it.trang_thai_phong==0 }
        val phongTroAdapter= PhongTroAdapter(listPhongTrong)
        binding.recyclerDanhSachPhongTrong.adapter=phongTroAdapter
        binding.recyclerDanhSachPhongTrong.layoutManager= LinearLayoutManager(this)
    }
}