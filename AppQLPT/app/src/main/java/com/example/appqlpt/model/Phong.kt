package com.example.appqlpt.model

data class Phong(
    val ma_phong:String,
    val ten_phong:String,
    val dien_tich:Int,
    val gia_thue:Long,
    val so_nguoi_o:Int,
    val trang_thai_phong:Int,
    val ma_khu:String
):java.io.Serializable{
    companion object{
        const val TB_NAME="Phong"
        const val CLM_MA_PHONG="MaPhong"
        const val CLM_TEN_PHONG="TenPhong"
        const val CLM_DIEN_TICH="DienTich"
        const val CLM_GIA_THUE="GiaThue"
        const val CLM_SO_NGUOI_O="SoNguoiO"
        const val CLM_TRANG_THAI_PHONG="TrangThaiPhong"
        const val CLM_MA_KHU="MaKhu"
    }
}