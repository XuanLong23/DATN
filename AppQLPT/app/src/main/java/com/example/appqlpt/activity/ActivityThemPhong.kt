package com.example.appqlpt.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.ListDichVuTrongPhongAdapter
import com.example.appqlpt.adapter.ListLoaiDichVuAdapter
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.database.DichVuDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.ActivityThemPhongBinding
import com.example.appqlpt.databinding.DialogThemLoaiDichVuBinding
import com.example.appqlpt.model.DichVu
import com.example.appqlpt.model.Phong
import java.util.UUID

class ActivityThemPhong : AppCompatActivity() {
    private lateinit var binding:ActivityThemPhongBinding
    private var maKhu=""
    private var maPhong=""
    private var check=false
    private var listLoaiDichVu= listOf<DichVu>()
    private var listLoaiDichVuPhong= listOf<DichVu>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityThemPhongBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setSupportActionBar(binding.tbThemPhong)
        val ab = getSupportActionBar()
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)


        maPhong=UUID.randomUUID().toString()
        val phongDao=PhongDao(this)
        val loaiDichVuDao= DichVuDao(this)
        val srf=getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        maKhu=srf.getString(MA_KHU_KEY, "")!!
        val listLoaiDichVu=loaiDichVuDao.getAllInDichVuByKhuTro(maKhu)

        binding.btnThemDichVu.setOnClickListener {
            check=true
            val build = AlertDialog.Builder(this).create()
            val dialog = DialogThemLoaiDichVuBinding.inflate(LayoutInflater.from(this))
            val listLoaiDichVuAdapter=ListLoaiDichVuAdapter(listLoaiDichVu,
                this@ActivityThemPhong, maPhong)
            dialog.listLoaiDichVu.adapter=listLoaiDichVuAdapter
            dialog.btnHuyLoaiDV.setOnClickListener {
                onResume()
                build.dismiss()
            }
                build.setView(dialog.root)
                build.show()
        }

        binding.btnLuuThemPhong.setOnClickListener {
           listLoaiDichVuPhong= loaiDichVuDao.getAllInDichVuByPhong(maPhong)
            if (check && listLoaiDichVuPhong.isNotEmpty()) {
                val soPhong = binding.edSoNguoiOToiDa.text.toString().toIntOrNull() ?: 0
                val tenMacDinh = binding.edTenPhongTro.text.toString()
                val phong = Phong(
                    ma_phong = maPhong,
                    ten_phong = tenMacDinh,
                    dien_tich = binding.edDienTichPhong.text.toString().toInt(),
                    gia_thue = binding.edGiaThue.text.toString().toLong(),
                    so_nguoi_o = soPhong,
                    trang_thai_phong = 0,
                    ma_khu = maKhu
                )
                phongDao.insertPhong(phong)
                thongBaoThanhCong("Thêm phòng thành công")
            }
            else{
                thongBaoLoi("Không thêm được phòng vì chưa chọn loại dịch vụ")
            }
        }
        binding.btnHuyThemPhong.setOnClickListener{
            DichVuDao(this).xoaDichVuByMaPhong(maPhong)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        DichVuDao(this).xoaDichVuByMaPhong(maPhong)
        finish()
    }

    override fun onResume() {
        super.onResume()
        listLoaiDichVu=DichVuDao(this@ActivityThemPhong).getAllInDichVuByPhong(maPhong)
        val listDichVuTrongPhongAdapter=ListDichVuTrongPhongAdapter(listLoaiDichVu, this@ActivityThemPhong)
        binding.rcvListLoaiDichVu.adapter=listDichVuTrongPhongAdapter
        binding.rcvListLoaiDichVu.layoutManager=LinearLayoutManager(this)
    }

    fun thongBaoLoi(loi : String){
        val bundle = androidx.appcompat.app.AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo Lỗi")
        bundle.setMessage(loi)
        bundle.setNegativeButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        bundle.show()
    }
    fun thongBaoThanhCong(loi : String){
        val bundle = androidx.appcompat.app.AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo")
        bundle.setMessage(loi)
        bundle.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
            finish()
        })
        bundle.setPositiveButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        bundle.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId();
        if (id == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }


}