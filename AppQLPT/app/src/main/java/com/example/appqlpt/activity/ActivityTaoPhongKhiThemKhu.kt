package com.example.appqlpt.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.appqlpt.MainActivity
import com.example.appqlpt.R
import com.example.appqlpt.database.DichVuDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.ActivityTaoPhongKhiThemKhuBinding
import com.example.appqlpt.databinding.DialogThemLoaiDichVuBinding
import com.example.appqlpt.model.DichVu
import com.example.appqlpt.model.Phong
import java.util.UUID

class ActivityTaoPhongKhiThemKhu : AppCompatActivity() {
    private lateinit var binding:ActivityTaoPhongKhiThemKhuBinding
    private val listDichVuDien= listOf<Triple<String,Int,Int>>(
        Triple("Miễn phí/Không sử dụng",0,0), Triple("Tính theo đầu người",2,70000),
        Triple("Tính theo phòng",3,150000),Triple("Tính theo đồng hồ (phổ biến)",1,3000)
    )
    private val listDichVuNuoc= listOf<Triple<String,Int,Int>>(
        Triple("Miễn phí/Không sử dụng",0,0),Triple("Tính theo đầu người",2,70000),
        Triple("Tính theo phòng",3,100000),Triple("Tính theo đồng hồ (phổ biến)",1,20000)
    )
    private val listDichVuRac= listOf<Triple<String,Int,Int>>(
        Triple("Miễn phí/Không sử dụng",0,0),Triple("Tính theo đầu người",2,20000),
        Triple("Tính theo phòng",3,120000)
    )
    private val listDichVuMang= listOf<Triple<String,Int,Int>>(
        Triple("Miễn phí/Không sử dụng",0,0),Triple("Tính theo đầu người",2,50000),
        Triple("Tính theo phòng",3,100000)
    )
    private var trangThaiDichVuDien=1
    private var trangThaiDichVuNuoc=1
    private var trangThaiDichVuRac=0
    private var trangThaiDichVuMang=0

    private var giaDien=3000
    private var giaNuoc=20000
    private var giaRac=0
    private var giaMang=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTaoPhongKhiThemKhuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val phongDao= PhongDao(this)
        val dichVuDao=DichVuDao(this)

        val soLuongPhong=intent.getIntExtra(SO_LUONG_PHONG_KEY,0)
        val maKhu=intent.getStringExtra(MA_KHU_KEY)!!
        binding.edtSoPhongTro.isEnabled=false
        binding.edtSoPhongTro.setTextColor(Color.BLACK)
        binding.edtSoPhongTro.setText(soLuongPhong.toString())

        binding.tvDichVuDien.setOnClickListener {
            val build=AlertDialog.Builder(this).create()
            val dialogbinding=DialogThemLoaiDichVuBinding.inflate(LayoutInflater.from(this))
            val listText=listDichVuDien.map { it.first }
            dialogbinding.lvLuaChonDichVu.adapter=ArrayAdapter(this,R.layout.layout_item_chon_dich_vu,listText)
            dialogbinding.tvLoaiDichVu.text="Dịch vụ Điện"
            dialogbinding.lvLuaChonDichVu.setOnItemClickListener { adapterView, view, i, l ->
                binding.tvDichVuDien.text=listDichVuDien[i].first
                trangThaiDichVuDien=listDichVuDien[i].second
                giaDien=listDichVuDien[i].third
                build.dismiss()
            }
            dialogbinding.btnHuyChon.setOnClickListener {
                build.cancel()
            }
            build.setView(dialogbinding.root)
            build.show()
        }
        binding.tvDichVuNuoc.setOnClickListener {
            val build=AlertDialog.Builder(this).create()
            val dialogbinding=DialogThemLoaiDichVuBinding.inflate(LayoutInflater.from(this))
            val listText=listDichVuNuoc.map { it.first }
            dialogbinding.lvLuaChonDichVu.adapter=ArrayAdapter(this,R.layout.layout_item_chon_dich_vu,listText)
            dialogbinding.tvLoaiDichVu.text="Dịch vụ Nước"
            dialogbinding.lvLuaChonDichVu.setOnItemClickListener { adapterView, view, i, l ->
                binding.tvDichVuNuoc.text=listDichVuNuoc[i].first
                trangThaiDichVuNuoc=listDichVuNuoc[i].second
                giaNuoc=listDichVuNuoc[i].third
                build.dismiss()
            }
            dialogbinding.btnHuyChon.setOnClickListener {
                build.cancel()
            }
            build.setView(dialogbinding.root)
            build.show()
        }
        binding.tvDichVuRac.setOnClickListener {
            val build=AlertDialog.Builder(this).create()
            val dialogbinding=DialogThemLoaiDichVuBinding.inflate(LayoutInflater.from(this))
            val listText=listDichVuRac.map { it.first }
            dialogbinding.lvLuaChonDichVu.adapter=ArrayAdapter(this,R.layout.layout_item_chon_dich_vu,listText)
            dialogbinding.tvLoaiDichVu.text="Dịch vụ Rác"
            dialogbinding.lvLuaChonDichVu.setOnItemClickListener { adapterView, view, i, l ->
                binding.tvDichVuRac.text=listDichVuRac[i].first
                trangThaiDichVuRac=listDichVuRac[i].second
                giaRac=listDichVuRac[i].third
                build.dismiss()
            }
            dialogbinding.btnHuyChon.setOnClickListener {
                build.cancel()
            }
            build.setView(dialogbinding.root)
            build.show()
        }
        binding.tvDichVuMang.setOnClickListener {
            val build=AlertDialog.Builder(this).create()
            val dialogbinding=DialogThemLoaiDichVuBinding.inflate(LayoutInflater.from(this))
            val listText=listDichVuMang.map { it.first }
            dialogbinding.lvLuaChonDichVu.adapter=ArrayAdapter(this,R.layout.layout_item_chon_dich_vu,listText)
            dialogbinding.tvLoaiDichVu.text="Dịch vụ Mạng"
            dialogbinding.lvLuaChonDichVu.setOnItemClickListener { adapterView, view, i, l ->
                binding.tvDichVuMang.text=listDichVuMang[i].first
                trangThaiDichVuMang=listDichVuMang[i].second
                giaMang=listDichVuMang[i].third
                build.dismiss()
            }
            dialogbinding.btnHuyChon.setOnClickListener {
                build.cancel()
            }
            build.setView(dialogbinding.root)
            build.show()
        }

