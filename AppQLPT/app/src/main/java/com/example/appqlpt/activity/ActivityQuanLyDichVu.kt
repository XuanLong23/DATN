package com.example.appqlpt.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.QuanLyDichVuAdapter
import com.example.appqlpt.database.DichVuDao
import com.example.appqlpt.databinding.ActivityQuanLyDichVuBinding


private var maKhu=""
class ActivityQuanLyDichVu : AppCompatActivity() {
    private val listLoaiThanhToan= listOf(
        Pair("Miễn phí/Không sử dung",0), Pair("Tính theo đầu người",2), Pair("Tính theo phòng",3),
        Pair("Tính theo đồng hồ(Phổ biến)", 1, )
    )
    private lateinit var binding: ActivityQuanLyDichVuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuanLyDichVuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbQuanLyDichVu)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)
        val srf = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        val loaiDichVuPhongDao = DichVuDao(this)
        val listDichVu = loaiDichVuPhongDao.getAllInDichVuByKhuTro(maKhu)
        val adapter = QuanLyDichVuAdapter(listDichVu, this@ActivityQuanLyDichVu, maKhu)
        binding.rcvListDichVu.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(this@ActivityQuanLyDichVu)
        }
        binding.imgThemDichVu.setOnClickListener {
            val intent= Intent(this@ActivityQuanLyDichVu, ActivityThemDichVu::class.java)
            startActivity(intent)

        }
    }
    override fun  onOptionsItemSelected(item : MenuItem): Boolean {
        val id : Int = item.itemId;
        if (id==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "onResume: called ")
        val loaiDichVuPhongDao = DichVuDao(this)
        val listDichVu = loaiDichVuPhongDao.getAllInDichVuByKhuTro(maKhu)
        val adapter = QuanLyDichVuAdapter(listDichVu, this@ActivityQuanLyDichVu, maKhu)
        binding.rcvListDichVu.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(this@ActivityQuanLyDichVu)
        }
    }

}