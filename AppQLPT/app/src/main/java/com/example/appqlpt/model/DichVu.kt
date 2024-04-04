package com.example.appqlpt.model

data class DichVu(
    val ma_dich_vu:String,
    val ten_dich_vu:String,
    val gia_dich_vu:Int,
    val trang_thai_dich_vu:Int,
    val so_cu:Int,
    val so_moi:Int,
    val ma_khu_tro:String,
    val ma_phong:String
) {
    companion object{
        const val TB_NAME="DichVu"
        const val CLM_MA_DICH_VU="MaDichVu"
        const val CLM_TEN_DICH_VU="TenDichVu"
        const val CLM_GIA_DICH_VU="GiaDichVu"
        const val CLM_TRANG_THAI_DICH_VU="TrangThaiDichVu"
        const val CLM_SO_CU="SoCu"
        const val CLM_SO_MOI="SoMoi"
        const val CLM_MA_PHONG="MaPhong"
        const val CLM_MA_KHU_TRO="MaKhuTro"
    }
}