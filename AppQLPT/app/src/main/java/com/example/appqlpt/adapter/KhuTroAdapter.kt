package com.example.appqlpt.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlpt.activity.ActivityManHinhChinhChuTro
import com.example.appqlpt.database.KhuTroDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.LayoutItemKhuTroBinding
import com.example.appqlpt.model.KhuTro
import com.example.appqlpt.model.Phong

const val MA_KHU_KEY="ma_khu"
const val TEN_KHU_KEY="ten_khu"
const val FILE_NAME="USER_FILE"
class KhuTroViewHolder(val binding:LayoutItemKhuTroBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(khuTro: KhuTro){
        val pre=binding.root.context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
        val maKhu=pre.getString(MA_KHU_KEY,"")
        var listPhongTrong:List<Phong> = listOf<Phong>()
        var listPhong= listOf<Phong>()
        listPhong=PhongDao(binding.root.context).getAllInPhongByKhuTro(khuTro.ten_khu_tro)
        listPhongTrong=PhongDao(binding.root.context).getAllInPhongByKhuTro(khuTro.ten_khu_tro).filter { it.trang_thai_phong==0 }
        binding.tvTenKhuTro.text="Khu: "+khuTro.ten_khu_tro
        binding.tvSoPhongTro.text="Tổng số phòng: "+listPhong.size
        binding.tvSoPhongKhuTroConTrong.text=""+listPhongTrong.size+" phòng trống"
        binding.tvDiaChiKhuTro.text="Địa chỉ: "+khuTro.dia_chi
        binding.btnQuanLyKhuTro.setOnClickListener {
            val intent= Intent(binding.root.context,ActivityManHinhChinhChuTro::class.java)
            intent.putExtra(MA_KHU_KEY,khuTro.ma_khu_tro)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags=Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            binding.root.context.startActivity(intent)
        }
        if(maKhu==khuTro.ma_khu_tro){
            binding.chkTrangThaiKhuTro.isVisible=true
            binding.chkTrangThaiKhuTro.isChecked=true
        }
        binding.btnXoaKhuTro.setOnClickListener {
            val dao=KhuTroDao(binding.root.context)
            if(dao.deleteKhuTro(khuTro)>0){
                thongBaoThanhCong("Bạn đã xóa thành công!")
            }
            else{
                thongBaoLoi("Bạn xóa không thành công!")
            }
        }
    }

    private fun thongBaoLoi(s: String) {
        val bundle=AlertDialog.Builder(binding.root.context)
        bundle.setTitle("Thông báo")
        bundle.setMessage(s)
        bundle.setNegativeButton("Hủy",DialogInterface.OnClickListener { dialogInterface, which ->
            dialogInterface.cancel()
        })
        bundle.show()
    }

    private fun thongBaoThanhCong(s: String) {
        val bundle=AlertDialog.Builder(binding.root.context)
        bundle.setTitle("Thông báo")
        bundle.setMessage(s)
        bundle.setNegativeButton("OK",DialogInterface.OnClickListener { dialogInterface, which ->
            val intent=Intent(binding.root.context,ActivityManHinhChinhChuTro::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            binding.root.context.startActivity(intent)
        })
        bundle.show()
    }
}
class KhuTroAdapter (val list:List<KhuTro>):RecyclerView.Adapter<KhuTroViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KhuTroViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=LayoutItemKhuTroBinding.inflate(inflater,parent,false)
        return KhuTroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KhuTroViewHolder, position: Int) {
        val khuTro=list[position]
        holder.bind(khuTro)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}