package com.example.appqlpt.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.KhuTroAdapter
import com.example.appqlpt.adapter.ViewPagerManHinhChinhAdapter
import com.example.appqlpt.database.HopDongDao
import com.example.appqlpt.database.KhuTroDao
import com.example.appqlpt.databinding.ActivityManHinhChinhChuTroBinding
import com.example.appqlpt.databinding.DialogDanhSachKhuTroBinding
import com.example.appqlpt.model.KhuTro
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*

class ActivityManHinhChinhChuTro : AppCompatActivity() {
    private lateinit var binding:ActivityManHinhChinhChuTroBinding
    private var listKhuTro= listOf<KhuTro>()
    private var maKhu=""
    private lateinit var bottomSheetDialog: BottomSheetDialog
    val sdf= SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityManHinhChinhChuTroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomSheetDialog= BottomSheetDialog(this)
        val admin=getSharedPreferences(THONG_TIN_DANG_NHAP, MODE_PRIVATE).getString(USERNAME_KEY,"")!!
        listKhuTro=KhuTroDao(applicationContext).getAllInKhuTroByChuTro(admin)

        val pre = getSharedPreferences(FILE_NAME, MODE_PRIVATE)

        if(listKhuTro.isEmpty()){
            val intent=Intent(this@ActivityManHinhChinhChuTro,ActivityHuongDanTaoKhu::class.java)
            startActivity(intent)
            finish()
        }
        when{
            intent.getStringExtra(MA_KHU_KEY)==null ->{
                if(listKhuTro.isNotEmpty()){
                    maKhu=listKhuTro[0].ma_khu_tro
                }
            }
            intent.getStringExtra(MA_KHU_KEY)!=null ->{
                maKhu=intent.getStringExtra(MA_KHU_KEY)!!
            }
        }

        val khuTro=listKhuTro.find { it.ma_khu_tro==maKhu }
        binding.tvTenKhu.text=khuTro?.ten_khu_tro
        pre.edit().putString(MA_KHU_KEY, maKhu).commit()
        val today= Date()
        val date=sdf.format(today)
        val listHopDong= HopDongDao(applicationContext).getAllInHopDongByMaKhu(maKhu, 1)
        val findHopDong=listHopDong.any { hopDong->
            hopDong.trang_thai_hop_dong==2
        }
        if(findHopDong){
            createNotifcationChannel("channel1","thong bao hop dong", "hop dong"  )
            //thongBao(this@ActivityManHinhChinhChuTro,"channel1","Hết hạn hơp đồng", "Có hợp đồng sắp hết hạn, xin vào app để xem", date, 1)
        }

        binding.menuManHinhChinh.setOnClickListener {
            val build=DialogDanhSachKhuTroBinding.inflate(LayoutInflater.from(this))
            val adapter=KhuTroAdapter(listKhuTro)
            build.rcyKhuTro.layoutManager=LinearLayoutManager(applicationContext)
            build.rcyKhuTro.adapter=adapter

            bottomSheetDialog.setContentView(build.root)
            build.icClose.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            build.btnThemKhuTro.setOnClickListener {
                val intent=Intent(this,ActivityThemKhuTro::class.java)
                startActivity(intent)
                finish()
            }
            bottomSheetDialog.show()

        }


        val adapter=ViewPagerManHinhChinhAdapter(supportFragmentManager,lifecycle)
        binding.viewPager2ManHinhChinh.adapter=adapter
        TabLayoutMediator(binding.tabLayoutManHinhChinh,binding.viewPager2ManHinhChinh){tab,pos->
            when(pos){
                0->{
                    tab.setIcon(R.drawable.home_icon)
                    tab.setText("Trang chủ")
                }
                1->{
                    tab.setIcon(R.drawable.dangtin_icon)
                    tab.setText("Đăng tin")
                }
                2->{
                    tab.setIcon(R.drawable.thongbao_icon)
                    tab.setText("Thông báo")
                }
                3->{
                    tab.setIcon(R.drawable.canhan_icon)
                    tab.setText("Cá nhân")
                }
                else->tab.setText("Trang chủ")
            }
        }.attach()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotifcationChannel(channel: String, name:String, desc:String) {
        val importance= NotificationManager.IMPORTANCE_DEFAULT
        val channel= NotificationChannel(channel, name, importance)
        channel.description=desc
        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun thongBao(context: Context, channel: String, tieuDe:String, noiDung:String, date:String, loai:Int){
//        val intent=Intent(context, Notification::class.java)
//        with(intent){
//            putExtra(TITLE_EXTRAS, tieuDe)
//            putExtra(MESS_EXTRAS, noiDung)
//            putExtra(LOAI_THONG_BAO, loai)
//            putExtra(MA_KHU, maKhu)
//            putExtra(DATE,date )
//            putExtra(CHANNEL_ID, channel)
//        }
//        val pendingIntent= PendingIntent.getBroadcast(applicationContext,
//            notificationID, intent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
//        val alarmManager=getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val  localDateTime=LocalDateTime.parse(date,
//            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
//        val time=localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
////        val time= Date().time+10000L
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            time, pendingIntent)
//    }
}