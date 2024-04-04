package com.example.appqlpt.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.appqlpt.model.HoaDon
import com.example.appqlpt.model.Phong

class HoaDonDao(context:Context) {
    val dbHelper=DBHelper(context)
    val db=dbHelper.writableDatabase

    fun insertHoaDon(hoadon: HoaDon):Long{
        val values=ContentValues()
        values.apply {
            put(HoaDon.CLM_MA_HOA_DON,hoadon.ma_hoa_don)
            put(HoaDon.CLM_NGAY_TAO_HOA_DON,hoadon.ngay_tao_hoa_don)
            put(HoaDon.CLM_TRANG_THAI_HOA_DON,hoadon.trang_thai_hoa_don)
            put(HoaDon.CLM_SO_DIEN,hoadon.so_dien)
            put(HoaDon.CLM_SO_NUOC,hoadon.so_nuoc)
            put(HoaDon.CLM_MIEN_GIAM,hoadon.mien_giam)
            put(HoaDon.CLM_MA_PHONG,hoadon.ma_phong)
            put(HoaDon.CLM_GIA_THUE,hoadon.gia_thue)
            put(HoaDon.CLM_GIA_DICH_VU,hoadon.gia_dich_vu)
            put(HoaDon.CLM_TONG,hoadon.tong)
        }
        return db.insert(HoaDon.TB_NAME,null,values)
    }
    fun update(hoadon:HoaDon):Int{
        val values=ContentValues()
        values.apply {
            put(HoaDon.CLM_TRANG_THAI_HOA_DON,hoadon.trang_thai_hoa_don)
        }
        return db.update(HoaDon.TB_NAME,values,"${HoaDon.CLM_MA_HOA_DON}=?", arrayOf(hoadon.ma_hoa_don))
    }

    @SuppressLint("Range")
    fun getAllInHoaDonByMaKhu(maKhu:String):List<HoaDon>{
        val list= mutableListOf<HoaDon>()
        val sql="""
            SELECT * FROM ${HoaDon.TB_NAME} JOIN ${Phong.TB_NAME} ON ${HoaDon.TB_NAME}.${HoaDon.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
            WHERE ${Phong.TB_NAME}.${Phong.CLM_MA_KHU}="$maKhu"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val hoadon=HoaDon(
                    ma_hoa_don = c.getString(c.getColumnIndex(HoaDon.CLM_MA_HOA_DON)),
                    ngay_tao_hoa_don = c.getString(c.getColumnIndex(HoaDon.CLM_NGAY_TAO_HOA_DON)),
                    trang_thai_hoa_don = c.getInt(c.getColumnIndex(HoaDon.CLM_TRANG_THAI_HOA_DON)),
                    so_dien = c.getInt(c.getColumnIndex(HoaDon.CLM_SO_DIEN)),
                    so_nuoc = c.getInt(c.getColumnIndex(HoaDon.CLM_SO_NUOC)),
                    gia_thue = c.getInt(c.getColumnIndex(HoaDon.CLM_GIA_THUE)),
                    gia_dich_vu = c.getInt(c.getColumnIndex(HoaDon.CLM_GIA_DICH_VU)),
                    mien_giam = c.getInt(c.getColumnIndex(HoaDon.CLM_MIEN_GIAM)),
                    ma_phong = c.getString(c.getColumnIndex(HoaDon.CLM_MA_PHONG)),
                    tong=c.getLong(c.getColumnIndex(HoaDon.CLM_TONG))
                )
                list+=hoadon
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getAllInHoaDonByDate(dateStart:String, dateEnd:String, maKhu:String, status:Int):Long{
        val list= mutableListOf<Long>()
        val sql="""
            select sum(${HoaDon.TB_NAME}.${HoaDon.CLM_TONG}) as tong from ${HoaDon.TB_NAME} 
            join ${Phong.TB_NAME} on ${HoaDon.TB_NAME}.${HoaDon.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
            where ${HoaDon.TB_NAME}.${HoaDon.CLM_NGAY_TAO_HOA_DON} BETWEEN ? AND ? AND ${Phong.TB_NAME}.${Phong.CLM_MA_KHU}="$maKhu" 
            and ${HoaDon.TB_NAME}.${HoaDon.CLM_TRANG_THAI_HOA_DON} = "$status"
        """.trimIndent()
        val c=db.rawQuery(sql, arrayOf(dateStart,dateEnd))
        if(c.moveToFirst()){
            do{
                try{
                    list.add(c.getLong(c.getColumnIndex(HoaDon.CLM_TONG)))
                }catch(e:Exception){
                    list.add(0)
                }
            }while(c.moveToNext())
        }
        return list[0].toLong()
    }
}