package com.example.appqlpt.adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlpt.activity.ActivityTaoHoaDon
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.LayoutItemPhongDangOBinding

import com.example.appqlpt.model.Phong

const val MA_PHONG_HOA_DON_KEY="ma_phong_de_lay_hoa_don"

class DanhSachPhongDaOViewHolder(
    val binding: LayoutItemPhongDangOBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(phong: Phong, context: Context){

          //  val maPhong = PhongDao(binding.root.context).getPhongById(phong.ma_phong)
            binding.tvTenPhong.text = phong.ten_phong
            binding.tvGiaThue.text = dinhDangTienPhongDaO(phong.gia_thue.toString())
            binding.chkTrangThaiPhongTrong.isChecked = true
            binding.chkTrangThaiPhongTrong.isClickable = false
            binding.linnerLayoutItemPhong.setOnClickListener {
                manHinhHoaDon(context,phong.ma_phong)
            }

    }
}
fun manHinhHoaDon(context: Context, id:String){
    val intent = Intent(context,ActivityTaoHoaDon::class.java)
    intent.apply {
        putExtra(MA_PHONG_HOA_DON_KEY, id)
    }
    context.startActivity(intent)
}
fun dinhDangTienPhongDaO(gia : String): String{
    val tienFormat = String.format("%,d",gia.toLong()).replace(",",".")
    return tienFormat
}
class DanhSachPhongDaOAdapter(
    val list:List<Phong>,
   val context: Context
):RecyclerView.Adapter<DanhSachPhongDaOViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DanhSachPhongDaOViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemPhongDangOBinding.inflate(inflater,parent,false)
        return DanhSachPhongDaOViewHolder(binding)
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: DanhSachPhongDaOViewHolder, position: Int) {
        val user = list[position]
        holder.bind(user, context)
    }
}
