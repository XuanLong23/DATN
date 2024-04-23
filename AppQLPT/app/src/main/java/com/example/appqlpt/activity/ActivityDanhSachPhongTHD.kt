package com.example.appqlpt.activity

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.appqlpt.R
import com.example.appqlpt.adapter.DanhSachPhongDaOAdapter
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.database.HoaDonDao
import com.example.appqlpt.database.KhachThueDao
import com.example.appqlpt.databinding.ActivityDanhSachTaoHoaDonBinding
import com.example.appqlpt.model.KhachThue
import com.example.appqlpt.model.Phong

const val Ma_PHONG_KEY = "Ma Phong"
class ActivityDanhSachPhongTHD : AppCompatActivity() {
    private lateinit var binding: ActivityDanhSachTaoHoaDonBinding
    var list = mutableListOf<Phong>()
    var maKhu = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDanhSachTaoHoaDonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbTaoHoaDon)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)


        val srf=binding.root.context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE)
        maKhu= srf.getString(MA_KHU_KEY, "").toString()

        onResume()

    }

    override fun onResume() {
        super.onResume()
        val phongDao= KhachThueDao(binding.root.context)
        list= phongDao.getAllInPhongByMaKhu(maKhu).filter { it.trang_thai_phong == 1} as MutableList<Phong>
        val danhSachPhongDaOAdapter= DanhSachPhongDaOAdapter(list,binding.root.context)
        binding.recActivityTaoHopDong1.adapter = danhSachPhongDaOAdapter
        danhSachPhongDaOAdapter.notifyDataSetChanged()


    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.getItemId();
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }
}