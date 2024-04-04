package com.example.appqlpt.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.appqlpt.model.ChuTro

class ChuTroDao(context:Context) {
    val dbHelper=DBHelper(context)
    val db=dbHelper.writableDatabase

    fun insertChuTro(chutro:ChuTro):Long{
        val values=ContentValues()
        values.apply {
            put(ChuTro.CLM_TEN_DANG_NHAP,chutro.ten_dang_nhap)
            put(ChuTro.CLM_SDT,chutro.sdt)
            put(ChuTro.CLM_HO_TEN,chutro.ho_ten)
            put(ChuTro.CLM_STK,chutro.stk)
            put(ChuTro.CLM_NGAN_HANG,chutro.ngan_hang)
            put(ChuTro.CLM_NGAY_SINH,chutro.ngay_sinh)
            put(ChuTro.CLM_MAT_KHAU,chutro.mat_khau)
        }
        return db.insert(ChuTro.TB_NAME,null,values)
    }
    fun updateChuTro(chutro:ChuTro):Int{
        val values=ContentValues()
        values.apply {
            put(ChuTro.CLM_TEN_DANG_NHAP,chutro.ten_dang_nhap)
            put(ChuTro.CLM_SDT,chutro.sdt)
            put(ChuTro.CLM_HO_TEN,chutro.ho_ten)
            put(ChuTro.CLM_STK,chutro.stk)
            put(ChuTro.CLM_NGAN_HANG,chutro.ngan_hang)
            put(ChuTro.CLM_NGAY_SINH,chutro.ngay_sinh)
            put(ChuTro.CLM_MAT_KHAU,chutro.mat_khau)
        }
        return db.update(ChuTro.TB_NAME,values,"${ChuTro.CLM_TEN_DANG_NHAP}=?", arrayOf(chutro.ten_dang_nhap))
    }

    @SuppressLint("Range")
    fun getAllChuTro():List<ChuTro>{
        val list= mutableListOf<ChuTro>()
        val sql="SELECT * FROM ${ChuTro.TB_NAME}"
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val chutro=ChuTro(
                    ten_dang_nhap = c.getString(c.getColumnIndex(ChuTro.CLM_TEN_DANG_NHAP)),
                    sdt=c.getString(c.getColumnIndex(ChuTro.CLM_SDT)),
                    ho_ten = c.getString(c.getColumnIndex(ChuTro.CLM_HO_TEN)),
                    stk=c.getString(c.getColumnIndex(ChuTro.CLM_STK)),
                    ngan_hang = c.getString(c.getColumnIndex(ChuTro.CLM_NGAN_HANG)),
                    ngay_sinh = c.getString(c.getColumnIndex(ChuTro.CLM_NGAY_SINH)),
                    mat_khau = c.getString(c.getColumnIndex(ChuTro.CLM_MAT_KHAU))
                )
                list+=chutro
            }while(c.moveToNext())
        }
        return list
    }
    fun checkLogin(username:String,password:String):Boolean{
        val sql="""
            SELECT * FROM ${ChuTro.TB_NAME} 
            WHERE ${ChuTro.CLM_TEN_DANG_NHAP}="$username"
            AND ${ChuTro.CLM_MAT_KHAU}="$password"
        """.trimIndent()
        return try{
            val c=db.rawQuery(sql,null)
            c.moveToFirst()
        }catch (e:java.lang.Exception){
            false
        }
    }

    @SuppressLint("Range")
    fun getChuTro(username:String):ChuTro?{
        val sql="""
            SELECT * FROM ${ChuTro.TB_NAME}
            WHERE ${ChuTro.CLM_TEN_DANG_NHAP}="$username"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return ChuTro(
                ten_dang_nhap = c.getString(c.getColumnIndex(ChuTro.CLM_TEN_DANG_NHAP)),
                sdt=c.getString(c.getColumnIndex(ChuTro.CLM_SDT)),
                ho_ten = c.getString(c.getColumnIndex(ChuTro.CLM_HO_TEN)),
                stk=c.getString(c.getColumnIndex(ChuTro.CLM_STK)),
                ngan_hang = c.getString(c.getColumnIndex(ChuTro.CLM_NGAN_HANG)),
                ngay_sinh = c.getString(c.getColumnIndex(ChuTro.CLM_NGAY_SINH)),
                mat_khau = c.getString(c.getColumnIndex(ChuTro.CLM_MAT_KHAU))
            )
        }
        return null
    }
}