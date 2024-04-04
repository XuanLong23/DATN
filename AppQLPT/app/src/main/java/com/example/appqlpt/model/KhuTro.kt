package com.example.appqlpt.model

data class KhuTro(
    val ma_khu_tro:String,
    val ten_khu_tro:String,
    val dia_chi:String,
    val so_luong_phong:Int,
    val ten_dang_nhap:String
) {
    companion object{
        const val TB_NAME="KhuTro"
        const val CLM_MA_KHU_TRO="MaKhuTro"
        const val CLM_TEN_KHU_TRO="TenKhuTro"
        const val CLM_DIA_CHI="DiaChi"
        const val CLM_SO_LUONG_PHONG="SoLuongPhong"
        const val CLM_TEN_DANG_NHAP="TenDangNhap"
    }
}