package com.example.appqlpt.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Telephony
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlpt.database.KhachThueDao
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.DialogHoaDonChiTietBinding
import com.example.appqlpt.databinding.LayoutItemHoaDonBinding
import com.example.appqlpt.model.HoaDon
import com.example.appqlpt.model.Phong
import java.text.SimpleDateFormat
import java.util.*

class HoaDonDaThanhToanViewHolder(
    var binding: LayoutItemHoaDonBinding

):RecyclerView.ViewHolder(binding.root){
    fun bind(hoaDon: HoaDon){


        if (hoaDon.trang_thai_hoa_don==1 ){
            binding.tvTrangThaiHoaDon.isChecked = true
            binding.tvTrangThaiHoaDon.isClickable = false
            val phong = PhongDao(binding.root.context).getPhongById(hoaDon.ma_phong)
            binding.tvTenPhong.text = phong?.ten_phong
            // tính tổng
            val chuHopDong= phong?.let { phong ->
                KhachThueDao(binding.root.context).getListKhachThueByMaPhong(phong.ma_phong).find { it.trang_thai_chu_hop_dong==1 }
            }

            binding.tvTong.text = dinhDangTien(hoaDon.tong.toString())
            binding.tvTong.setTextColor(Color.argb(200,0,200,0))
            binding.tvDaThu.text =  dinhDangTien(hoaDon.tong.toString())
            binding.tvDaThu.setTextColor(Color.argb(200,0,200,0))

            val dateFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val newDate = dateFormat.parse(hoaDon.ngay_tao_hoa_don)
            val calendar = Calendar.getInstance()
            if (newDate != null) {
                calendar.time = newDate
            }
            val month = calendar.get(Calendar.MONTH)+1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val year = calendar.get(Calendar.YEAR)

            binding.tvThang.setText("T${month.toString()}")
            binding.tvNam.setText(year.toString())
            binding.textViewHoaDOn.text = "Thu tiền tháng ${month.toString()}"


            if (chuHopDong!=null) {
                val sdt_ND = chuHopDong.sdt_khach_thue
                val message_ND =
                    "Thông báo hóa đơn tháng ${chuyenNgay(hoaDon.ngay_tao_hoa_don)} tổng tiền là ${hoaDon.tong}"
                binding.thongBaoHoaDon.setOnClickListener {
                    //   Toast.makeText(binding.root.context,"Sdt ${sdt_ND} và ${message_ND}",Toast.LENGTH_SHORT).show()

                    nhanTinHD(sdt_ND.toString(), message_ND, binding.root.context)
                }
                binding.goiDienHoaDon.setOnClickListener {
                    goiDienHD(sdt_ND, binding.root.context)
                }
            }


            binding.layoutChuyenChiTietHoaDon.setOnClickListener {
                val bottomSheetDialog = BottomSheetDialog(binding.root.context)
                val dialog = DialogHoaDonChiTietBinding.inflate(LayoutInflater.from(binding.root.context))
                bottomSheetDialog.setContentView(dialog.root)
//                Toast.makeText(binding.root.context,sdt_ND.toString(),Toast.LENGTH_SHORT).show()

                dialog.tvTenPhong.text = phong?.ten_phong
                dialog.tvNgay.text = chuyenNgay(hoaDon.ngay_tao_hoa_don)
                dialog.tvTienPhong.text =   "${dinhDangTien(hoaDon.gia_thue.toString())} Vnd"
                dialog.tvGiaDichVu.text = "${dinhDangTien(hoaDon.gia_dich_vu.toString())} Vnd"
                dialog.tvSoDien.text =  "${dinhDangTien(hoaDon.so_dien.toString())} Số"
                dialog.tvKhoiNuoc.text =   "${hoaDon.so_nuoc.toString()} Khối"
                dialog.tvTienMienGiam.text =  "${hoaDon.mien_giam.toString()} Vnd"
                dialog.tvNgayHoaDon.text = "Hóa đơn tháng "+ chuyenNgay(hoaDon.ngay_tao_hoa_don)
                dialog.chkThanhToan.isChecked = true


                dialog.tvTongTien.text = dinhDangTien(hoaDon.tong.toString())
                dialog.tvTongTien.setTextColor(Color.argb(200,0,200,0))
                dialog.btnDong.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                bottomSheetDialog.show()
            }
        }
        else{
            binding.linerLayoutItemHD.isVisible  = false
        }
        binding.lnThongBaoHoaDon.isVisible = false
    }
}
fun chuyenNgay(ngay : String ):String{
    val sdfNgay = SimpleDateFormat("yyyy-MM-dd")
    val objDate = sdfNgay.parse(ngay)
    val ngay =  DateFormat.format("MM-yyyy",objDate) as String
    return ngay
}
fun dinhDangTien(gia : String): String{
    val tienFormat = String.format("%,d",gia.toLong()).replace(",",".")
    return tienFormat
}

fun nhanTinHD(sdt:String, message:String, context: Context){

    val uri = Uri.parse("smsto:+$sdt")
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    with(intent) {
        putExtra("address", "+$sdt")
        putExtra("sms_body", message)
    }
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
            val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context)
            if (defaultSmsPackageName != null) intent.setPackage(defaultSmsPackageName)
            context.startActivity(intent)
        }
        else -> context.startActivity(intent)
    }
}
fun goiDienHD(sdt:String, context:Context){

    val dialIntent = Intent(Intent.ACTION_DIAL)
    dialIntent.data = Uri.parse("tel:$sdt")
    context.startActivity(dialIntent)
}

class HoaDonDaThanhToanAdapter(val list: List<HoaDon>): RecyclerView.Adapter<HoaDonDaThanhToanViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoaDonDaThanhToanViewHolder {
        val infa = LayoutInflater.from(parent.context)
        val binding = LayoutItemHoaDonBinding.inflate(infa,parent,false)
        return HoaDonDaThanhToanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HoaDonDaThanhToanViewHolder, position: Int) {
        val hoaDon = list[position]
        holder.apply {
            bind(hoaDon)
        }
    }

    override fun getItemCount()= list.size

}