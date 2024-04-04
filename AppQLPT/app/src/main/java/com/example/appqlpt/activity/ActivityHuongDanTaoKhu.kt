package com.example.appqlpt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appqlpt.databinding.ActivityHuongDanTaoKhuBinding

class ActivityHuongDanTaoKhu : AppCompatActivity() {
    private lateinit var binding:ActivityHuongDanTaoKhuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHuongDanTaoKhuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnTaoKhu.setOnClickListener {
            val intent=Intent(this@ActivityHuongDanTaoKhu,ActivityThemKhuTro::class.java)
            startActivity(intent)
            finish()
        }
    }
}