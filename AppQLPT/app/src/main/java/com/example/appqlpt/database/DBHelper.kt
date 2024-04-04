package com.example.appqlpt.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appqlpt.model.*

class DBHelper (context:Context) : SQLiteOpenHelper(context, DB_NAME,null,1){
    companion object{
        const val DB_NAME="MyDB"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val chu_tro="""
            CREATE TABLE ${ChuTro.TB_NAME}(
            ${ChuTro.CLM_SDT} text unique not null,
            ${ChuTro.CLM_TEN_DANG_NHAP} primary key not null,
            ${ChuTro.CLM_HO_TEN} text not null,
            ${ChuTro.CLM_STK} text,
            ${ChuTro.CLM_NGAN_HANG} text,
            ${ChuTro.CLM_NGAY_SINH} text,
            ${ChuTro.CLM_MAT_KHAU} text not null );
        """.trimIndent()
        p0?.execSQL(chu_tro)

        val khu_tro="""
            CREATE TABLE ${KhuTro.TB_NAME}(
            ${KhuTro.CLM_MA_KHU_TRO} text primary key not null,
            ${KhuTro.CLM_TEN_KHU_TRO} text not null,
            ${KhuTro.CLM_DIA_CHI} text not null,
            ${KhuTro.CLM_SO_LUONG_PHONG} int not null,
            ${KhuTro.CLM_TEN_DANG_NHAP} text not null,
            FOREIGN KEY(${KhuTro.CLM_TEN_DANG_NHAP})  REFERENCES ${ChuTro.TB_NAME}(${ChuTro.CLM_TEN_DANG_NHAP})
            )
        """.trimIndent()
        p0?.execSQL(khu_tro)

        val phong="""
            CREATE TABLE ${Phong.TB_NAME}(
            ${Phong.CLM_MA_PHONG} text primary key not null,
            ${Phong.CLM_TEN_PHONG} text not null,
            ${Phong.CLM_DIEN_TICH} int not null,
            ${Phong.CLM_GIA_THUE} long not null,
            ${Phong.CLM_SO_NGUOI_O} int not null,
            ${Phong.CLM_TRANG_THAI_PHONG} int not null,
            ${Phong.CLM_MA_KHU} text not null,
            FOREIGN KEY (${Phong.CLM_MA_KHU}) REFERENCES ${KhuTro.TB_NAME}(${KhuTro.CLM_MA_KHU_TRO}))
        """.trimIndent()
        p0?.execSQL(phong)

        val dich_vu="""
            CREATE TABLE ${DichVu.TB_NAME}(
            ${DichVu.CLM_MA_DICH_VU} text primary key not null,
            ${DichVu.CLM_TEN_DICH_VU} text not null,
            ${DichVu.CLM_GIA_DICH_VU} int not null,
            ${DichVu.CLM_MA_PHONG} text not null,
            ${DichVu.CLM_TRANG_THAI_DICH_VU} int not null,
            ${DichVu.CLM_MA_KHU_TRO} text not null,
            ${DichVu.CLM_SO_CU} int not null,
            ${DichVu.CLM_SO_MOI} int not null,
            FOREIGN KEY (${DichVu.CLM_MA_PHONG}) REFERENCES ${Phong.TB_NAME}(${Phong.CLM_MA_PHONG}),
            FOREIGN KEY (${DichVu.CLM_MA_KHU_TRO}) REFERENCES ${KhuTro.TB_NAME}(${KhuTro.CLM_MA_KHU_TRO}))
        """.trimIndent()
        p0?.execSQL(dich_vu)

        val hoa_don="""
            CREATE TABLE ${HoaDon.TB_NAME}(
            ${HoaDon.CLM_MA_HOA_DON} text primary key not null,
            ${HoaDon.CLM_NGAY_TAO_HOA_DON} text not null,
            ${HoaDon.CLM_GIA_THUE} int not null,
            ${HoaDon.CLM_SO_DIEN} int not null,
            ${HoaDon.CLM_SO_NUOC} int not null,
            ${HoaDon.CLM_TRANG_THAI_HOA_DON} int not null,
            ${HoaDon.CLM_MIEN_GIAM} int not null,
            ${HoaDon.CLM_GIA_DICH_VU} int not null,
            ${HoaDon.CLM_MA_PHONG} text not null,
            ${HoaDon.CLM_TONG} long not null,
            FOREIGN KEY (${HoaDon.CLM_MA_PHONG}) REFERENCES ${Phong.TB_NAME}(${Phong.CLM_MA_PHONG}))
        """.trimIndent()
        p0?.execSQL(hoa_don)

        val khach_thue="""
            CREATE TABLE ${KhachThue.TB_NAME}(
            ${KhachThue.CLM_MA_KHACH_THUE} text primary key not null,
            ${KhachThue.CLM_HO_TEN_KHACH_THUE} text not null,
            ${KhachThue.CLM_CCCD} text not null,
            ${KhachThue.CLM_NAM_SINH} text not null,
            ${KhachThue.CLM_QUE_QUAN} text not null,
            ${KhachThue.CLM_SDT_KHACH_THUE} text unique not null,
            ${KhachThue.CLM_MA_PHONG} text not null,
            ${KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG} int not null,
            ${KhachThue.CLM_TRANG_THAI_O} int not null,
            FOREIGN KEY (${KhachThue.CLM_MA_PHONG}) REFERENCES ${Phong.TB_NAME}(${Phong.CLM_MA_PHONG}))
        """.trimIndent()
        p0?.execSQL(khach_thue)

        val hop_dong="""
            CREATE TABLE ${HopDong.TB_NAME}(
            ${HopDong.CLM_MA_HOP_DONG} text primary key not null,
            ${HopDong.CLM_THOI_HAN} int not null,
            ${HopDong.CLM_NGAY_O} text not null,
            ${HopDong.CLM_NGAY_HOP_DONG} text not null,
            ${HopDong.CLM_NGAY_LAP_HOP_DONG} text not null,
            ${HopDong.CLM_ANH_HOP_DONG} text not null,
            ${HopDong.CLM_TIEN_COC} long not null,
            ${HopDong.CLM_TRANG_THAI_HOP_DONG} int not null,
            ${HopDong.CLM_HIEU_LUC_HOP_DONG} int not null,
            ${HopDong.CLM_MA_PHONG} text not null,
            ${HopDong.CLM_MA_KHACH_THUE} text not null,
            FOREIGN KEY (${HopDong.CLM_MA_PHONG}) REFERENCES ${Phong.TB_NAME}(${Phong.CLM_MA_PHONG}),
            FOREIGN KEY (${HopDong.CLM_MA_KHACH_THUE}) REFERENCES ${KhachThue.TB_NAME}(${KhachThue.CLM_MA_KHACH_THUE}))
        """.trimIndent()
        p0?.execSQL(hop_dong)

        val thong_bao="""
            CREATE TABLE ${ThongBao.TB_NAME}(
            ${ThongBao.CLM_MA_THONG_BAO} text primary key not null,
            ${ThongBao.CLM_TIEU_DE} text not null,
            ${ThongBao.CLM_NGAY_THONG_BAO} text not null,
            ${ThongBao.CLM_NOI_DUNG} text not null,
            ${ThongBao.CLM_MA_KHU} text not null,
            ${ThongBao.CLM_LOAI_THONG_BAO} int not null,
            FOREIGN KEY (${ThongBao.CLM_MA_KHU}) REFERENCES ${KhuTro.TB_NAME}(${KhuTro.CLM_MA_KHU_TRO}))
        """.trimIndent()
        p0?.execSQL(thong_bao)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}