package com.example.appqlpt.model

data class ThongBao(
    val ma_thong_bao:String,
    val tieu_de:String,
    val ma_khu:String,
    val ngay_thong_bao:String,
    val loai_thong_bao:Int,
    val noi_dung:String
) {
    companion object{
        const val TB_NAME="ThongBao"
        const val CLM_MA_THONG_BAO="MaThongBao"
        const val CLM_TIEU_DE="TieuDe"
        const val CLM_MA_KHU="MaKhu"
        const val CLM_NGAY_THONG_BAO="NgayThongBao"
        const val CLM_LOAI_THONG_BAO="LoaiThongBao"
        const val CLM_NOI_DUNG="NoiDung"
    }
}