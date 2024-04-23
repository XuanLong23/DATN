package com.example.appqlpt.activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.example.appqlpt.R
import com.example.appqlpt.adapter.ViewpagerDanhSachHoaDonAdapter
import com.example.appqlpt.databinding.ActivityDanhSachHoaDonBinding

class ActivityDanhSachHoaDon : AppCompatActivity() {
    private lateinit var binding: ActivityDanhSachHoaDonBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDanhSachHoaDonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tbDanhSachHoaDon

        setSupportActionBar(binding.tbDanhSachHoaDon)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)





        val adapter = ViewpagerDanhSachHoaDonAdapter(supportFragmentManager, lifecycle)
        binding.viewpagerHoaDon.adapter = adapter
        TabLayoutMediator(binding.tabDanhSachHoaDon, binding.viewpagerHoaDon) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.text = "Đã thanh toán"
                }
                1 -> {
                    tab.text = "Chưa thanh toán"
                }
                else -> tab.text = "Đã thanh toán"
            }
        }.attach()
    }

    fun chuyenActivity(){
        val intent = Intent(this@ActivityDanhSachHoaDon, ActivityManHinhChinhChuTro::class.java)
        startActivity(intent)
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.getItemId();
        if (id==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }


}