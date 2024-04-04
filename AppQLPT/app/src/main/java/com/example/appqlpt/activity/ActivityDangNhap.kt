package com.example.appqlpt.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.appqlpt.MainActivity
import com.example.appqlpt.R
import com.example.appqlpt.database.ChuTroDao
import com.example.appqlpt.database.KhuTroDao
import com.example.appqlpt.databinding.ActivityDangNhapBinding
import com.example.appqlpt.model.KhuTro

const val THONG_TIN_DANG_NHAP="Thong_tin_dang_nhap"
const val USERNAME_KEY="USERNAME"
const val PASSWORD_KEY="PASSWORD"
const val CHECKBOX_KEY="REMEMBER"
class ActivityDangNhap : AppCompatActivity() {
    private lateinit var binding: ActivityDangNhapBinding
    private var listKhuTro= listOf<KhuTro>()
    private var admin=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDangNhapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val chuTroDao=ChuTroDao(applicationContext)
        val khuTroDao=KhuTroDao(applicationContext)
        val pref:SharedPreferences=getSharedPreferences(THONG_TIN_DANG_NHAP, MODE_PRIVATE)
        admin= pref.getString(USERNAME_KEY,"")!!
        binding.edtTenDN.setText(pref.getString(USERNAME_KEY,""))
        if(pref.getBoolean(CHECKBOX_KEY,false)){
            binding.edtMatKhau.setText(pref.getString(PASSWORD_KEY,""))
            binding.chkLuuTK.isChecked=pref.getBoolean(CHECKBOX_KEY,false)
        }
        else{
            binding.edtMatKhau.setText("")
            binding.chkLuuTK.isChecked=false
        }
        binding.btnDN.setOnClickListener{
            val userName=binding.edtTenDN.text.toString()
            val password=binding.edtMatKhau.text.toString()
            val check=binding.chkLuuTK
            if(admin!=null) admin=userName
            listKhuTro=khuTroDao.getAllInKhuTroByChuTro(admin)
            if(userName.isNotBlank()&&password.isNotBlank()){
                if(chuTroDao.checkLogin(userName,password)){
                    if(listKhuTro.isNotEmpty()){
                            val intent=Intent(this@ActivityDangNhap,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        rememberUser(userName,password,check.isChecked)
                    }
                    else{
                        val intent=Intent(this@ActivityDangNhap,ActivityHuongDanTaoKhu::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else{
                    thongBaoLoi("Tên tài khoản hoặc mật khẩu không đúng!")
                }
            }
            else{
                thongBaoLoi("Nhập đầy đủ thông tin!")
            }
        }
        binding.btnHuyDN.setOnClickListener {
            binding.edtTenDN.setText("")
            binding.edtMatKhau.setText("")
            binding.chkLuuTK.isChecked=false
        }
        binding.txtDangKy.setOnClickListener {
            val intent=Intent(this@ActivityDangNhap,ActivityDangKy::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun thongBaoLoi(s: String) {
        val alertDialog=AlertDialog.Builder(this)
        alertDialog.setTitle("Thông báo")
        alertDialog.setMessage(s)
        alertDialog.setNegativeButton("Hủy",DialogInterface.OnClickListener { dialog, i ->
            dialog.cancel()
        })
        alertDialog.show()
    }

    private fun rememberUser(u: String, p: String, check: Boolean) {
        val pref=getSharedPreferences(THONG_TIN_DANG_NHAP, MODE_PRIVATE)
        val edit=pref.edit()
        edit.putString(USERNAME_KEY,u)
        edit.putString(PASSWORD_KEY,p)
        edit.putBoolean(CHECKBOX_KEY,check)
        edit.commit()
    }
}