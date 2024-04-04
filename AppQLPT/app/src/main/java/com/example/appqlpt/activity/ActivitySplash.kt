package com.example.appqlpt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.appqlpt.MainActivity
import com.example.appqlpt.R

class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val intent= Intent(this,ActivityDangNhap::class.java)
            startActivity(intent)
            finishAffinity()
        },2000)
    }
}