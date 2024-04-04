package com.example.appqlpt.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.appqlpt.R
import com.example.appqlpt.database.KhuTroDao
import com.example.appqlpt.databinding.ActivityThemKhuTroBinding
import com.example.appqlpt.model.KhuTro
import java.util.UUID

const val SO_LUONG_PHONG_KEY="so_luong_phong"
const val MA_KHU_KEY="ma_khu_khi_tao_khu"
class ActivityThemKhuTro : AppCompatActivity() {
    private lateinit var binding:ActivityThemKhuTroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityThemKhuTroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbThemKhuTro)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_left)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pref=getSharedPreferences(THONG_TIN_DANG_NHAP, MODE_PRIVATE)
        val admin=pref.getString(USERNAME_KEY,"")!!

        binding.chkTuDongTaoPhong.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                binding.tvSoPhong.visibility= View.VISIBLE
                binding.edtSoPhong.visibility=View.VISIBLE
            }
            else{
                binding.tvSoPhong.visibility= View.GONE
                binding.edtSoPhong.visibility=View.GONE
                binding.edtSoPhong.setText("")
            }
        }

        binding.btnTiepTuc.setOnClickListener { 
            if(!validate()){
                thongBaoLoi("Nhập đầy đủ thông tin!")
            }
            else{
                val id=UUID.randomUUID().toString()
                val khuTro = KhuTro(
                    ma_khu_tro = id,
                    ten_khu_tro = binding.edtTenKhuTro.text.toString(),
                    so_luong_phong = binding.edtSoPhong.text.toString().toInt(),
                    dia_chi = binding.edtDiaChi.text.toString(),
                    ten_dang_nhap = admin
                )
                val dao=KhuTroDao(this@ActivityThemKhuTro).insertKhuTro(khuTro)
                if(dao>0){
                    val intent=Intent(this@ActivityThemKhuTro,ActivityTaoPhongKhiThemKhu::class.java)
                    intent.putExtra(SO_LUONG_PHONG_KEY,binding.edtSoPhong.text.toString().toInt())
                    intent.putExtra(MA_KHU_KEY,id)
                    startActivity(intent)
                    finishAffinity()
                }
                else{
                    thongBaoLoi("Tạo khu thất bại!")
                }
            }
        }

    }

    private fun thongBaoLoi(s: String) {
        val alertDialog=AlertDialog.Builder(this)
        alertDialog.setTitle("Thông báo")
        alertDialog.setMessage(s)
        alertDialog.setNegativeButton("Hủy",DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })
    }

    private fun validate(): Boolean {
        return (
                    binding.edtTenKhuTro.text.toString().isNotBlank()&&
                    binding.edtSoPhong.text.toString().isNotBlank()&&
                    binding.edtDiaChi.text.toString().isNotBlank()
                )
    }
}