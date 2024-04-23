package com.example.appqlpt.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Telephony
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.example.appqlpt.database.HoaDonDao
import com.example.appqlpt.database.KhachThueDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.DialogHoaDonChiTietBinding
import com.example.appqlpt.databinding.LayoutItemHoaDonBinding
import com.example.appqlpt.model.HoaDon
import java.text.SimpleDateFormat
import java.util.*

class HoaDonViewHolder(
    var binding: LayoutItemHoaDonBinding
):RecyclerView.ViewHolder(binding.root){
    fun bind(hoaDon: HoaDon){

        if (hoaDon.trang_thai_hoa_don==0 ){
            binding.tvTrangThaiHoaDon.isChecked = false
            val phong = PhongDao(binding.root.context).getPhongById(hoaDon.ma_phong)
            val list = phong?.ten_phong?.let {
                KhachThueDao(binding.root.context).getAllInKhachThueByTenPhong(
                    it
                )
            }
            val sdt_ND = list?.sdt_khach_thue
            val message_ND = "[Thông báo] Phòng ${phong?.ten_phong} hóa đơn tháng ${chuyenNgay(hoaDon.ngay_tao_hoa_don)} tổng tiền là ${hoaDon.tong}" +
                    ". Thanh toán trước 5 ngày theo quy định."

            binding.thongBaoHoaDon.setOnClickListener {
                //   Toast.makeText(binding.root.context,"Sdt ${sdt_ND} và ${message_ND}",Toast.LENGTH_SHORT).show()
                nhanTin1(sdt_ND.toString(),message_ND,binding.root.context)
            }
            binding.goiDienHoaDon.setOnClickListener {
                goiDien1(sdt_ND.toString(),binding.root.context)
            }
            binding.tvTrangThaiHoaDon.setOnClickListener {
                val hoaDon = HoaDon(
                    ma_phong = hoaDon.ma_phong,
                    ma_hoa_don = hoaDon.ma_hoa_don,
                    gia_thue = hoaDon.gia_thue,
                    mien_giam = hoaDon.mien_giam,
                    gia_dich_vu = hoaDon.gia_dich_vu,
                    trang_thai_hoa_don = 1,
                    ngay_tao_hoa_don = hoaDon.ngay_tao_hoa_don,
                    so_nuoc = hoaDon.so_nuoc,
                    so_dien = hoaDon.so_dien,
                    tong = hoaDon.tong
                )
                val builder = AlertDialog.Builder(binding.root.context)
                builder.setTitle("Thông báo thanh toán")
                builder.setMessage("Xác nhận thanh toán!")
                builder.setPositiveButton("Xác nhận", DialogInterface.OnClickListener { dialog, which ->
                    val dao = HoaDonDao(binding.root.context).update(hoaDon)
                    dialog.cancel()
                })
                builder.setNegativeButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    binding.tvTrangThaiHoaDon.isChecked = false
                })

                builder.show()
            }
            binding.tvTenPhong.text = phong?.ten_phong

            binding.tvTong.text = dinhDangTien(hoaDon.tong.toString())
            binding.tvConLai.text = dinhDangTien(hoaDon.tong.toString())

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


            binding.layoutChuyenChiTietHoaDon.setOnClickListener {
                val bottomSheetDialog = BottomSheetDialog(binding.root.context)
                val dialog = DialogHoaDonChiTietBinding.inflate(LayoutInflater.from(binding.root.context))
                bottomSheetDialog.setContentView(dialog.root)


                dialog.tvTenPhong.text = phong?.ten_phong
                dialog.tvNgay.text = chuyenNgay1(hoaDon.ngay_tao_hoa_don)
                dialog.tvTienPhong.text =   "${dinhDangTien(hoaDon.gia_thue.toString())} Vnd"
                dialog.tvGiaDichVu.text = "${dinhDangTien(hoaDon.gia_dich_vu.toString())} Vnd"
                dialog.tvSoDien.text =  "${dinhDangTien(hoaDon.so_dien.toString())} Số"
                dialog.tvKhoiNuoc.text =   "${hoaDon.so_nuoc.toString()} Khối"
                dialog.tvTienMienGiam.text =  "${hoaDon.mien_giam.toString()} Vnd"
                dialog.tvNgayHoaDon.text = "Hóa đơn tháng "+ chuyenNgay1(hoaDon.ngay_tao_hoa_don)
                dialog.chkThanhToan.isChecked = false


                dialog.tvTongTien.text = dinhDangTien(hoaDon.tong.toString())
                dialog.btnDong.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                bottomSheetDialog.show()
            }
        }
        else{
            binding.linerLayoutItemHD.isVisible  = false
        }
    }
}
fun dinhDangTien1(gia : String): String{
    val tienFormat = String.format("%,d",gia.toLong()).replace(",",".")
    return tienFormat
}
fun nhanTin1(sdt:String, message:String, context: Context){

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
fun goiDien1(sdt:String, context: Context){
    val dialIntent = Intent(Intent.ACTION_DIAL)
    dialIntent.data = Uri.parse("tel:$sdt")
    context.startActivity(dialIntent)
}
fun chuyenNgay1(ngay : String ):String{
    val sdfNgay = SimpleDateFormat("yyyy-MM-dd")
    val objDate = sdfNgay.parse(ngay)
    val ngay =  DateFormat.format("MM-yyyy",objDate) as String
    return ngay
}
class HoaDonAdapter(val list: List<HoaDon>): RecyclerView.Adapter<HoaDonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoaDonViewHolder {
        val infa = LayoutInflater.from(parent.context)
        val binding = LayoutItemHoaDonBinding.inflate(infa,parent,false)
        return HoaDonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HoaDonViewHolder, position: Int) {
        val hoaDon = list[position]
        holder.apply {
            bind(hoaDon)
        }
    }

    override fun getItemCount()= list.size

}