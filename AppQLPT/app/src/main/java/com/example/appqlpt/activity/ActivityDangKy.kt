package com.example.appqlpt.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.appqlpt.R
import com.example.appqlpt.database.ChuTroDao
import com.example.appqlpt.databinding.ActivityDangKyBinding
import com.example.appqlpt.model.ChuTro
import com.google.android.material.snackbar.Snackbar

class ActivityDangKy : AppCompatActivity() {
    private lateinit var binding:ActivityDangKyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDangKyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbDangKy)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_left)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnDangKy.setOnClickListener {
            if(validate()<1){
                Snackbar.make(it,"Dữ liệu không được để trống!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                val chuTro=ChuTro(
                    sdt = binding.edtSDT.text.toString(),
                    ten_dang_nhap = binding.edtTenDN.text.toString(),
                    ho_ten = binding.edtHoTen.text.toString(),
                    stk="",
                    ngan_hang = "",
                    ngay_sinh = binding.edtNgaySinh.text.toString(),
                    mat_khau = binding.edtMatKhau.text.toString()
                )
                val chuTroDao=ChuTroDao(this@ActivityDangKy).insertChuTro(chuTro)
                if(chuTroDao>0){
                    thongBaoThanhCong("Tạo thành công tài khoản mới!")
                    xoaTrang()
                }
                else{
                    thongBaoLoi("Tạo tài khoản thất bại!")
                }
            }
        }

    }

    private fun xoaTrang() {
        binding.edtHoTen.setText("")
        binding.edtSDT.setText("")
        binding.edtNgaySinh.setText("")
        binding.edtTenDN.setText("")
        binding.edtMatKhau.setText("")
    }

    private fun thongBaoThanhCong(s: String) {
        val alertDialog=AlertDialog.Builder(this)
        alertDialog.setTitle("Thông báo")
        alertDialog.setMessage(s)
        alertDialog.setNegativeButton("OK",DialogInterface.OnClickListener { dialogInterface, i ->
            val intent=Intent(this@ActivityDangKy,ActivityDangNhap::class.java)
            startActivity(intent)
            finish()
        })
        alertDialog.setPositiveButton("Hủy",DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })
        alertDialog.show()
    }

    private fun thongBaoLoi(s: String) {
        val alertDialog= AlertDialog.Builder(this)
        alertDialog.setTitle("Thông báo")
        alertDialog.setMessage(s)
        alertDialog.setNegativeButton("Hủy", DialogInterface.OnClickListener { dialog, i ->
            dialog.cancel()
        })
        alertDialog.show()
    }

    private fun validate(): Int {
        var check=-1
        if(binding.edtHoTen.text.toString().isNotBlank()&&
                binding.edtSDT.text.toString().isNotBlank()&&
                binding.edtNgaySinh.text.toString().isNotBlank()&&
                binding.edtMatKhau.text.toString().isNotBlank())
            check=1
        return check
    }
}