package com.example.appqlpt.model

data class ChuTro (
    val sdt:String,
    val ten_dang_nhap:String,
    val ho_ten:String,
    val stk:String,
    val ngan_hang:String,
    val ngay_sinh:String,
    val mat_khau:String):java.io.Serializable{
    companion object{
        const val TB_NAME="ChuTro"
        const val CLM_SDT="SdtChuTro"
        const val CLM_TEN_DANG_NHAP="TenDangNhap"
        const val CLM_HO_TEN="HoTen"
        const val CLM_STK="STK"
        const val CLM_NGAN_HANG="NganHang"
        const val CLM_NGAY_SINH="NgaySinh"
        const val CLM_MAT_KHAU="MatKhau"
    }

}