package com.example.appqlpt.model

data class HopDong(
    val ma_hop_dong:String,
    var thoi_han:Int,
    var ngay_o:String,
    var ngay_hop_dong:String,
    var ngay_lap_hop_dong:String,
    var anh_hop_dong:String,
    var tien_coc:Int,
    var trang_thai_hop_dong:Int,
    var hieu_luc_hop_dong:Int,
    var ma_phong:String,
    var ma_khach_thue:String
):java.io.Serializable {
    companion object{
        const val TB_NAME="HopDong"
        const val CLM_MA_HOP_DONG="MaHopDong"
        const val CLM_THOI_HAN="ThoiHan"
        const val CLM_NGAY_O="NgayO"
        const val CLM_NGAY_HOP_DONG="NgayHopDong"
        const val CLM_NGAY_LAP_HOP_DONG="NgayLapHopDong"
        const val CLM_ANH_HOP_DONG="AnhHopDong"
        const val CLM_TIEN_COC="TienCoc"
        const val CLM_TRANG_THAI_HOP_DONG="TrangThaiHopDong"
        const val CLM_HIEU_LUC_HOP_DONG="HieuLucHopDong"
        const val CLM_MA_PHONG="MaPhong"
        const val CLM_MA_KHACH_THUE="MaKhachThue"
    }
}