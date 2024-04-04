package com.example.appqlpt.model

data class KhachThue(
    val ma_khach_thue:String,
    val ho_ten:String,
    val cccd:String,
    val nam_sinh:String,
    val sdt_khach_thue:String,
    val que_quan:String,
    val trang_thai_chu_hop_dong:Int,
    val trang_thai_o:Int,
    val ma_phong:String
):java.io.Serializable {
    companion object{
        const val TB_NAME="KhachThue"
        const val CLM_MA_KHACH_THUE="MaKhachThue"
        const val CLM_HO_TEN_KHACH_THUE="HoTen"
        const val CLM_CCCD="CCCD"
        const val CLM_NAM_SINH="NamSinh"
        const val CLM_SDT_KHACH_THUE="SDT"
        const val CLM_QUE_QUAN="QueQuan"
        const val CLM_TRANG_THAI_CHU_HOP_DONG="TrangThaiChuHopDong"
        const val CLM_TRANG_THAI_O="TrangThaiO"
        const val CLM_MA_PHONG="MaPhong"
        fun timkiemKhach(list:List<KhachThue>,s:String):List<KhachThue>{
            return list.filter{it.ho_ten.contains(s)}
        }
    }
}