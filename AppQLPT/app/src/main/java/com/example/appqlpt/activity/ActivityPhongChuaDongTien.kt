package com.example.appqlpt.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.HoaDonAdapter
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.database.HoaDonDao
import com.example.appqlpt.databinding.ActivityPhongChuaDongTienBinding
import com.example.appqlpt.databinding.ActivityPhongSapHetHopDongBinding
import com.example.appqlpt.model.HoaDon
import java.text.SimpleDateFormat
import java.util.*

class ActivityPhongChuaDongTien : AppCompatActivity() {
    private lateinit var binding: ActivityPhongChuaDongTienBinding
    private var maKhu = ""
    private var list = listOf<HoaDon>()
    private var date=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhongChuaDongTienBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tbDanhSachPhong
        setSupportActionBar(binding.tbDanhSachPhong)
        val ab = getSupportActionBar()
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)
        val srf=binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu= srf.getString(MA_KHU_KEY, "")!!
        val sdf = SimpleDateFormat("yyyy-MM")
        date=sdf.format(Date()).toString()
        onResume()
    }

    fun chuyenActivity(){
        val intent = Intent(this@ActivityPhongChuaDongTien, ActivityManHinhChinhChuTro::class.java)
        startActivity(intent)
        finish()
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.getItemId();
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }
    override fun onResume() {
        super.onResume()

        val hoaDonDao = HoaDonDao(binding.root.context)
        list = hoaDonDao.getAllInHoaDonByMaKhu(maKhu).filter { it.trang_thai_hoa_don==0 && date in it.ngay_tao_hoa_don }
        val hoaDonAdapter = HoaDonAdapter(list)
        binding.rcyPhongChuaDongTien.adapter = hoaDonAdapter
        hoaDonAdapter.notifyDataSetChanged()
    }
}