package com.example.appqlpt.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlpt.activity.ActivityDanhSachHopDong
import com.example.appqlpt.database.HopDongDao
import com.example.appqlpt.databinding.DialogChiTietHopDongBinding
import com.example.appqlpt.databinding.LayoutItemDsHopDongBinding
import com.example.appqlpt.fragment.FragmentQuanLy
import com.example.appqlpt.model.HopDong
import java.text.SimpleDateFormat
import java.util.*


class HopDongPhongSapHetHanTongQuanViewHolder(
    val binding: LayoutItemDsHopDongBinding

): RecyclerView.ViewHolder(binding.root){


    fun bind(hopDong: HopDong){


        binding.tvDanhSachHopDongTenPhong.text=HopDongDao(binding.root.context).getTenPhongByIDHopDong(hopDong.ma_hop_dong)
        binding.tvDanhSachHopDongTenThanhVien.text= "Họ và tên: "+ HopDongDao(binding.root.context).getTenKhachThueByIDHopDong(hopDong.ma_hop_dong)
        if (hopDong.trang_thai_hop_dong == 0){
            binding.tvDanhSachHopDongTrangThai.text = "Tình trạng hợp đồng: Hết hợp đồng"
            binding.tvDanhSachHopDongTrangThai.setTextColor(Color.RED)
        }else if (hopDong.trang_thai_hop_dong == 1){
            binding.tvDanhSachHopDongTrangThai.text = "Tình trạng hợp đồng: Còn hợp đồng"
            binding.tvDanhSachHopDongTrangThai.setTextColor(Color.BLACK)
        }else{
            binding.tvDanhSachHopDongTrangThai.text = "Tình trạng hợp đồng: Sắp hết hạn"
            binding.tvDanhSachHopDongTrangThai.setTextColor(Color.BLUE)
        }
        binding.tvDanhSachHopDongNgayO.text = chuyenDinhDangNgay(hopDong.ngay_o)
        binding.tvDanhSachHopDongNgayKetThuc.text = chuyenDinhDangNgay(hopDong.ngay_hop_dong)
        binding.tvDanhSachHopDongNgayLap.text = chuyenDinhDangNgay(hopDong.ngay_lap_hop_dong)
        binding.tvDanhSachHopDongTrangThai.setOnClickListener {
            val ngayHetHan = hopDong.ngay_hop_dong
            val dateFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val newDate = dateFormat.parse(ngayHetHan)
            val calendar = Calendar.getInstance()
            if (newDate != null) {
                calendar.time = newDate
            }
            val monthNgayHetHan = calendar.get(Calendar.MONTH)
            val dayNgayHetHan = calendar.get(Calendar.DAY_OF_MONTH) - 7
            val yearNgayHetHan = calendar.get(Calendar.YEAR)
            val cNgayHetHanHopDong = GregorianCalendar(yearNgayHetHan, monthNgayHetHan, dayNgayHetHan)
            //============================
            val simpleDateFormatNow = SimpleDateFormat("yyyy-MM-dd")
            var mYearNow = 0
            var mMonthNow = 0
            var mDayNow = 0
            val c = Calendar.getInstance() as GregorianCalendar?
            mYearNow = (c as Calendar).get(Calendar.YEAR)
            mMonthNow = c!!.get(Calendar.MONTH)
            mDayNow = c!!.get(Calendar.DAY_OF_MONTH)
            val cNow = GregorianCalendar(mYearNow, mMonthNow, mDayNow)
            Log.d("TAG", "updateHopDong: "+ (dateFormat.format(cNgayHetHanHopDong!!.time)))

        }

        binding.tvChiTietDanhSachHD.setOnClickListener {
              val build = AlertDialog.Builder(binding.root.context).create()
              val dialog = DialogChiTietHopDongBinding.inflate(LayoutInflater.from(binding.root.context))
            build.setView(dialog.root)
            dialog.tvChiTietHDTenPhong.setText("Phòng:"+ HopDongDao(binding.root.context).getTenPhongByIDHopDong(hopDong.ma_hop_dong))
            dialog.tvChiTietHDTenNguoiDung.setText("Họ và tên:"+ HopDongDao(binding.root.context).getTenKhachThueByIDHopDong(hopDong.ma_hop_dong))
            dialog.tvChiTietHDThoiHan.setText("Thời hạn: "+ hopDong.thoi_han + " tháng")
            dialog.tvChiTietHDNgayO.setText("Ngày ở: "+ chuyenDinhDangNgay(hopDong.ngay_o))
            dialog.tvChiTietHDNgayHopDong.setText("Ngày kết thúc: "+ chuyenDinhDangNgay(hopDong.ngay_hop_dong))
            dialog.tvNgayLapHopDong.setText("Ngày lập hợp đồng: "+ chuyenDinhDangNgay(hopDong.ngay_lap_hop_dong))
            dialog.tvChiTietHDTienCoc.setText("Tiền cọc: "+ hopDong.tien_coc)
            if (hopDong.trang_thai_hop_dong==1){
                dialog.tvChiTietHDTrangThai.setText("Tình trạng hợp đồng: Còn hợp đồng")
            }else if(hopDong.trang_thai_hop_dong==0){
                dialog.tvChiTietHDTrangThai.setText("Tình trạng hợp đồng: Hết hợp đồng")
            }else{
                dialog.tvChiTietHDTrangThai.setText("Tình trạng hợp đồng: Sắp hết hợp đồng")
            }
            Toast.makeText(dialog.root.context,""+hopDong.trang_thai_hop_dong,Toast.LENGTH_SHORT).show()

            dialog.btnDongChiTietHopDong.setOnClickListener {

                build.dismiss()
            }
            build.show()
        }

    }


    private fun chuyenDinhDangNgay(ngay: String):String {
        val sdfNgay = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatNgayO = DateFormat()
        val objDateNgayO = sdfNgay.parse(ngay)
        val ngay = DateFormat.format("dd/MM/yyyy", objDateNgayO) as String
        return ngay
    }
}
class HopDongPhongSapHetHanTongQuanAdapter (val listHopDong: List<HopDong>): RecyclerView.Adapter<HopDongPhongSapHetHanTongQuanViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HopDongPhongSapHetHanTongQuanViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding = LayoutItemDsHopDongBinding.inflate(inflater,parent,false)

        return  HopDongPhongSapHetHanTongQuanViewHolder(binding)
    }
    override fun getItemCount()=listHopDong.size
    override fun onBindViewHolder(holder: HopDongPhongSapHetHanTongQuanViewHolder, position: Int) {
        val hopDong = listHopDong[position]

        holder.apply {

            bind(hopDong)
            //updateHopDong(hopDong)



            //fragment.updateDSHopDong(hopDong)



        }

//        holder.binding.tvDanhSachHopDongTrangThai.setOnClickListener {
//            activity.updateHopDong(hopDong)
//        }
    }
}
