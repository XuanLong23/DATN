package com.example.appqlpt.model

data class HoaDon(
    val ma_hoa_don:String,
    val gia_thue:Int,
    val ngay_tao_hoa_don:String,
    var trang_thai_hoa_don:Int,
    val so_dien:Int,
    val so_nuoc:Int,
    val gia_dich_vu:Int,
    val mien_giam:Int,
    val tong:Long,
    var ma_phong:String
) {
    companion object{
        const val TB_NAME="HoaDon"
        const val CLM_MA_HOA_DON="MaHoaDon"
        const val CLM_GIA_THUE="GiaThue"
        const val CLM_NGAY_TAO_HOA_DON="NgayTaoHoaDon"
        const val CLM_TRANG_THAI_HOA_DON="TrangThaiHoaDon"
        const val CLM_SO_DIEN="SoDien"
        const val CLM_SO_NUOC="SoNuoc"
        const val CLM_GIA_DICH_VU="GiaDichVu"
        const val CLM_MIEN_GIAM="MienGiam"
        const val CLM_TONG="Tong"
        const val CLM_MA_PHONG="MaPhong"
    }
}