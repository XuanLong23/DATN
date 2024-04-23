package com.example.appqlpt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.tabs.TabLayoutMediator
import com.example.appqlpt.R

import com.example.appqlpt.adapter.ViewpagerDanhSachPhongAdapter
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.ActivityDanhSachPhongBinding
import com.example.appqlpt.model.Phong


class ActivityDanhSachPhong : AppCompatActivity() {
    private lateinit var binding: ActivityDanhSachPhongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDanhSachPhongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbDanhSachPhong)
        val ab = supportActionBar
        if (ab != null){
            ab.setHomeAsUpIndicator(R.drawable.back_left)
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        val adapter = ViewpagerDanhSachPhongAdapter(supportFragmentManager, lifecycle)
        binding.viewpagerDanhSachPhong.adapter = adapter
        TabLayoutMediator(binding.tabDanhSachPhong, binding.viewpagerDanhSachPhong) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.text = "Tất cả phòng"
                }
                1 -> {
                    tab.text = "Phòng trống"
                }
                else -> tab.text = "Phòng đang ở"
            }
        }.attach()
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.getItemId();
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }
}