package com.example.appqlpt.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.appqlpt.model.HopDong
import com.example.appqlpt.model.KhachThue
import com.example.appqlpt.model.KhuTro
import com.example.appqlpt.model.Phong

class HopDongDao(context: Context) {
    val dbHelper=DBHelper(context)
    val db=dbHelper.writableDatabase

    fun insertHopDong(hopdong:HopDong):Long{
        val values=ContentValues()
        values.apply {
            put(HopDong.CLM_MA_HOP_DONG,hopdong.ma_hop_dong)
            put(HopDong.CLM_THOI_HAN,hopdong.thoi_han)
            put(HopDong.CLM_NGAY_O,hopdong.ngay_o)
            put(HopDong.CLM_NGAY_HOP_DONG,hopdong.ngay_hop_dong)
            put(HopDong.CLM_NGAY_LAP_HOP_DONG,hopdong.ngay_lap_hop_dong)
            put(HopDong.CLM_ANH_HOP_DONG,hopdong.anh_hop_dong)
            put(HopDong.CLM_TIEN_COC,hopdong.tien_coc)
            put(HopDong.CLM_TRANG_THAI_HOP_DONG,hopdong.trang_thai_hop_dong)
            put(HopDong.CLM_HIEU_LUC_HOP_DONG,hopdong.hieu_luc_hop_dong)
            put(HopDong.CLM_MA_PHONG,hopdong.ma_phong)
            put(HopDong.CLM_MA_KHACH_THUE,hopdong.ma_khach_thue)
        }
        return db.insert(HopDong.TB_NAME,null,values)
    }
    fun updateHopDong(hopdong: HopDong):Int{
        val values=ContentValues()
        values.apply {
            put(HopDong.CLM_MA_HOP_DONG,hopdong.ma_hop_dong)
            put(HopDong.CLM_THOI_HAN,hopdong.thoi_han)
            put(HopDong.CLM_NGAY_O,hopdong.ngay_o)
            put(HopDong.CLM_NGAY_HOP_DONG,hopdong.ngay_hop_dong)
            put(HopDong.CLM_NGAY_LAP_HOP_DONG,hopdong.ngay_lap_hop_dong)
            put(HopDong.CLM_ANH_HOP_DONG,hopdong.anh_hop_dong)
            put(HopDong.CLM_TIEN_COC,hopdong.tien_coc)
            put(HopDong.CLM_TRANG_THAI_HOP_DONG,hopdong.trang_thai_hop_dong)
            put(HopDong.CLM_HIEU_LUC_HOP_DONG,hopdong.hieu_luc_hop_dong)
            put(HopDong.CLM_MA_PHONG,hopdong.ma_phong)
            put(HopDong.CLM_MA_KHACH_THUE,hopdong.ma_khach_thue)
        }
        return db.update(HopDong.TB_NAME,values,"${HopDong.CLM_MA_HOP_DONG}=?", arrayOf(hopdong.ma_hop_dong))
    }
    @SuppressLint("Range")
    fun getAllInHopDongById(id:String):HopDong?{
        val sql="""
            SELECT * FROM ${HopDong.TB_NAME} WHERE ${HopDong.CLM_MA_HOP_DONG}="$id"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return HopDong(
                ma_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_MA_HOP_DONG)),
                thoi_han = c.getInt(c.getColumnIndex(HopDong.CLM_THOI_HAN)),
                ngay_o = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_O)),
                ngay_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_HOP_DONG)),
                ngay_lap_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_LAP_HOP_DONG)),
                anh_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_ANH_HOP_DONG)),
                tien_coc = c.getInt(c.getColumnIndex(HopDong.CLM_TIEN_COC)),
                trang_thai_hop_dong = c.getInt(c.getColumnIndex(HopDong.CLM_TRANG_THAI_HOP_DONG)),
                hieu_luc_hop_dong = c.getInt(c.getColumnIndex(HopDong.CLM_HIEU_LUC_HOP_DONG)),
                ma_phong = c.getString(c.getColumnIndex(HopDong.CLM_MA_PHONG)),
                ma_khach_thue = c.getString(c.getColumnIndex(HopDong.CLM_MA_KHACH_THUE))
            )
        }
        return null
    }
    @SuppressLint("Range")
    fun getAllInHopDong():List<HopDong>{
        val list= mutableListOf<HopDong>()
        val sql="""
            SELECT * FROM ${HopDong.TB_NAME}
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val hopdong=HopDong(
                    ma_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_MA_HOP_DONG)),
                    thoi_han = c.getInt(c.getColumnIndex(HopDong.CLM_THOI_HAN)),
                    ngay_o = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_O)),
                    ngay_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_HOP_DONG)),
                    ngay_lap_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_LAP_HOP_DONG)),
                    anh_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_ANH_HOP_DONG)),
                    tien_coc = c.getInt(c.getColumnIndex(HopDong.CLM_TIEN_COC)),
                    trang_thai_hop_dong = c.getInt(c.getColumnIndex(HopDong.CLM_TRANG_THAI_HOP_DONG)),
                    hieu_luc_hop_dong = c.getInt(c.getColumnIndex(HopDong.CLM_HIEU_LUC_HOP_DONG)),
                    ma_phong = c.getString(c.getColumnIndex(HopDong.CLM_MA_PHONG)),
                    ma_khach_thue = c.getString(c.getColumnIndex(HopDong.CLM_MA_KHACH_THUE))
                )
                list+=hopdong
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getTenPhongByIDHopDong(id:String):String{
        val sql="""
            SELECT ${Phong.TB_NAME}.${Phong.CLM_TEN_PHONG} FROM ${Phong.TB_NAME}
            JOIN ${HopDong.TB_NAME} on ${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}=${HopDong.TB_NAME}.${HopDong.CLM_MA_PHONG}
            WHERE ${HopDong.TB_NAME}.${HopDong.CLM_MA_HOP_DONG}="$id"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return c.getString(c.getColumnIndex(Phong.CLM_TEN_PHONG))
        }
        return "null"
    }
    @SuppressLint("Range")
    fun getTenKhachThueByIDHopDong(id:String):String{
        val sql="""
            SELECT ${KhachThue.TB_NAME}.${KhachThue.CLM_HO_TEN_KHACH_THUE} FROM ${KhachThue.TB_NAME}
            JOIN ${HopDong.TB_NAME} on ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_KHACH_THUE}=${HopDong.TB_NAME}.${HopDong.CLM_MA_KHACH_THUE}
            WHERE ${HopDong.TB_NAME}.${HopDong.CLM_MA_HOP_DONG}="$id"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE))
        }
        return "null"
    }
    @SuppressLint("Range")
    fun getAllInHopDongByMaKhu(id:String, hieuluc:Int):List<HopDong>{
        val list= mutableListOf<HopDong>()
        val sql="""
            SELECT * FROM ${HopDong.TB_NAME} JOIN ${Phong.TB_NAME} ON ${HopDong.TB_NAME}.${HopDong.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
            JOIN ${KhuTro.TB_NAME} ON ${Phong.TB_NAME}.${Phong.CLM_MA_KHU}=${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}
            WHERE ${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}="$id" AND ${HopDong.TB_NAME}.${HopDong.CLM_HIEU_LUC_HOP_DONG}=$hieuluc
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val hopdong=HopDong(
                    ma_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_MA_HOP_DONG)),
                    thoi_han = c.getInt(c.getColumnIndex(HopDong.CLM_THOI_HAN)),
                    ngay_o = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_O)),
                    ngay_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_HOP_DONG)),
                    ngay_lap_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_LAP_HOP_DONG)),
                    anh_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_ANH_HOP_DONG)),
                    tien_coc = c.getInt(c.getColumnIndex(HopDong.CLM_TIEN_COC)),
                    trang_thai_hop_dong = c.getInt(c.getColumnIndex(HopDong.CLM_TRANG_THAI_HOP_DONG)),
                    hieu_luc_hop_dong = c.getInt(c.getColumnIndex(HopDong.CLM_HIEU_LUC_HOP_DONG)),
                    ma_phong = c.getString(c.getColumnIndex(HopDong.CLM_MA_PHONG)),
                    ma_khach_thue = c.getString(c.getColumnIndex(HopDong.CLM_MA_KHACH_THUE))
                )
                list+=hopdong
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getHopDongSapHetHanByMaKhu(maKhu:String, trangthai:Int, hieuluc:Int):List<HopDong>{
        val list= mutableListOf<HopDong>()
        val sql="""
            SELECT * FROM ${HopDong.TB_NAME} JOIN ${Phong.TB_NAME} ON ${HopDong.TB_NAME}.${HopDong.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
            JOIN ${KhuTro.TB_NAME} ON ${Phong.TB_NAME}.${Phong.CLM_MA_KHU}=${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}
            WHERE ${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}="$maKhu" AND ${HopDong.TB_NAME}.${HopDong.CLM_HIEU_LUC_HOP_DONG}=$hieuluc AND ${HopDong.TB_NAME}.${HopDong.CLM_TRANG_THAI_HOP_DONG}=$trangthai
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val hopdong=HopDong(
                    ma_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_MA_HOP_DONG)),
                    thoi_han = c.getInt(c.getColumnIndex(HopDong.CLM_THOI_HAN)),
                    ngay_o = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_O)),
                    ngay_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_HOP_DONG)),
                    ngay_lap_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_NGAY_LAP_HOP_DONG)),
                    anh_hop_dong = c.getString(c.getColumnIndex(HopDong.CLM_ANH_HOP_DONG)),
                    tien_coc = c.getInt(c.getColumnIndex(HopDong.CLM_TIEN_COC)),
                    trang_thai_hop_dong = c.getInt(c.getColumnIndex(HopDong.CLM_TRANG_THAI_HOP_DONG)),
                    hieu_luc_hop_dong = c.getInt(c.getColumnIndex(HopDong.CLM_HIEU_LUC_HOP_DONG)),
                    ma_phong = c.getString(c.getColumnIndex(HopDong.CLM_MA_PHONG)),
                    ma_khach_thue = c.getString(c.getColumnIndex(HopDong.CLM_MA_KHACH_THUE))
                )
                list+=hopdong
            }while(c.moveToNext())
        }
        return list
    }
}