package com.example.appqlpt.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.appqlpt.model.ChuTro
import com.example.appqlpt.model.KhuTro

class KhuTroDao(context:Context) {
    val dbHelper=DBHelper(context)
    val db=dbHelper.writableDatabase

    fun insertKhuTro(khutro:KhuTro):Long{
        val values=ContentValues()
        values.apply {
            put(KhuTro.CLM_MA_KHU_TRO,khutro.ma_khu_tro)
            put(KhuTro.CLM_TEN_KHU_TRO,khutro.ten_khu_tro)
            put(KhuTro.CLM_DIA_CHI,khutro.dia_chi)
            put(KhuTro.CLM_SO_LUONG_PHONG,khutro.so_luong_phong)
            put(KhuTro.CLM_TEN_DANG_NHAP,khutro.ten_dang_nhap)
        }
        return db.insert(KhuTro.TB_NAME,null,values)
    }

    fun updateInKhuTro(khutro: KhuTro):Int{
        val values=ContentValues()
        values.apply {
            put(KhuTro.CLM_MA_KHU_TRO,khutro.ma_khu_tro)
            put(KhuTro.CLM_TEN_KHU_TRO,khutro.ten_khu_tro)
            put(KhuTro.CLM_DIA_CHI,khutro.dia_chi)
            put(KhuTro.CLM_SO_LUONG_PHONG,khutro.so_luong_phong)
            put(KhuTro.CLM_TEN_DANG_NHAP,khutro.ten_dang_nhap)
        }
        return db.update(KhuTro.TB_NAME,values,"${KhuTro.CLM_MA_KHU_TRO}=?", arrayOf(khutro.ma_khu_tro))
    }

    @SuppressLint("Range")
    fun getAllInKhuTroByChuTro(s:String):List<KhuTro>{
        val list= mutableListOf<KhuTro>()
        val sql="""
            SELECT * FROM ${KhuTro.TB_NAME} WHERE ${KhuTro.CLM_TEN_DANG_NHAP}="$s"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do {
                val khutro = KhuTro(
                    ma_khu_tro = c.getString(c.getColumnIndex(KhuTro.CLM_MA_KHU_TRO)),
                    ten_khu_tro = c.getString(c.getColumnIndex(KhuTro.CLM_TEN_KHU_TRO)),
                    dia_chi = c.getString(c.getColumnIndex(KhuTro.CLM_DIA_CHI)),
                    so_luong_phong = c.getInt(c.getColumnIndex(KhuTro.CLM_SO_LUONG_PHONG)),
                    ten_dang_nhap = c.getString(c.getColumnIndex(KhuTro.CLM_TEN_DANG_NHAP))
                )
                list += khutro
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getTenKhuTro():String{
        val sql="""
            SELECT ${KhuTro.CLM_TEN_KHU_TRO} FROM ${KhuTro.TB_NAME} 
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return c.getString(c.getColumnIndex(KhuTro.CLM_TEN_KHU_TRO))
        }
        return "null"
    }
}