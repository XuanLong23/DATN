package com.example.appqlpt.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlpt.activity.ActivityDangNhap
import com.example.appqlpt.activity.ActivityQuanLyDichVu
import com.example.appqlpt.activity.ActivitySuaDichVu
import com.example.appqlpt.database.DichVuDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.ItemQuanLyDichVuBinding
import com.example.appqlpt.model.DichVu
const val MA_DICH_VU_KEY="ma_phong_de_sua_dich_vu"
class QuanLyLoaiDichVuViewHolder(
    val binding:ItemQuanLyDichVuBinding
):RecyclerView.ViewHolder(binding.root)

class QuanLyDichVuAdapter(
    val list:List<DichVu>,
    val context:Context,
    val maKhu:String
): RecyclerView.Adapter<QuanLyLoaiDichVuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuanLyLoaiDichVuViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemQuanLyDichVuBinding.inflate(inflater, parent, false)
        return QuanLyLoaiDichVuViewHolder(binding)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: QuanLyLoaiDichVuViewHolder, position: Int) {
        val item=list[position]
        val phongSize=PhongDao(context).getAllInPhongByMaKhu(maKhu).size
        val dichVuTrongPhongSize= DichVuDao(context).getAllInDichVu().
        filter { it.ten_dich_vu==item.ten_dich_vu && it.ma_khu_tro==maKhu }.size
        val gia=when(item.trang_thai_dich_vu){
            0 -> "Miễn Phí"
            1 -> "${item.gia_dich_vu} Đồng/Số"
            2 -> "${item.gia_dich_vu} Đồng/người"
            3 -> "${item.gia_dich_vu} Đồng/Phòng"
            else -> {""}
        }
        holder.binding.tenLoaiDichVu.text=item.ten_dich_vu
        holder.binding.tvGiaDichVu.text=gia
        when{
            phongSize<=dichVuTrongPhongSize->{
                holder.binding.tvInfo.text="Áp dụng cho tất cả phòng"
                holder.binding.tvInfo.setTextColor(Color.parseColor("#ED061F"))
            }
            dichVuTrongPhongSize in 2 until phongSize ->{
                holder.binding.tvInfo.text="Áp dụng cho một số phòng"
                holder.binding.tvInfo.setTextColor(Color.parseColor("#FF5C00"))
            }
            else ->{
                holder.binding.tvInfo.text="Chưa áp dụng phòng nào"
                holder.binding.tvInfo.setTextColor(Color.BLACK)
            }
        }

        holder.binding.btnXoaDichVu.setOnClickListener {
            thongBaoXoa("Bạn chắc chắn muốn xóa: ${item.ten_dich_vu}", item)
        }
        holder.binding.cardDichVu.setOnClickListener {
            val intent=Intent(context, ActivitySuaDichVu::class.java)
            intent.putExtra(MA_DICH_VU_KEY, item.ma_dich_vu)
            context.startActivity(intent)
        }

    }
    fun thongBaoXoa(m : String, dichVu:DichVu){
        val bundle = AlertDialog.Builder(context)
        bundle.setTitle("Thông Báo")
        bundle.setMessage(m)
        bundle.setNegativeButton("OK") { dialog, which ->
            val loaiDichVuPhongDao=DichVuDao(context)
             loaiDichVuPhongDao.xoaDichVuByMaKhuVaTen(dichVu)
            val intent=Intent(context, ActivityQuanLyDichVu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)

        }
        bundle.setPositiveButton("Hủy") { dialog, which ->
            dialog.cancel()
        }
        bundle.show()
    }

}