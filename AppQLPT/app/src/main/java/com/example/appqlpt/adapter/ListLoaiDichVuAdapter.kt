package com.example.appqlpt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Toast
import com.example.appqlpt.database.DichVuDao
import com.example.appqlpt.databinding.ItemListLoaiDichVuBinding.*
import com.example.appqlpt.model.DichVu
import java.util.UUID

val listChon= mutableListOf<Any?>()
var list= listOf<DichVu>()
var item1:Any?=null
class ListLoaiDichVuAdapter(
    private val list:List<DichVu>,
    val context:Context,
    val maPhong:String
    ): BaseAdapter()
{
    override fun getCount(): Int=list.size

    override fun getItem(p0: Int): Any =list[p0]
    override fun getItemId(p0: Int)=p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val loaiDichVuPhongDao= DichVuDao(context)
        val checkList=loaiDichVuPhongDao.getAllInDichVuByPhong(maPhong)
        var item=list[p0]
        val inflater= LayoutInflater.from(p2?.context)
        val binding= inflate(inflater, p2, false)
        val gia=when(item.trang_thai_dich_vu){
            0 -> "Miễn Phí"
            1 -> "${item.gia_dich_vu} Đồng/Số"
            2 -> "${item.gia_dich_vu} Đồng/người"
            3 -> "${item.gia_dich_vu} Đồng/Phòng"
            else -> {""}
        }
        binding.tenLoaiDichVu.text=item.ten_dich_vu
        binding.tvGiaDichVu.text=gia
        if (item.ten_dich_vu in checkList.map { it.ten_dich_vu }){
            binding.swChonLoaiDichVu.isChecked=true
        }
        binding.swChonLoaiDichVu.setOnCheckedChangeListener { _, b ->
            item1=item.copy(ma_phong =maPhong, ma_dich_vu = UUID.randomUUID().toString())
            if(b)
                 loaiDichVuPhongDao.insertDichVu(item1 as DichVu)
            else
                loaiDichVuPhongDao.xoaDichVuByTenVaMaPhong(item1 as DichVu)
        }

        return binding.root
    }
}