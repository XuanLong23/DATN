package com.example.appqlpt.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.appqlpt.R
import com.example.appqlpt.database.ChuTroDao
import com.example.appqlpt.databinding.ActivityCapNhatThongTinChuNhaBinding
import com.example.appqlpt.model.ChuTro

class ActivityCapNhatThongTinChuNha : AppCompatActivity() {
    private lateinit var binding: ActivityCapNhatThongTinChuNhaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCapNhatThongTinChuNhaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tbDangKy
        setSupportActionBar(binding.tbDangKy)
        val ab = getSupportActionBar()
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)
        val i = intent
        val admin: ChuTro = i.getSerializableExtra("admin") as ChuTro
        binding.edHoVaTen.setText(admin.ho_ten)
        binding.edNgaySinh.setText(admin.ngay_sinh)
        binding.edMatKhauDangKy.setText(admin.mat_khau)
        binding.edSoDienThoai.setText(admin.sdt)
        binding.edSoTaiKhoan.setText(admin.stk)
        binding.edNganHang.setText(admin.ngan_hang)
        binding.edTenDangNhapDangKy.setText(admin.ten_dang_nhap)
        binding.edTenDangNhapDangKy.isEnabled = false
        binding.edTenDangNhapDangKy.setTextColor(Color.BLACK)
        binding.btnLuuCapNhat.setOnClickListener {
            if (validate()<1){
                thongBaoLoi("Dữ liệu không được để trống!")
                return@setOnClickListener
            }else{
                val adminNew = ChuTro (
                    sdt = binding.edSoDienThoai.text.toString(),
                    ten_dang_nhap = admin.ten_dang_nhap,
                    ho_ten = binding.edHoVaTen.text.toString(),
                    stk = binding.edSoTaiKhoan.text.toString(),
                    ngan_hang = binding.edNganHang.text.toString(),
                    ngay_sinh = binding.edNgaySinh.text.toString(),
                    mat_khau = binding.edMatKhauDangKy.text.toString()
                )
                val dao = ChuTroDao(this@ActivityCapNhatThongTinChuNha).updateChuTro(adminNew)
                if (dao>0){
                    thongBaoThanhCong("Bạn đã cập nhật thành công !!!")
                    xoaTrang()
                }else{
                    thongBaoLoi("Bạn đã cập nhật không thành công tài khoản !!!")
                    xoaTrang()
                }
            }
        }
        binding.btnHuyCapNhat.setOnClickListener {
            xoaTrang()
        }
    }

    fun xoaTrang(){
        binding.edHoVaTen.setText("")
        binding.edMatKhauDangKy.setText("")
        binding.edSoDienThoai.setText("")
        binding.edTenDangNhapDangKy.setText("")
    }
    fun chuyenActivity(){
        val intent = Intent(this@ActivityCapNhatThongTinChuNha, ActivityManHinhChinhChuTro::class.java)
        startActivity(intent)
        finish()
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.getItemId();
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }
    fun validate(): Int {
        var check = -1
        if (binding.edHoVaTen.text.toString().isNotBlank() &&
            binding.edSoDienThoai.text.toString().isNotBlank() &&
            binding.edTenDangNhapDangKy.text.toString().isNotBlank() &&
            binding.edMatKhauDangKy.text.toString().isNotBlank()
            &&
            binding.edSoTaiKhoan.text.toString().isNotBlank()
            &&
            binding.edNgaySinh.text.toString().isNotBlank()) {
            check = 1
        }
        return check
    }

    fun thongBaoLoi(loi : String){
        val bundle = AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo Lỗi")
        bundle.setMessage(loi)
        bundle.setNegativeButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        bundle.show()
    }
    fun thongBaoThanhCong(loi : String){
        val bundle = AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo")
        bundle.setMessage(loi)
        bundle.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
//            val intent = Intent(this@ActivityCapNhatThongTinChuNha,ActivityManHinhChinhChuTro::class.java)
//            startActivity(intent)
            finish()
        })
        bundle.setPositiveButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        bundle.show()
    }
}