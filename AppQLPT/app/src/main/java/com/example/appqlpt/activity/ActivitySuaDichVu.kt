package com.example.appqlpt.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_DICH_VU_KEY
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.database.DichVuDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.ActivitySuaDichVuBinding
import com.example.appqlpt.databinding.DialogThemLoaiDichVuBinding
import com.example.appqlpt.model.DichVu
import java.util.*


class ActivitySuaDichVu : AppCompatActivity() {
    private lateinit var binding:ActivitySuaDichVuBinding
    private var maKhu=""
    private val listLoaiThanhToan= listOf(
        Pair("Miễn phí/Không sử dung",0), Pair("Tính theo đầu người",2), Pair("Tính theo phòng",3),
        Pair("Tính theo đồng hồ", 1, )
    )
    private var trangThaiLoaiDichVu=1
    var loaiDichVu: DichVu?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySuaDichVuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val idDichVu=intent.getStringExtra(MA_DICH_VU_KEY)!!
        val dichVu= DichVuDao(this).getDichVuById(idDichVu)
        binding.edGiaDichVu.setText(dichVu?.gia_dich_vu.toString())
        binding.edTenDichVu.setText(dichVu?.ten_dich_vu)
        val loaiHinhThanhtoan=dichVu?.trang_thai_dich_vu
        val thanhToan=listLoaiThanhToan.first { it.second==loaiHinhThanhtoan }
        binding.tvTenLoaiThanhToan.text=thanhToan.first
        val srf = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        val maDichVu=UUID.randomUUID().toString()
        setSupportActionBar(binding.tbQuanLyDichVu)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)
        val loaiDichVuPhongDao=DichVuDao(this)
        val phongDao=PhongDao(this)
        binding.btnThemLoaiDV.setOnClickListener{
            val tenDichVu=binding.edTenDichVu.text.toString()
            val giaDichVu=binding.edGiaDichVu.text.toString().toInt()
            loaiDichVu = DichVu(
                ma_dich_vu = maDichVu,
                ten_dich_vu = tenDichVu,
                gia_dich_vu = giaDichVu,
                trang_thai_dich_vu = trangThaiLoaiDichVu,
                ma_khu_tro = maKhu,
                ma_phong = "",
                so_cu =-1,
                so_moi  = -1
            )
            val kq= dichVu?.ten_dich_vu?.let { it1 ->
                loaiDichVuPhongDao.updateDichVuByMaKhuVaTen(loaiDichVu!!,
                    it1
                )
            }
            if (kq != null) {
                if (kq>0)
                    thongBao("Sửa thành công", this)
            }
        }
        binding.lnLoaiThanhToan.setOnClickListener {
            val build = AlertDialog.Builder(this).create()
            val dialog = DialogThemLoaiDichVuBinding.inflate(LayoutInflater.from(this))
            val listText= listLoaiThanhToan.map { it.first }
            val arrayAdapter= ArrayAdapter(this, R.layout.layout_item_chon_dich_vu, listText)
            dialog.listLoaiDichVu.adapter=arrayAdapter
            dialog.tvDichVuPhong.text="Loại hình thanh toán"
            dialog.listLoaiDichVu.setOnItemClickListener { adapterView, view, i, l ->
                binding.tvTenLoaiThanhToan.text= listLoaiThanhToan[i].first
                trangThaiLoaiDichVu= listLoaiThanhToan[i].second
                build.dismiss()
            }
            dialog.btnHuyLoaiDV.setOnClickListener {
                build.cancel()
            }
            build.setView(dialog.root)
            build.show()
        }
        binding.btnHuyLoaiDV.setOnClickListener{
            finish()
        }
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.itemId;
        if (id==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    fun thongBao(message : String, context: Context){
        val bundle = AlertDialog.Builder(context)
        bundle.setTitle("Thông báo")
        bundle.setMessage(message)
        bundle.setNegativeButton("OK") { dialog, which ->
            dialog.cancel()
            finish()
        }
        bundle.show()
    }
}