package com.example.appqlpt.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.appqlpt.R
import com.example.appqlpt.activity.THONG_TIN_DANG_NHAP
import com.example.appqlpt.activity.USERNAME_KEY
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.database.HoaDonDao
import com.example.appqlpt.database.HopDongDao
import com.example.appqlpt.database.KhuTroDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.FragmentTongQuanBinding
import com.example.appqlpt.model.HoaDon
import com.example.appqlpt.model.HopDong
import com.example.appqlpt.model.KhuTro
import com.example.appqlpt.model.Phong
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FragmentTongQuan : Fragment() {
    private lateinit var binding: FragmentTongQuanBinding
    var listHopDongSapHetHan = listOf<HopDong>()
    var listHopDong= listOf<HopDong>()
    var listHoaDon = listOf<HoaDon>()
    var listPhong = listOf<Phong>()
    var listPhongTrong = listOf<Phong>()
    var listPhongDangThue = listOf<Phong>()
    private var maKhu=""
    private var tenKhu=""
    private var listKhuTro = listOf<KhuTro>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTongQuanBinding.inflate(layoutInflater)


        val srf = binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        //================================
        val admin =
            binding.root.context.getSharedPreferences(
                THONG_TIN_DANG_NHAP,
                AppCompatActivity.MODE_PRIVATE
            ).getString(USERNAME_KEY, "")!!
        listKhuTro = KhuTroDao(binding.root.context).getAllInKhuTroByChuTro(admin)

        val pre =
            binding.root.context.getSharedPreferences(FILE_NAME, AppCompatActivity.MODE_PRIVATE)

        val khuTro = listKhuTro.find { it.ma_khu_tro == maKhu }
        binding.tvTenKhuTongQuan.text = ("Khu ") + khuTro?.ten_khu_tro
        pre.edit().putString(MA_KHU_KEY, maKhu).commit()
        onResume()

        binding.phongTrong.setOnClickListener {
            val intent = Intent(context, ActivityPhongTrong::class.java)
            startActivity(intent)
        }
        binding.phongDangChoThue.setOnClickListener {
            val intent = Intent(context, ActivityPhongDangThue::class.java)
            startActivity(intent)
        }
        binding.phongSapHetHan.setOnClickListener {

            val intent = Intent(context, ActivityPhongSapHetHopDong::class.java)
            startActivity(intent)
        }


        binding.phongChuaDongTien.setOnClickListener {
            val intent = Intent(context, ActivityPhongChuaDongTien::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun updateHopDong() {
        listHopDong = HopDongDao(binding.root.context).getAllInHopDongByMaKhu(maKhu,1)
        for(i in 0 until  listHopDong.size){
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

            //============================
            val simpleDateFormatNow = SimpleDateFormat("yyyy-MM-dd")
            var mYearNow = 0
            var mMonthNow = 0
            var mDayNow = 0
            val c = Calendar.getInstance() as GregorianCalendar?
            mYearNow = (c as Calendar).get(Calendar.YEAR)
            mMonthNow = c!!.get(Calendar.MONTH)
            mDayNow = c!!.get(Calendar.DAY_OF_MONTH)
            val cNow = GregorianCalendar(mYearNow, mMonthNow, mDayNow)
            for (y in 0..7) {
                if (y == 0) {
                    if (dateFormat.format(tinhNgaySapHetHanHopDong(listHopDong.get(i), y)!!.time) <= simpleDateFormatNow.format(cNow!!.time)) {
                        updateHDHetHan(listHopDong.get(i))
                        //reloadDanhSanhHD(binding)
                        //reloadDSHopDong()

                    }
                } else {
                    if (dateFormat.format(tinhNgaySapHetHanHopDong(listHopDong.get(i), y)!!.time) == simpleDateFormatNow.format(cNow!!.time)) {
                        updateHDSapHetHan(listHopDong.get(i))
                        //reloadDSHopDong()

                    }
                }
            }
        }
    }
    fun tinhNgaySapHetHanHopDong(hopDong: HopDong, a:Int): GregorianCalendar {
        val ngayHetHan = hopDong.ngay_hop_dong
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val newDate = dateFormat.parse(ngayHetHan)
        val calendar = Calendar.getInstance()
        if (newDate != null) {
            calendar.time = newDate
        }

        val monthNgaySapHetHan = calendar.get(Calendar.MONTH)
        val dayNgaySapHetHan = calendar.get(Calendar.DAY_OF_MONTH) - a
        val yearNgaySapHetHan = calendar.get(Calendar.YEAR)
        return GregorianCalendar(yearNgaySapHetHan, monthNgaySapHetHan, dayNgaySapHetHan)
    }

    fun updateHDHetHan(hopDong: HopDong){
        val hopDongNew = HopDong(
            ma_hop_dong = hopDong.ma_hop_dong,
            ma_phong = hopDong.ma_phong,
            ma_khach_thue = hopDong.ma_khach_thue,
            thoi_han = hopDong.thoi_han,
            ngay_o = hopDong.ngay_o,
            ngay_hop_dong = hopDong.ngay_hop_dong,
            tien_coc = hopDong.tien_coc,
            anh_hop_dong = hopDong.anh_hop_dong,
            trang_thai_hop_dong = 0,
            hieu_luc_hop_dong = hopDong.hieu_luc_hop_dong,
            ngay_lap_hop_dong = hopDong.ngay_lap_hop_dong
        )
        HopDongDao(binding.root.context).updateHopDong(hopDongNew)
    }
    fun updateHDSapHetHan(hopDong: HopDong){
        val hopDongNew = HopDong(
            ma_hop_dong = hopDong.ma_hop_dong,
            ma_phong = hopDong.ma_phong,
            ma_khach_thue = hopDong.ma_khach_thue,
            thoi_han = hopDong.thoi_han,
            ngay_o = hopDong.ngay_o,
            ngay_hop_dong = hopDong.ngay_hop_dong,
            tien_coc = hopDong.tien_coc,
            anh_hop_dong = hopDong.anh_hop_dong,
            trang_thai_hop_dong = 2,
            hieu_luc_hop_dong = hopDong.hieu_luc_hop_dong,
            ngay_lap_hop_dong = hopDong.ngay_lap_hop_dong
        )
        HopDongDao(binding.root.context).updateHopDong(hopDongNew)
    }
    override fun onResume() {
        super.onResume()
        listPhong = PhongDao(binding.root.context).getAllInPhongByMaKhu(maKhu)
        listPhongDangThue=PhongDao(binding.root.context).getAllInPhongByMaKhu(maKhu).filter { it.trang_thai_phong==1 }
        listPhongTrong=PhongDao(binding.root.context).getAllInPhongByMaKhu(maKhu).filter { it.trang_thai_phong==0 }
        updateHopDong()
        listHopDongSapHetHan = HopDongDao(binding.root.context).getHopDongSapHetHanByMaKhu(maKhu,2,1)
        binding.tvPhongSapHetHanHD.setText(""+listHopDongSapHetHan.size)
        binding.tvSoPhongTongQuan.setText(""+listPhong.size)
        binding.tvPhongTrong.setText(""+listPhongTrong.size)
        binding.tvPhongDangThue.setText(""+listPhongDangThue.size)


        val sdf = SimpleDateFormat("yyyy-MM")
        val ngay = sdf.format(Date())
        listHoaDon = HoaDonDao(binding.root.context).getAllInHoaDonByMaKhu(maKhu).filter { it.trang_thai_hoa_don == 0 && ngay in it.ngay_tao_hoa_don }
        binding.slPhongChuaDongTien.setText(""+listHoaDon.size)
    }
}