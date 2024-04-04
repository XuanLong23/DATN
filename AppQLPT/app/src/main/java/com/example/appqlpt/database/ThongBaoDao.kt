package com.example.appqlpt.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.appqlpt.model.ThongBao

class ThongBaoDao(context:Context) {
    private val dbHelper=DBHelper(context)
    private val db=dbHelper.writableDatabase

    fun insertThongBao(thongbao: ThongBao):Long{
        val values=ContentValues()
        values.apply {
            put(ThongBao.CLM_MA_THONG_BAO,thongbao.ma_thong_bao)
            put(ThongBao.CLM_TIEU_DE,thongbao.tieu_de)
            put(ThongBao.CLM_NGAY_THONG_BAO,thongbao.ngay_thong_bao)
            put(ThongBao.CLM_NOI_DUNG,thongbao.noi_dung)
            put(ThongBao.CLM_MA_KHU,thongbao.ma_khu)
            put(ThongBao.CLM_LOAI_THONG_BAO,thongbao.loai_thong_bao)
        }
        return db.insert(ThongBao.TB_NAME,null,values)
    }
    @SuppressLint("Range")
    fun getAllInThongBaoByMaKhu(maKhu:String):List<ThongBao>{
        val list= mutableListOf<ThongBao>()
        val sql="""
            select*from ${ThongBao.TB_NAME} where ${ThongBao.CLM_MA_KHU} = "$maKhu" 
        """.trimIndent()
        val c=db.rawQuery(sql,null)

        if(c.moveToFirst()){
            do {
                val thongBao=ThongBao(
                    ma_thong_bao = c.getString(c.getColumnIndex(ThongBao.CLM_MA_THONG_BAO)),
                    tieu_de = c.getString(c.getColumnIndex(ThongBao.CLM_TIEU_DE)),
                    ngay_thong_bao = c.getString(c.getColumnIndex(ThongBao.CLM_NGAY_THONG_BAO)),
                    noi_dung = c.getString(c.getColumnIndex(ThongBao.CLM_NOI_DUNG)),
                    ma_khu = c.getString(c.getColumnIndex(ThongBao.CLM_MA_KHU)),
                    loai_thong_bao = c.getInt(c.getColumnIndex(ThongBao.CLM_LOAI_THONG_BAO))
                )
                list+=thongBao
            }while (c.moveToNext())
        }
        return list
    }
}