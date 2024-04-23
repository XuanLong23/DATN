package com.example.appqlpt.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.example.appqlpt.R
import com.example.appqlpt.adapter.*
import com.example.appqlpt.database.HopDongDao
import com.example.appqlpt.databinding.ActivityDanhSachHopDongBinding
import com.example.appqlpt.fragment.FragmentQuanLy
import com.example.appqlpt.model.HopDong
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ActivityDanhSachHopDong : AppCompatActivity() {
    private lateinit var binding: ActivityDanhSachHopDongBinding
    var listHopDong = listOf<HopDong>()
    private var maKhu=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDanhSachHopDongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolBerDSHopDong
        setSupportActionBar(binding.toolBerDSHopDong )
        val ab = getSupportActionBar()
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)
//        val srf = this.binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
//        maKhu = srf.getString(MA_KHU_KEY, "")!!
//        reloadDSHopDong()
        val adapter = ViewpagerDanhSachHopDongDSAdapter(supportFragmentManager, lifecycle)
        binding.viewpagerDanhSachHopDong.adapter = adapter
        TabLayoutMediator(binding.tabDanhSachHopDong, binding.viewpagerDanhSachHopDong) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.text = "Còn hiệu lực"
                }
                1 -> {
                    tab.text = "Hết hiệu lực"
                }

                else -> tab.text = "Còn hiệu lực"
            }
        }.attach()
    }



    fun chuyenActivity(){
        val intent = Intent(this@ActivityDanhSachHopDong, ActivityManHinhChinhChuTro::class.java)
        startActivity(intent)
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.getItemId();
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }

//    fun updateHopDong(hopDong: HopDong) {
//            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
//
//            //============================
//            val simpleDateFormatNow = SimpleDateFormat("yyyy-MM-dd")
//            var mYearNow = 0
//            var mMonthNow = 0
//            var mDayNow = 0
//            val c = Calendar.getInstance() as GregorianCalendar?
//            mYearNow = (c as Calendar).get(Calendar.YEAR)
//            mMonthNow = c!!.get(Calendar.MONTH)
//            mDayNow = c!!.get(Calendar.DAY_OF_MONTH)
//            val cNow = GregorianCalendar(mYearNow, mMonthNow, mDayNow)
//            for (i in 0..7) {
//                if (i == 0) {
//                    if (dateFormat.format(tinhNgaySapHetHanHopDong(hopDong, i)!!.time) <= simpleDateFormatNow.format(cNow!!.time)) {
//                        updateHDHetHan(hopDong)
//                        //reloadDanhSanhHD(binding)
//                        reloadDSHopDong()
//
//                    }
//                } else {
//                    if (dateFormat.format(tinhNgaySapHetHanHopDong(hopDong, i)!!.time) == simpleDateFormatNow.format(cNow!!.time)) {
//                        updateHDSapHetHan(hopDong)
//                        reloadDSHopDong()
//
//                    }
//                }
//            }

    //}
//        fun tinhNgaySapHetHanHopDong(hopDong: HopDong, a:Int): GregorianCalendar {
//            val ngayHetHan = hopDong.ngay_hop_dong
//            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
//            val newDate = dateFormat.parse(ngayHetHan)
//            val calendar = Calendar.getInstance()
//            if (newDate != null) {
//                calendar.time = newDate
//            }
//
//            val monthNgaySapHetHan = calendar.get(Calendar.MONTH)
//            val dayNgaySapHetHan = calendar.get(Calendar.DAY_OF_MONTH) - a
//            val yearNgaySapHetHan = calendar.get(Calendar.YEAR)
//            return GregorianCalendar(yearNgaySapHetHan, monthNgaySapHetHan, dayNgaySapHetHan)
//        }
//
//        fun updateHDHetHan(hopDong: HopDong){
//            val hopDongNew = HopDong(
//                ma_hop_dong = hopDong.ma_hop_dong,
//                ma_phong = hopDong.ma_phong,
//                ma_nguoi_dung = hopDong.ma_nguoi_dung,
//                thoi_han = hopDong.thoi_han,
//                ngay_o = hopDong.ngay_o,
//                ngay_hop_dong = hopDong.ngay_hop_dong,
//                tien_coc = hopDong.tien_coc,
//                anh_hop_dong = hopDong.anh_hop_dong,
//                trang_thai_hop_dong = 0,
//                ngay_lap_hop_dong = hopDong.ngay_lap_hop_dong
//            )
//            HopDongDao(binding.root.context).updateHopDong(hopDongNew)
//        }
//        fun updateHDSapHetHan(hopDong: HopDong){
//            val hopDongNew = HopDong(
//                ma_hop_dong = hopDong.ma_hop_dong,
//                ma_phong = hopDong.ma_phong,
//                ma_nguoi_dung = hopDong.ma_nguoi_dung,
//                thoi_han = hopDong.thoi_han,
//                ngay_o = hopDong.ngay_o,
//                ngay_hop_dong = hopDong.ngay_hop_dong,
//                tien_coc = hopDong.tien_coc,
//                anh_hop_dong = hopDong.anh_hop_dong,
//                trang_thai_hop_dong = 2,
//                ngay_lap_hop_dong = hopDong.ngay_lap_hop_dong
//            )
//            HopDongDao(binding.root.context).updateHopDong(hopDongNew)
//        }


//    fun reloadDSHopDong(){
//        val srf = this.binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
//        maKhu = srf.getString(MA_KHU_KEY, "")!!
//        listHopDong = HopDongDao(this@ActivityDanhSachHopDong).getAllInHopDongByMaKhu(maKhu)
//        //listHopDong=HopDongDao(this@ActivityDanhSachHopDong).getAllInHopDong()
//        val hopDongAdapter = HopDongAdapter(listHopDong)
//        this.binding.recyclerDanhSachHopDong.adapter = hopDongAdapter
//        this.binding.recyclerDanhSachHopDong.layoutManager = LinearLayoutManager(this)
//    }

}