        binding.btnLuuThemPhong.setOnClickListener {
            val tenPhong=binding.edtTenPhongTro.text.toString()
            val soLuongNguoiO=binding.edtSoNguoiToiDa.text.toString().toInt()
            val dienTich=binding.edtDienTich.text.toString().toInt()
            val giaThue=binding.edtGiaThue.text.toString().toLong()
            repeat(soLuongPhong){
                val idPhong=UUID.randomUUID().toString()
                val phong=Phong(
                    ma_phong = idPhong,
                    ten_phong = "$tenPhong ${it+1}",
                    trang_thai_phong = 0,
                    so_nguoi_o = soLuongNguoiO,
                    gia_thue = giaThue,
                    dien_tich = dienTich,
                    ma_khu = maKhu
                )
                val dien=DichVu(
                    ma_dich_vu = UUID.randomUUID().toString(),
                    ten_dich_vu ="Tiền điện",
                    gia_dich_vu =giaDien,
                    ma_phong = idPhong,
                    ma_khu_tro = maKhu,
                    trang_thai_dich_vu =trangThaiDichVuDien,
                    so_moi = -1,
                    so_cu = -1
                )
                val nuoc=DichVu(
                    ma_dich_vu = UUID.randomUUID().toString(),
                    ten_dich_vu ="Tiền nước",
                    gia_dich_vu =giaNuoc,
                    ma_phong = idPhong,
                    ma_khu_tro = maKhu,
                    trang_thai_dich_vu =trangThaiDichVuNuoc,
                    so_moi = -1,
                    so_cu = -1
                )
                val rac=DichVu(
                    ma_dich_vu = UUID.randomUUID().toString(),
                    ten_dich_vu ="Tiền rác",
                    gia_dich_vu =giaRac,
                    ma_phong = idPhong,
                    ma_khu_tro = maKhu,
                    trang_thai_dich_vu =trangThaiDichVuRac,
                    so_moi = -1,
                    so_cu = -1
                )
                val mang=DichVu(
                    ma_dich_vu = UUID.randomUUID().toString(),
                    ten_dich_vu ="Tiền mạng",
                    gia_dich_vu =giaMang,
                    ma_phong = idPhong,
                    ma_khu_tro = maKhu,
                    trang_thai_dich_vu =trangThaiDichVuMang,
                    so_moi = -1,
                    so_cu = -1
                )
                phongDao.insertPhong(phong)
                dichVuDao.insertDichVu(dien)
                dichVuDao.insertDichVu(nuoc)
                dichVuDao.insertDichVu(rac)
                dichVuDao.insertDichVu(mang)
            }
            val intent= Intent(this@ActivityTaoPhongKhiThemKhu, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}