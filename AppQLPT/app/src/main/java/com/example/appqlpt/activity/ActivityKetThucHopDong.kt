package com.example.appqlpt.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.appqlpt.R
import com.example.appqlpt.database.*
import com.example.appqlpt.databinding.ActivityKetThucHopDongBinding
import com.example.appqlpt.model.HopDong
import com.example.appqlpt.model.KhachThue
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ActivityKetThucHopDong : AppCompatActivity() {
    private lateinit var binding: ActivityKetThucHopDongBinding
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val simpleDateFormatNow = SimpleDateFormat("yyyy-MM-dd")
    var mYearNow = 0
    var mDayNow = 0
    var mMonthNow = 0
    var tienDenBuHopDong = 100
    var tienCocDenBu = 0
    var tongTien = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKetThucHopDongBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.tbKetThucHopDong)
        val ab = getSupportActionBar()
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)


        val c = Calendar.getInstance() as GregorianCalendar?
        mYearNow = (c as Calendar).get(Calendar.YEAR)
        mMonthNow = c!!.get(Calendar.MONTH)
        mDayNow = c!!.get(Calendar.DAY_OF_MONTH)
        val cNow = GregorianCalendar(mYearNow, mMonthNow, mDayNow)
        val intent = intent
        val hopDong : HopDong = intent.getSerializableExtra("hopDong") as HopDong

        val tenPhong = PhongDao(this@ActivityKetThucHopDong).getTenPhongById(hopDong.ma_phong)
        binding.tvTenPhongXuLyPhong.setText(""+tenPhong)
        val tenKhu = KhuTroDao(this@ActivityKetThucHopDong).getTenKhuTro()
        binding.tvTenKhuXuLyPhong.setText("Khu: "+tenKhu)
        binding.tvSoTienCocXuLyPhong.setText(""+hopDong.tien_coc)
        binding.chkThanhToanXuLy.isEnabled = false
        binding.chkKiemTraXuLyPhong.isEnabled = false
        if (hopDong.trang_thai_hop_dong==0){
            binding.tvThoiHanXuLyHopDong.setText("Hết hạn")
            binding.tvThoiHanXuLyHopDong.setTextColor(Color.RED)
            binding.tvNgayKetThucHopDong.setText(chuyenDinhDangNgay(hopDong.ngay_hop_dong))
            binding.tvSoTienDenBuHopDong.setText(""+tienCocDenBu)
        }else if (hopDong.trang_thai_hop_dong==1){
            binding.tvThoiHanXuLyHopDong.setText("Còn hạn")
            binding.tvThoiHanXuLyHopDong.setTextColor(Color.BLACK)
            binding.tvNgayKetThucHopDong.setText(chuyenDinhDangNgay(simpleDateFormatNow.format(cNow!!.time)))
            tienCocDenBu = hopDong.tien_coc
            binding.tvSoTienDenBuHopDong.setText(""+tienCocDenBu)
        }else{
            binding.tvThoiHanXuLyHopDong.setText("Sắp hết hạn")
            binding.tvThoiHanXuLyHopDong.setTextColor(Color.BLUE)
            binding.tvNgayKetThucHopDong.setText(chuyenDinhDangNgay(simpleDateFormatNow.format(cNow!!.time)))
            binding.tvSoTienDenBuHopDong.setText(""+tienCocDenBu)
        }


        binding.chkThietHai.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b -> //Code khi trạng thái check thay đổi
            binding.layoutTienDenBu.isVisible = true
        })

        binding.btnKiemTraTaiSan.setOnClickListener {
            if (binding.chkThietHai.isChecked) {
                if(binding.edTienThietHai.text.toString().isNotBlank()){
                    binding.tvTienThietHaiXuLyPhong.setText(""+binding.edTienThietHai.text.toString())
                }else{
                    thongBaoLoi("Bạn cần điền số tiền thiệt hại trước khi kiểm tra!")
                    return@setOnClickListener
                }

            }else{
                binding.tvTienThietHaiXuLyPhong.setText(""+0)
            }
            binding.chkKiemTraXuLyPhong.isChecked = true
            binding.tvCongViecXuLyPhong.setText(""+1+"/2")
        }


        binding.btnDaThucHienXuLyPhong.setOnClickListener {
            binding.chkThanhToanXuLy.isChecked = true
            if(hopDong.trang_thai_hop_dong==0){
                if (binding.chkThietHai.isChecked){
                    tongTien = hopDong.tien_coc-binding.tvTienThietHaiXuLyPhong.text.toString().toInt()
                    binding.tvTongTien.setText("Tiền thanh toán cho khách: ")
                    binding.tvTienConLaiXuLyPhong.setText(""+tongTien)
                }else{
                    tongTien = hopDong.tien_coc-0
                    binding.tvTongTien.setText("Tiền thanh toán cho khách: ")
                    binding.tvTienConLaiXuLyPhong.setText(""+tongTien)
                }
            }else if (hopDong.trang_thai_hop_dong==1){
                if (binding.chkThietHai.isChecked){
                    tongTien = tienCocDenBu+binding.tvTienThietHaiXuLyPhong.text.toString().toInt()
                    binding.tvTongTien.setText("Tiền cần thu của khách: ")
                    binding.tvTienConLaiXuLyPhong.setText(""+tongTien)
                }else{
                    tongTien = tienCocDenBu+0
                    binding.tvTongTien.setText("Tiền cần thu của khách: ")
                    binding.tvTienConLaiXuLyPhong.setText(""+tongTien)
                }
            }else if (hopDong.trang_thai_hop_dong==2){
                if (binding.chkThietHai.isChecked){
                    tongTien = hopDong.tien_coc-binding.tvTienThietHaiXuLyPhong.text.toString().toInt()
                    binding.tvTongTien.setText("Tiền thanh toán cho khách: ")
                    binding.tvTienConLaiXuLyPhong.setText(""+tongTien)
                }else{
                    tongTien = hopDong.tien_coc-0
                    binding.tvTongTien.setText("Tiền thanh toán cho khách: ")
                    binding.tvTienConLaiXuLyPhong.setText(""+tongTien)
                }
            }
            binding.tvCongViecXuLyPhong.setText(""+2+"/2")
        }

        binding.btnXoaThongTinPhong.setOnClickListener {
            if (binding.chkKiemTraXuLyPhong.isChecked && binding.chkThanhToanXuLy.isChecked){
                updateHD(hopDong,binding)
            }else{
                thongBaoLoi("Hãy thực hiện đủ các thao tác để hoàn thành việc kết thúc hợp đồng!")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId();
        if (id == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }

    private fun updateHD(hopDong: HopDong, binding: ActivityKetThucHopDongBinding) {
        val hopDongNew = HopDong(
            ma_hop_dong = hopDong.ma_hop_dong,
            ma_phong = hopDong.ma_phong,
            ma_khach_thue = hopDong.ma_khach_thue,
            thoi_han = hopDong.thoi_han,
            ngay_o = hopDong.ngay_o,
            ngay_hop_dong = chuyenDinhDangNgayChuan(binding.tvNgayKetThucHopDong.text.toString()),
            tien_coc = hopDong.tien_coc,
            anh_hop_dong = hopDong.anh_hop_dong,
            trang_thai_hop_dong = 0,
            hieu_luc_hop_dong = 0,
            ngay_lap_hop_dong = hopDong.ngay_lap_hop_dong
        )
        val updatePhong = PhongDao(this@ActivityKetThucHopDong).updateTrangThaiPhongThanhDaO(hopDong.ma_phong)

        var count =0
        val listNDTrongPhong = KhachThueDao(this).getKhachThueByMaPhong(hopDong.ma_phong)
        for (i in 0 until listNDTrongPhong.size){
            if(updateNDTrongPhong(listNDTrongPhong.get(i))>0){
                count++
            }
        }
        var update = HopDongDao(this.binding.root.context).updateHopDong(hopDongNew)
        if (update>0 && count==listNDTrongPhong.size && updatePhong>0){
            thongBaoThanhCong("Kết thúc hợp đồng thành công!")
        }
    }

    private fun updateNDTrongPhong(nguoiDung: KhachThue):Int {
        val nguoiDungNew = KhachThue(
            ma_khach_thue = nguoiDung.ma_khach_thue,
            ho_ten = nguoiDung.ho_ten,
            nam_sinh = nguoiDung.nam_sinh,
            sdt_khach_thue = nguoiDung.sdt_khach_thue,
            que_quan = nguoiDung.que_quan,
            cccd = nguoiDung.cccd,
            trang_thai_chu_hop_dong = 0,
            trang_thai_o = 0,
            ma_phong = nguoiDung.ma_phong
        )
        var update = KhachThueDao(this).updateKhachThue(nguoiDungNew)
        return update
    }

    private fun chuyenDinhDangNgay(ngay: String):String {
        val sdfNgay = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatNgayO = DateFormat()
        val objDateNgayO = sdfNgay.parse(ngay)
        val ngay = DateFormat.format("dd/MM/yyyy", objDateNgayO) as String
        return ngay
    }

    private fun chuyenDinhDangNgayChuan(text: String):String {
        var ngay_chuan_dinh_dang = ""
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val dateFormat = android.text.format.DateFormat()
            val objDate = sdf.parse(text.toString().trim { it <= ' ' })
            ngay_chuan_dinh_dang =
                android.text.format.DateFormat.format("yyyy-MM-dd", objDate) as String
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ngay_chuan_dinh_dang
    }

    fun thongBaoLoi(loi : String){
        val bundle = androidx.appcompat.app.AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo Lỗi")
        bundle.setMessage(loi)
        bundle.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        bundle.show()
    }
    fun thongBaoThanhCong(loi : String){
        val bundle = androidx.appcompat.app.AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo")
        bundle.setMessage(loi)
        bundle.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
//            val intent = Intent(this@ActivityKetThucHopDong,ActivityXuLyPhong::class.java)
//            startActivity(intent)
            finish()
        })
        bundle.show()
    }
}