package com.example.appqlpt.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.appqlpt.model.DichVu

class DichVuDao(context:Context) {
    val dbHelper=DBHelper(context)
    val db=dbHelper.writableDatabase

    fun insertDichVu(dichvu:DichVu):Long{
        val values=ContentValues()
        values.apply {
            put(DichVu.CLM_MA_DICH_VU,dichvu.ma_dich_vu)
            put(DichVu.CLM_TEN_DICH_VU,dichvu.ten_dich_vu)
            put(DichVu.CLM_GIA_DICH_VU,dichvu.gia_dich_vu)
            put(DichVu.CLM_TRANG_THAI_DICH_VU,dichvu.trang_thai_dich_vu)
            put(DichVu.CLM_MA_PHONG,dichvu.ma_phong)
            put(DichVu.CLM_SO_MOI,dichvu.so_moi)
            put(DichVu.CLM_SO_CU,dichvu.so_cu)
            put(DichVu.CLM_MA_KHU_TRO,dichvu.ma_khu_tro)
        }
        return db.insert(DichVu.TB_NAME,null,values)
    }
    fun xoaDichVuByTenVaMaPhong(dichvu:DichVu):Int{
        return db.delete(DichVu.TB_NAME,
        "${DichVu.CLM_TEN_DICH_VU}=? AND ${DichVu.CLM_MA_PHONG}=?",
        arrayOf(dichvu.ten_dich_vu,dichvu.ma_phong))
    }
    fun updateDichVu(id:String,soCu:Int,soMoi:Int):Int{
        val values=ContentValues()
        values.apply {
            put(DichVu.CLM_SO_CU,soCu)
            put(DichVu.CLM_SO_MOI,soMoi)
        }
        return db.update(DichVu.TB_NAME,values,"${DichVu.CLM_MA_DICH_VU}=?", arrayOf(id))
    }
    fun updateDichVuByMaKhuVaTen(dichvu:DichVu, ten:String):Int{
        val values=ContentValues()
        values.apply {
            put(DichVu.CLM_TEN_DICH_VU,dichvu.ten_dich_vu)
            put(DichVu.CLM_GIA_DICH_VU,dichvu.gia_dich_vu)
            put(DichVu.CLM_TRANG_THAI_DICH_VU,dichvu.trang_thai_dich_vu)
        }
        return db.update(DichVu.TB_NAME,values,
        "${DichVu.CLM_TEN_DICH_VU}=? AND ${DichVu.CLM_MA_KHU_TRO}=?",
        arrayOf(ten,dichvu.ma_khu_tro)
        )
    }
    fun xoaDichVuByMaKhuVaTen(dichvu:DichVu):Int{
        return db.delete(DichVu.TB_NAME,"${DichVu.CLM_TEN_DICH_VU}=? AND ${DichVu.CLM_MA_KHU_TRO}=?",
        arrayOf(dichvu.ten_dich_vu,dichvu.ma_khu_tro)
        )
    }
    fun xoaDichVuByMaPhong(maPhong:String):Int{
        return db.delete(
            DichVu.TB_NAME,
            "${DichVu.CLM_MA_PHONG}=?",
            arrayOf(maPhong)
        )
    }
    @SuppressLint("Range")
    fun getAllInDichVu():List<DichVu>{
        val list= mutableListOf<DichVu>()
        val sql="""
            SELECT * FROM ${DichVu.TB_NAME}
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val dichvu=DichVu(
                    ma_dich_vu = c.getString(c.getColumnIndex(DichVu.CLM_MA_DICH_VU)),
                    ten_dich_vu = c.getString(c.getColumnIndex(DichVu.CLM_TEN_DICH_VU)),
                    gia_dich_vu = c.getInt(c.getColumnIndex(DichVu.CLM_GIA_DICH_VU)),
                    trang_thai_dich_vu = c.getInt(c.getColumnIndex(DichVu.CLM_TRANG_THAI_DICH_VU)),
                    ma_phong = c.getString(c.getColumnIndex(DichVu.CLM_MA_PHONG)),
                    ma_khu_tro = c.getString(c.getColumnIndex(DichVu.CLM_MA_KHU_TRO)),
                    so_cu = c.getInt(c.getColumnIndex(DichVu.CLM_SO_CU)),
                    so_moi = c.getInt(c.getColumnIndex(DichVu.CLM_SO_MOI))
                )
                list+=dichvu
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getAllInDichVuByPhong(maPhong:String):List<DichVu>{
        val list= mutableListOf<DichVu>()
        val sql="""
            SELECT * FROM ${DichVu.TB_NAME} WHERE ${DichVu.CLM_MA_PHONG}="$maPhong"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val dichvu=DichVu(
                    ma_dich_vu = c.getString(c.getColumnIndex(DichVu.CLM_MA_DICH_VU)),
                    ten_dich_vu = c.getString(c.getColumnIndex(DichVu.CLM_TEN_DICH_VU)),
                    gia_dich_vu = c.getInt(c.getColumnIndex(DichVu.CLM_GIA_DICH_VU)),
                    trang_thai_dich_vu = c.getInt(c.getColumnIndex(DichVu.CLM_TRANG_THAI_DICH_VU)),
                    ma_phong = c.getString(c.getColumnIndex(DichVu.CLM_MA_PHONG)),
                    ma_khu_tro = c.getString(c.getColumnIndex(DichVu.CLM_MA_KHU_TRO)),
                    so_cu = c.getInt(c.getColumnIndex(DichVu.CLM_SO_CU)),
                    so_moi = c.getInt(c.getColumnIndex(DichVu.CLM_SO_MOI))
                )
                list+=dichvu
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getAllInDichVuByKhuTro(maKhu:String):List<DichVu>{
        val list= mutableListOf<DichVu>()
        val sql="""
            SELECT DISTINCT * FROM ${DichVu.TB_NAME} WHERE ${DichVu.CLM_MA_KHU_TRO}="$maKhu"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val dichvu=DichVu(
                    ma_dich_vu = c.getString(c.getColumnIndex(DichVu.CLM_MA_DICH_VU)),
                    ten_dich_vu = c.getString(c.getColumnIndex(DichVu.CLM_TEN_DICH_VU)),
                    gia_dich_vu = c.getInt(c.getColumnIndex(DichVu.CLM_GIA_DICH_VU)),
                    trang_thai_dich_vu = c.getInt(c.getColumnIndex(DichVu.CLM_TRANG_THAI_DICH_VU)),
                    ma_phong = c.getString(c.getColumnIndex(DichVu.CLM_MA_PHONG)),
                    ma_khu_tro = c.getString(c.getColumnIndex(DichVu.CLM_MA_KHU_TRO)),
                    so_cu = c.getInt(c.getColumnIndex(DichVu.CLM_SO_CU)),
                    so_moi = c.getInt(c.getColumnIndex(DichVu.CLM_SO_MOI))
                )
                list+=dichvu
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getDichVuById(id:String):DichVu?{
        val sql="""
            SELECT * FROM ${DichVu.TB_NAME} WHERE ${DichVu.CLM_MA_DICH_VU}="$id"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return DichVu(
                ma_dich_vu = c.getString(c.getColumnIndex(DichVu.CLM_MA_DICH_VU)),
                ten_dich_vu = c.getString(c.getColumnIndex(DichVu.CLM_TEN_DICH_VU)),
                gia_dich_vu = c.getInt(c.getColumnIndex(DichVu.CLM_GIA_DICH_VU)),
                trang_thai_dich_vu = c.getInt(c.getColumnIndex(DichVu.CLM_TRANG_THAI_DICH_VU)),
                ma_phong = c.getString(c.getColumnIndex(DichVu.CLM_MA_PHONG)),
                ma_khu_tro = c.getString(c.getColumnIndex(DichVu.CLM_MA_KHU_TRO)),
                so_cu = c.getInt(c.getColumnIndex(DichVu.CLM_SO_CU)),
                so_moi = c.getInt(c.getColumnIndex(DichVu.CLM_SO_MOI))
            )
        }
        return null
    }

}