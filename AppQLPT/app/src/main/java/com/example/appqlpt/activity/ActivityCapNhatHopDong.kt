package com.example.appqlpt.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.appqlpt.R
import com.example.appqlpt.adapter.FILE_NAME
import com.example.appqlpt.adapter.MA_KHU_KEY
import com.example.appqlpt.adapter.NguoiThueSpinnerAdapter
import com.example.appqlpt.database.HopDongDao
import com.example.appqlpt.database.KhachThueDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.ActivityCapNhatHopDongBinding
import com.example.appqlpt.model.HopDong
import com.example.appqlpt.model.KhachThue
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ActivityCapNhatHopDong : AppCompatActivity() {
    private lateinit var binding: ActivityCapNhatHopDongBinding
    var thoiHan = 0
    var mYear = 0
    var mMonth = 0
    var mDay = 0
    var mYearNow = 0
    var mMonthNow = 0
    var mDayNow = 0
    var mYear2 = 0
    var mMonth2 = 0
    var mDay2 = 0
    var mDateNgayO: Any? = null
    var dao: Int = 0
    var listND = listOf<KhachThue>()
    var listMaphong = listOf<String>()
    var listHopDong = listOf<HopDong>()
    private val simpleDateFormatNow = SimpleDateFormat("yyyy-MM-dd")
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private var maND = ""
    private var maPhong = ""
    private var tenPhong = ""
    private var maKhu = ""
    private var activityDanhSachHopDong: ActivityDanhSachHopDong = ActivityDanhSachHopDong()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCapNhatHopDongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tbTaoHopDongMoi
        setSupportActionBar(binding.tbTaoHopDongMoi)
        val ab = getSupportActionBar()
        ab?.setHomeAsUpIndicator(R.drawable.back_left)
        ab?.setDisplayHomeAsUpEnabled(true)
        //=================================================================
        val srf = binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
        val i = intent
        val hopDong: HopDong = i.getSerializableExtra("hopDong") as HopDong

        listND = KhachThueDao(this@ActivityCapNhatHopDong).getKhachThueByMaPhong(hopDong.ma_phong)
        binding.edTenPhongTro.setText(
            "" + PhongDao(this@ActivityCapNhatHopDong).getTenPhongById(
                hopDong.ma_phong
            )
        )
        binding.edNgayBatDauO.setText("" + chuyenDinhDangNgay(hopDong.ngay_o))
        binding.edThoiHan.setText("" +hopDong.thoi_han)
        binding.edNgayHetHan.setText("" + chuyenDinhDangNgay(hopDong.ngay_hop_dong))
        binding.edTienCoc.setText("" + hopDong.tien_coc)
        binding.chkTrangThai.isChecked = if (hopDong.trang_thai_hop_dong == 1 || hopDong.trang_thai_hop_dong==2) true else false
        binding.chkTrangThai.isClickable = false
        val spinner = NguoiThueSpinnerAdapter(this, listND)
        binding.spinnerNguoiDung.adapter = spinner
        binding.spinnerNguoiDung.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                maND = listND[position].ma_khach_thue
                Toast.makeText(this@ActivityCapNhatHopDong, maND, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        var posND = 0
        for (i in 0 until listND.size) {
            if (hopDong.ma_khach_thue == listND.get(i).ma_khach_thue) {
                posND = i
            }
        }
        binding.spinnerNguoiDung.setSelection(posND)

//        if (listND.size == 0) {
//            binding.tvThemNguoiThue.isVisible = true
//            binding.tvThemNguoiThue.setOnClickListener {
//                val dialog = DialogThemKhachThueHopDongBinding.inflate(LayoutInflater.from(this))
//                val build = AlertDialog.Builder(this).create()
//                val listPhong = PhongDao(this).getAllInPhongByMaKhu(maKhu)
//                dialog.edTenPhong.setText(tenPhong)
//                dialog.btnLuuThemNguoiDung.setOnClickListener {
//                    if (validateNguoiDung(dialog)<1){
//                        thongBaoLoi("Nhập đầy đủ dữ liệu!")
//                        return@setOnClickListener
//                    }else{
//                        val maNguoiDung = UUID.randomUUID().toString()
//                        val nguoiDung = NguoiDung(
//                            ma_nguoi_dung = maNguoiDung,
//                            ho_ten_nguoi_dung = dialog.edHoTenThemNguoiDung.text.toString(),
//                            nam_sinh = dialog.edNgaySinhThemNguoiDung.text.toString(),
//                            sdt_nguoi_dung = dialog.edSDTThemNguoiDung.text.toString(),
//                            que_quan = dialog.edQueQuanThemNguoiDung.text.toString(),
//                            cccd = dialog.edCCCDThemNguoiDung.text.toString(),
//                            trang_thai_chu_hop_dong = 0,
//                            trang_thai_o = 1,
//                            ma_phong = maPhong
//                        )
//                        val dao = NguoiDungDao(dialog.root.context).insertNguoiDung(nguoiDung)
//                        if (dao > 0) {
//                            onResume()
//                            onPause()
//                            build.dismiss()
//
//                        } else {
//                            thongBaoLoi("Bạn thêm không thành công!")
//                        }
//                        dialog.edHoTenThemNguoiDung.setText("")
//                        dialog.edSDTThemNguoiDung.setText("")
//                        dialog.edCCCDThemNguoiDung.setText("")
//                        dialog.edNgaySinhThemNguoiDung.setText("")
//                        dialog.edQueQuanThemNguoiDung.setText("")
//                    }
//                }
//                dialog.btnHuyThemNguoiDung.setOnClickListener {
//                    build.dismiss()
//                    onResume()
//                }
//                build.setView(dialog.root)
//                build.show()
//            }
//
//
//        }
        binding.edTenPhongTro.isEnabled = false
        binding.edNgayHetHan.isEnabled = false
        binding.edTenPhongTro.setTextColor(Color.BLACK)
        binding.edNgayHetHan.setTextColor(Color.BLACK)


        binding.imgCalendar.setOnClickListener {
            val c = Calendar.getInstance() as GregorianCalendar?
            mYear = (c as Calendar).get(Calendar.YEAR)
            mMonth = c!!.get(Calendar.MONTH)
            mDay = c!!.get(Calendar.DAY_OF_MONTH)
            val d = DatePickerDialog(
                this,
                0,
                mDateNgayO as DatePickerDialog.OnDateSetListener?,
                mYear,
                mMonth,
                mDay
            )
            d.show()
        }
        binding.edNgayBatDauO.setOnClickListener {
            val c = Calendar.getInstance() as GregorianCalendar?
            mYear = (c as Calendar).get(Calendar.YEAR)
            mMonth = c!!.get(Calendar.MONTH)
            mDay = c!!.get(Calendar.DAY_OF_MONTH)
            val d = DatePickerDialog(
                this,
                0,
                mDateNgayO as DatePickerDialog.OnDateSetListener?,
                mYear,
                mMonth,
                mDay
            )
            d.show()
        }
        binding.edThoiHan.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrBlank() && binding.edNgayBatDauO.text.toString().isNotBlank()) {
                val stringOldDate = binding.edNgayBatDauO.text.toString()
                //val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                val newDate = dateFormat.parse(stringOldDate)
                val calendar = Calendar.getInstance()
                if (newDate != null) {
                    calendar.time = newDate
                }
                val month = calendar.get(Calendar.MONTH) + text.toString().toInt()
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val year = calendar.get(Calendar.YEAR)
                val c1 = GregorianCalendar(year, month, day)
                binding.chkTrangThai.isChecked = true
                binding.edNgayHetHan.setText(simpleDateFormat.format(c1!!.time))
            }
        }
        mDateNgayO = OnDateSetListener { datePicker, i, i1, i2 ->
            mYear = i
            mMonth = i1
            mDay = i2
            val c = GregorianCalendar(mYear, mMonth, mDay)
            binding.edNgayBatDauO.setText(simpleDateFormat.format(c!!.time))
            if (binding.edThoiHan.text?.isNotBlank() == true)
                thoiHan = binding.edThoiHan.text.toString().toInt()
            mYear2 = i
            mMonth2 = i1 + thoiHan
            mDay2 = i2
            val c1 = GregorianCalendar(mYear2, mMonth2, mDay2)
            binding.edNgayHetHan.setText(simpleDateFormat.format(c1!!.time))
        }

        binding.btnLuuHopDong.setOnClickListener {
            if (validate() < 1) {
                thongBaoLoi("Dữ liệu không được để trống!")
                return@setOnClickListener
            }else {
                val c = Calendar.getInstance() as GregorianCalendar?
                mYearNow = (c as Calendar).get(Calendar.YEAR)
                mMonthNow = c!!.get(Calendar.MONTH)
                mDayNow = c!!.get(Calendar.DAY_OF_MONTH)
                val cNow = GregorianCalendar(mYearNow, mMonthNow, mDayNow)
                // Validate Ngay bat dau o
                try {
                    // Kiểm tra dữ liệu nhập vào
                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val dateFormat = android.text.format.DateFormat()
                    val objDate =
                        sdf.parse(binding.edNgayBatDauO.getText().toString().trim { it <= ' ' })
                    val ngayBatDauO =
                        android.text.format.DateFormat.format("dd/MM/yyyy", objDate) as String
                    binding.edNgayBatDauO.setText(ngayBatDauO)
                } catch (ex: Exception) {
                    Toast.makeText(
                        this@ActivityCapNhatHopDong,
                        "Ngày ở không đúng định dạng(dd/MM/yyyy)",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                // Validate Het hop Dong
                try {
                    // Kiểm tra dữ liệu nhập vào
                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val dateFormat = android.text.format.DateFormat()
                    val objDate =
                        sdf.parse(binding.edNgayHetHan.getText().toString().trim { it <= ' ' })
                    val ngayBatDauO =
                        android.text.format.DateFormat.format("dd/MM/yyyy", objDate) as String
                    binding.edNgayHetHan.setText(ngayBatDauO)
                } catch (ex: Exception) {
                    Toast.makeText(
                        this@ActivityCapNhatHopDong,
                        "Ngày hết hợp đồng không đúng định dạng(dd/MM/yyyy)",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                val hopDongNew = HopDong(
                    ma_hop_dong = hopDong.ma_hop_dong,
                    ma_phong = hopDong.ma_phong,
                    ma_khach_thue = maND,
                    thoi_han = binding.edThoiHan.text.toString().toInt(),
                    ngay_o = chuyenDinhDangNgayChuan(binding.edNgayBatDauO.getText().toString()),
                    ngay_hop_dong = chuyenDinhDangNgayChuan(binding.edNgayHetHan.getText().toString()),
                    tien_coc = binding.edTienCoc.text.toString().toInt(),
                    anh_hop_dong = "aaaa",
                    trang_thai_hop_dong = if (binding.chkTrangThai.isChecked) 1 else 0,
                    hieu_luc_hop_dong = 1,
                    ngay_lap_hop_dong = hopDong.ngay_lap_hop_dong
                )
                val dao = HopDongDao(this).updateHopDong(hopDongNew)
                if (dao > 0) {
                    thongBaoThanhCong("Bạn đã cập nhật thành công!")
                    //updateHopDong()
                } else {
                    thongBaoLoi("Bạn đã cập nhật không thành công!")
                }

                //activityDanhSachHopDong.updateHopDong(hopDong)
            }

        }

        binding.btnHuyHopDong.setOnClickListener {
            xoaTrang()
        }
    }

    private fun chuyenDinhDangNgay(ngay: String):String {
        val sdfNgay = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatNgayO = android.text.format.DateFormat()
        val objDateNgayO = sdfNgay.parse(ngay)
        val ngay = android.text.format.DateFormat.format("dd/MM/yyyy", objDateNgayO) as String
        return ngay
    }
    // Chuyen Dinh Dang Ngay
    private fun chuyenDinhDangNgayChuan(text: String): String {
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


    fun validate(): Int {
        var check = -1
        if (binding.edThoiHan.text.toString().isNotBlank() && binding.edNgayBatDauO.text.toString()
                .isNotBlank()
            && binding.edTienCoc.text.toString()
                .isNotBlank() && binding.edNgayHetHan.text.toString().isNotBlank()
            && binding.edTenPhongTro.text.toString().isNotBlank()
        ) {
            check = 1
        }
        return check
    }

    fun xoaTrang() {
        binding.edThoiHan.setText("")
        binding.edNgayBatDauO.setText("")
        binding.edTienCoc.setText("")
        binding.edNgayHetHan.setText("")
    }

    fun thongBaoLoi(loi: String) {
        val bundle = androidx.appcompat.app.AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo Lỗi")
        bundle.setMessage(loi)
        bundle.setNegativeButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        bundle.show()
    }

    fun thongBaoThanhCong(loi: String) {
        val bundle = androidx.appcompat.app.AlertDialog.Builder(this)
        bundle.setTitle("Thông Báo")
        bundle.setMessage(loi)
        bundle.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
            finish()
        })
        bundle.setPositiveButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        bundle.show()
    }

    fun chuyenActivity() {
        val intent = Intent(this@ActivityCapNhatHopDong, ActivityDanhSachHopDong::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId();
        if (id == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item);
    }

//    override fun onResume() {
//        super.onResume()
//        listND = NguoiDungDao(this@ActivityCapNhatHopDong).getNguoiDungByMaPhong(maPhong)
//        val spinner = NguoiThueSpinnerAdapter(this, listND)
//        binding.spinnerNguoiDung.adapter = spinner
//        binding.spinnerNguoiDung.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                maND = listND[position].ma_nguoi_dung
//                Toast.makeText(this@ActivityCapNhatHopDong, maND, Toast.LENGTH_SHORT).show()
//            }
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }
//    }

//    override fun onPause() {
//        super.onPause()
//        binding.tvThemNguoiThue.isVisible = false
//    }
}


