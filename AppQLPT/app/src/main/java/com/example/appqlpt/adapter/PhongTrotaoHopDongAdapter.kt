package com.example.appqlpt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlpt.database.*
import com.example.appqlpt.databinding.LayoutItemPhongBinding
import com.example.appqlpt.model.Phong
import kotlinx.coroutines.processNextEventInCurrentThread

class PhongTroTaoHopDongViewHolder(
    val binding:LayoutItemPhongBinding
):RecyclerView.ViewHolder(binding.root){
    fun bind(phong: Phong){
        binding.tvTenPhong.text=phong.ten_phong
        binding.tvGiaThue.text=phong.gia_thue.toString()
        binding.chkTrangThaiPhongDaCoc.isChecked= phong.trang_thai_phong==1
        binding.chkTrangThaiPhongTrong.isChecked=phong.trang_thai_phong==0
        if (phong.so_nguoi_o==0){
            binding.tvGioiHanNguoiO.text = "Tối đa: "+"Không giới hạn"
        }else{
            binding.tvGioiHanNguoiO.text = "Tối đa: "+phong.so_nguoi_o +" người"
        }
        binding.tvSoNguoiHienTai.text = "có "+ KhachThueDao(binding.root.context).getListKhachThueByMaPhong(phong.ma_phong).size +" người đang ở"
        binding.tvTenPhong.setOnClickListener {
            val phong=PhongDao(binding.root.context).getPhongById(phong.ma_phong)
            val listLoaiDichVu= phong?.let { it1 ->
                DichVuDao(binding.root.context).getAllInDichVuByPhong(
                    it1.ma_phong)
            }
            Toast.makeText(binding.root.context, listLoaiDichVu?.size.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
class PhongTrotaoHopDongAdapter(val listPhong: List<Phong>, val onClick:PhongInterface): RecyclerView.Adapter<PhongTroTaoHopDongViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhongTroTaoHopDongViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding = LayoutItemPhongBinding.inflate(inflater,parent,false)
        return  PhongTroTaoHopDongViewHolder(binding)
    }

    override fun getItemCount()=listPhong.size

    override fun onBindViewHolder(holder: PhongTroTaoHopDongViewHolder, position: Int) {
        val phong=listPhong[position]
        holder.apply {
            bind(phong)
        }
        holder.itemView.setOnClickListener {
             onClick.OnCLickPhong(position)
        }

    }
}
