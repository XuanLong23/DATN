package com.example.appqlpt.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.PhongInterface
import com.example.appqlpt.adapter.PhongTrotaoHopDongAdapter
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.ActivityTaoHopDongBinding
import com.example.appqlpt.model.Phong

class ActivityTaoHopDong : AppCompatActivity() {
    private lateinit var binding: ActivityTaoHopDongBinding
    var listMaPhong = listOf<String>()
    var listPhongChuaCoHopDong = mutableListOf<Phong>()
    var listPhong = listOf<Phong>()
    private var maKhu=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaoHopDongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbDanhSachPhong)
        val ab = supportActionBar
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)
        //=======================================
        val srf = binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        onResume()
    }

    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.itemId;
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }

    override fun onResume() {
        super.onResume()
        listPhongChuaCoHopDong = PhongDao(this@ActivityTaoHopDong).getPhongChuaCoHopDong(maKhu) as MutableList<Phong>
        val adapter = PhongTrotaoHopDongAdapter(listPhongChuaCoHopDong, object : PhongInterface {
            override fun OnCLickPhong(pos: Int) {
                val intent = Intent(this@ActivityTaoHopDong,ActivitytaoHopDongMoi::class.java)
                val bundle = Bundle()
                bundle.putString("tenPhong",listPhongChuaCoHopDong[pos].ten_phong)
                bundle.putString("maPhong",listPhongChuaCoHopDong[pos].ma_phong)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        })
        binding.rcyPhongTrongCanTaoHopDong.layoutManager = LinearLayoutManager(this)
        binding.rcyPhongTrongCanTaoHopDong.adapter = adapter
    }

}