package com.example.appqlpt.activity


import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appqlpt.R
import com.example.appqlpt.adapter.MA_PHONG_HOA_DON_KEY
import com.example.appqlpt.database.*
import com.example.appqlpt.databinding.FragmentTaoHoaDonBinding
import com.example.appqlpt.model.HoaDon
import java.text.SimpleDateFormat
import java.util.*

class ActivityTaoHoaDon : AppCompatActivity() {
    private lateinit var binding: FragmentTaoHoaDonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTaoHoaDonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val maPhong= intent.getStringExtra(MA_PHONG_HOA_DON_KEY)!!
        setSupportActionBar(binding.tbTaoHoaDon1)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)


        val phong = maPhong.let { PhongDao(this).getPhongById(it) }!!
//        val srf=binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
//        maKhu= srf.getString(MA_KHU_KEY, "")!!
//        list = HoaDonDao(this).getAllInHoaDonByMaKhu(maKhu)
        val listND = KhachThueDao(binding.root.context).getKhachThueByMaPhong(maPhong)
        val soNguoiO = listND.filter { it.trang_thai_o == 1 }.size
        val listDV = DichVuDao(binding.root.context).getAllInDichVuByPhong(maPhong)
        val dichVuDien = listDV.find { it.ten_dich_vu=="Tiền điện"}
        val dichVuNuoc= listDV.find { it.ten_dich_vu=="Tiền nước" }
        val dichVuTheoDauNguoi=listDV.filter { it.trang_thai_dich_vu==2 }.sumOf { it.gia_dich_vu *soNguoiO }
        val dichVuTheoPhong=listDV.filter { it.trang_thai_dich_vu==3 }.sumOf { it.gia_dich_vu }
        val loaiDichVuPhongDao=DichVuDao(this@ActivityTaoHoaDon)
        binding.edGiaDichVu.setText(dinhDangTienTaoHoaDon((dichVuTheoDauNguoi*soNguoiO+dichVuTheoPhong).toString()))
        if(dichVuDien!=null && dichVuNuoc!=null){
            when {
                dichVuDien.so_cu<0 -> {
                    binding.edSoDienCu.setText("")
                }
                dichVuNuoc.so_cu<0 -> {
                    binding.edSoNuocCu.setText("")

                }
                dichVuNuoc.trang_thai_dich_vu !=1 -> {
                    binding.edSoNuocCu.apply {
                        this.setText("0")
                        this.visibility=View.INVISIBLE
                    }
                    binding.edSoNuocMoi.apply {
                        this.setText("0")
                        this.visibility=View.INVISIBLE
                    }
                }
                else -> {
                    binding.edSoDienCu.setText(dinhDangTienTaoHoaDon(dichVuDien.so_moi.toString()))
                    binding.edSoNuocCu.setText(dinhDangTienTaoHoaDon(dichVuNuoc.so_moi.toString()))
                }
            }
        }

        binding.edTenPhongTro.setText(phong.ten_phong)
        binding.edGiaThue.setText("${dinhDangTienTaoHoaDon(phong.gia_thue.toString())} VND")
        binding.btnLuuHoaDon.setOnClickListener {
            if (validate() <1) {
                thongBaoLoi("Nhập đầy đủ thông tin")
            }else{

                val soDienCu=binding.edSoDienCu.text.toString().toInt()
                val soDienMoi=binding.edSoDienMoi.text.toString().toInt()
                val soNuocCu=binding.edSoNuocCu.text.toString().toInt()
                val soNuocMoi=binding.edSoNuocMoi.text.toString().toInt()
                val mienGiam=binding.edTienMienGiam.text.toString().toInt()
                if(dichVuDien!=null && dichVuNuoc !=null) {
                    val tong=(dichVuDien.gia_dich_vu*(soDienMoi-soDienCu))+
                            (dichVuNuoc.gia_dich_vu * (soNuocMoi-soNuocCu))+
                            phong.gia_thue.toInt()+
                            dichVuTheoDauNguoi*soNguoiO+dichVuTheoPhong-mienGiam
                    // Toast.makeText(binding.root.context, tong.toString(), Toast.LENGTH_SHORT).show()
                    val spf = SimpleDateFormat("yyyy-MM-dd")
                    val date = spf.format(Date())
                    val id = UUID.randomUUID().toString()
                    val check: Int = if (binding.chkDaThanhToan.isChecked) {
                        1
                    } else {
                        0
                    }
                    val hoaDon = HoaDon(
                        ma_hoa_don = id,
                        ma_phong = maPhong,
                        gia_thue = phong.gia_thue.toInt(),
                        so_dien = soDienMoi-soDienCu,
                        so_nuoc = soNuocMoi-soNuocCu,
                        gia_dich_vu = (dichVuTheoDauNguoi + dichVuTheoPhong),
                        mien_giam = binding.edTienMienGiam.text.toString().toInt(),
                        trang_thai_hoa_don = check,
                        ngay_tao_hoa_don = date,
                        tong = tong.toLong()
                    )
                    val daoHoaDon: Long = HoaDonDao(binding.root.context).insertHoaDon(hoaDon)
                    when {
                        dichVuDien != null && dichVuDien.so_cu < 0 -> {
                            dichVuDien.ma_dich_vu.let { it1 ->
                                loaiDichVuPhongDao.updateDichVu(
                                    it1,
                                    soDienCu,
                                    soDienMoi
                                )
                            }
                        }
                        dichVuDien != null && dichVuDien.so_cu > 0 -> {
                            dichVuDien.ma_dich_vu.let { it1 ->
                                loaiDichVuPhongDao.updateDichVu(
                                    it1,
                                    dichVuDien.so_moi,
                                    soDienMoi
                                )
                            }
                        }
                    }
                    when {
                        dichVuNuoc != null && dichVuNuoc.so_cu < 0 && dichVuNuoc.trang_thai_dich_vu == 1 -> {
                            loaiDichVuPhongDao.updateDichVu(
                                dichVuNuoc.ma_dich_vu,
                                soNuocCu,
                                soNuocMoi
                            )
                        }
                        dichVuNuoc != null && dichVuNuoc.so_cu > 0 && dichVuNuoc.trang_thai_dich_vu == 1 -> {
                            loaiDichVuPhongDao.updateDichVu(
                                dichVuNuoc.ma_dich_vu,
                                dichVuNuoc.so_moi,
                                soNuocMoi
                            )
                        }
                    }

                    if (daoHoaDon > 0) {
                        thongBaoXacNhan("Thêm Thành Công")
                    } else {
                        thongBaoLoi("Thêm Không Thành Công")
                    }
                }
            }
        }
        binding.btnHuyHoaDon.setOnClickListener {

            binding.edSoDienMoi.setText("")
            binding.edSoNuocMoi.setText("")
            binding.edTienMienGiam.setText("")
            binding.chkDaThanhToan.isChecked  = false
        }

    }
    fun dinhDangTienTaoHoaDon(gia : String): String{
        val tienFormat = String.format("%,d",gia.toLong()).replace(",",".")
        return tienFormat
    }
    fun validate(): Int {
        var check = -1

        if (binding.edSoDienCu.text.toString().isNotBlank() &&
            binding.edSoNuocCu.text.toString().isNotBlank() &&
            binding.edSoDienMoi.text.toString().isNotBlank() &&
            binding.edSoNuocMoi.text.toString().isNotBlank() &&
            binding.edTienMienGiam.text.toString().isNotBlank()) {
            check = 1
        }
        return check
    }
    fun thongBaoXacNhan(loi : String){
        val bundle = AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo")
        bundle.setMessage(loi)
        bundle.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            finish()
        })
        bundle.setNegativeButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()

        })
        bundle.show()
    }
    fun thongBaoLoi(loi : String){
        val bundle = AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo")
        bundle.setMessage(loi)
        bundle.setNegativeButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()

        })
        bundle.show()
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.itemId;
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }
}