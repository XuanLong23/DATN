package com.example.appqlpt.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.appqlpt.model.HopDong
import com.example.appqlpt.model.KhachThue
import com.example.appqlpt.model.KhuTro
import com.example.appqlpt.model.Phong

class KhachThueDao(context:Context) {
    val dbHelper=DBHelper(context)
    val db=dbHelper.writableDatabase

    fun insertKhachThue(khachthue:KhachThue):Long{
        val values=ContentValues()
        values.apply {
            put(KhachThue.CLM_MA_KHACH_THUE,khachthue.ma_khach_thue)
            put(KhachThue.CLM_HO_TEN_KHACH_THUE,khachthue.ho_ten)
            put(KhachThue.CLM_CCCD,khachthue.cccd)
            put(KhachThue.CLM_NAM_SINH,khachthue.nam_sinh)
            put(KhachThue.CLM_QUE_QUAN,khachthue.que_quan)
            put(KhachThue.CLM_SDT_KHACH_THUE,khachthue.sdt_khach_thue)
            put(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG,khachthue.trang_thai_chu_hop_dong)
            put(KhachThue.CLM_TRANG_THAI_O,khachthue.trang_thai_o)
            put(KhachThue.CLM_MA_PHONG,khachthue.ma_phong)
        }
        return db.insert(KhachThue.TB_NAME,null,values)
    }

    fun updateKhachThue(khachthue:KhachThue):Int{
        val values=ContentValues()
        values.apply {
            put(KhachThue.CLM_MA_KHACH_THUE,khachthue.ma_khach_thue)
            put(KhachThue.CLM_HO_TEN_KHACH_THUE,khachthue.ho_ten)
            put(KhachThue.CLM_CCCD,khachthue.cccd)
            put(KhachThue.CLM_NAM_SINH,khachthue.nam_sinh)
            put(KhachThue.CLM_QUE_QUAN,khachthue.que_quan)
            put(KhachThue.CLM_SDT_KHACH_THUE,khachthue.sdt_khach_thue)
            put(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG,khachthue.trang_thai_chu_hop_dong)
            put(KhachThue.CLM_TRANG_THAI_O,khachthue.trang_thai_o)
            put(KhachThue.CLM_MA_PHONG,khachthue.ma_phong)
        }
        return db.update(KhachThue.TB_NAME,values,"${KhachThue.CLM_MA_KHACH_THUE}=?", arrayOf(khachthue.ma_khach_thue))
    }

    @SuppressLint("Range")
    fun getAllInKhachThue():List<KhachThue>{
        val list= mutableListOf<KhachThue>()
        val sql="""
            SELECT * FROM ${KhachThue.TB_NAME}
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val khachthue=KhachThue(
                    ma_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_MA_KHACH_THUE)),
                    ho_ten = c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE)),
                    cccd = c.getString(c.getColumnIndex(KhachThue.CLM_CCCD)),
                    nam_sinh = c.getString(c.getColumnIndex(KhachThue.CLM_NAM_SINH)),
                    sdt_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_SDT_KHACH_THUE)),
                    que_quan = c.getString(c.getColumnIndex(KhachThue.CLM_QUE_QUAN)),
                    trang_thai_chu_hop_dong = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG)),
                    trang_thai_o = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_O)),
                    ma_phong = c.getString(c.getColumnIndex(KhachThue.CLM_MA_PHONG))
                )
                list+=khachthue
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getAllInKhachThueById(id:String):KhachThue?{
        val sql="""
            SELECT * FROM ${KhachThue.TB_NAME} WHERE ${KhachThue.CLM_MA_KHACH_THUE}="$id"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return KhachThue(
                ma_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_MA_KHACH_THUE)),
                ho_ten = c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE)),
                cccd = c.getString(c.getColumnIndex(KhachThue.CLM_CCCD)),
                nam_sinh = c.getString(c.getColumnIndex(KhachThue.CLM_NAM_SINH)),
                sdt_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_SDT_KHACH_THUE)),
                que_quan = c.getString(c.getColumnIndex(KhachThue.CLM_QUE_QUAN)),
                trang_thai_chu_hop_dong = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG)),
                trang_thai_o = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_O)),
                ma_phong = c.getString(c.getColumnIndex(KhachThue.CLM_MA_PHONG))
            )
        }
        return null
    }

    @SuppressLint("Range")
    fun getAllInKhachThueByTenPhong(id:String):KhachThue?{
        val sql="""
            SELECT * FROM ${KhachThue.TB_NAME} JOIN ${Phong.TB_NAME}
            ON ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
            WHERE ${Phong.CLM_TEN_PHONG}="$id"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return KhachThue(
                ma_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_MA_KHACH_THUE)),
                ho_ten = c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE)),
                cccd = c.getString(c.getColumnIndex(KhachThue.CLM_CCCD)),
                nam_sinh = c.getString(c.getColumnIndex(KhachThue.CLM_NAM_SINH)),
                sdt_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_SDT_KHACH_THUE)),
                que_quan = c.getString(c.getColumnIndex(KhachThue.CLM_QUE_QUAN)),
                trang_thai_chu_hop_dong = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG)),
                trang_thai_o = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_O)),
                ma_phong = c.getString(c.getColumnIndex(KhachThue.CLM_MA_PHONG))
            )
        }
        return null
    }

    @SuppressLint("Range")
    fun getAllInKhachThueByMaKhu(id:String):List<KhachThue>{
        val list= mutableListOf<KhachThue>()
        val sql="""
            SELECT * FROM ${KhachThue.TB_NAME} JOIN ${Phong.TB_NAME} 
            ON ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
            JOIN ${KhuTro.TB_NAME} ON ${Phong.TB_NAME}.${Phong.CLM_MA_KHU}=${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}
            WHERE ${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}="$id"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val khachthue=KhachThue(
                    ma_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_MA_KHACH_THUE)),
                    ho_ten = c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE)),
                    cccd = c.getString(c.getColumnIndex(KhachThue.CLM_CCCD)),
                    nam_sinh = c.getString(c.getColumnIndex(KhachThue.CLM_NAM_SINH)),
                    sdt_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_SDT_KHACH_THUE)),
                    que_quan = c.getString(c.getColumnIndex(KhachThue.CLM_QUE_QUAN)),
                    trang_thai_chu_hop_dong = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG)),
                    trang_thai_o = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_O)),
                    ma_phong = c.getString(c.getColumnIndex(KhachThue.CLM_MA_PHONG))
                )
                list+=khachthue
            }while(c.moveToNext())
        }
        return list
    }

    @SuppressLint("Range")
    fun getAllInPhongByMaKhu(maKhu:String):List<Phong>{
        val list= mutableListOf<Phong>()
        val sql="""
           select * from ${Phong.TB_NAME} where ${Phong.CLM_MA_KHU} = "$maKhu"
        """.trimIndent()
        val c=db.rawQuery(sql,null)

        if(c.moveToFirst()){
            do {
                val phong= Phong(
                    ma_phong = c.getString(c.getColumnIndex(Phong.CLM_MA_PHONG)),
                    ten_phong = c.getString(c.getColumnIndex(Phong.CLM_TEN_PHONG)),
                    dien_tich = c.getInt(c.getColumnIndex(Phong.CLM_DIEN_TICH)),
                    gia_thue = c.getLong(c.getColumnIndex(Phong.CLM_GIA_THUE)),
                    so_nguoi_o = c.getInt(c.getColumnIndex(Phong.CLM_SO_NGUOI_O)),
                    trang_thai_phong = c.getInt(c.getColumnIndex(Phong.CLM_TRANG_THAI_PHONG)),
                    ma_khu = c.getString(c.getColumnIndex(Phong.CLM_MA_KHU))
                )
                list+=phong
            }while (c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getKhachThueByMaPhong(maPhong:String):List<KhachThue>{
        val list= mutableListOf<KhachThue>()
        val sql="""
            SELECT * FROM ${KhachThue.TB_NAME} WHERE ${KhachThue.CLM_MA_PHONG}="$maPhong" AND ${KhachThue.CLM_TRANG_THAI_O}=1
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val khachthue=KhachThue(
                    ma_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_MA_KHACH_THUE)),
                    ho_ten = c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE)),
                    cccd = c.getString(c.getColumnIndex(KhachThue.CLM_CCCD)),
                    nam_sinh = c.getString(c.getColumnIndex(KhachThue.CLM_NAM_SINH)),
                    sdt_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_SDT_KHACH_THUE)),
                    que_quan = c.getString(c.getColumnIndex(KhachThue.CLM_QUE_QUAN)),
                    trang_thai_chu_hop_dong = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG)),
                    trang_thai_o = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_O)),
                    ma_phong = c.getString(c.getColumnIndex(KhachThue.CLM_MA_PHONG))
                )
                list+=khachthue
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getSoNguoiOByMaPhong(id:String):Int{
        val sql="""
            SELECT ${Phong.TB_NAME}.${Phong.CLM_SO_NGUOI_O} FROM ${Phong.TB_NAME}
            WHERE ${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}="$id"
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return c.getInt(c.getColumnIndex(Phong.CLM_SO_NGUOI_O))
        }
        return 0
    }
    @SuppressLint("Range")
    fun getListKhachThueByMaPhong(id:String):List<KhachThue>{
        val list= mutableListOf<KhachThue>()
        val sql="""
            SELECT * FROM ${KhachThue.TB_NAME} WHERE ${KhachThue.CLM_MA_PHONG}="$id" AND ${KhachThue.CLM_TRANG_THAI_O}=1
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val khachthue=KhachThue(
                    ma_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_MA_KHACH_THUE)),
                    ho_ten = c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE)),
                    cccd = c.getString(c.getColumnIndex(KhachThue.CLM_CCCD)),
                    nam_sinh = c.getString(c.getColumnIndex(KhachThue.CLM_NAM_SINH)),
                    sdt_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_SDT_KHACH_THUE)),
                    que_quan = c.getString(c.getColumnIndex(KhachThue.CLM_QUE_QUAN)),
                    trang_thai_chu_hop_dong = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG)),
                    trang_thai_o = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_O)),
                    ma_phong = c.getString(c.getColumnIndex(KhachThue.CLM_MA_PHONG))
                )
                list+=khachthue
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getAllInNguoiDangOByMaKhu(id:String):List<KhachThue>{
        val list= mutableListOf<KhachThue>()
        val sql="""
            SELECT * FROM ${KhachThue.TB_NAME} JOIN ${Phong.TB_NAME} 
                        ON ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
                        JOIN ${KhuTro.TB_NAME} ON ${Phong.TB_NAME}.${Phong.CLM_MA_KHU}=${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}
                        WHERE ${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}="$id" AND ${KhachThue.TB_NAME}.${KhachThue.CLM_TRANG_THAI_O}=1
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val khachthue=KhachThue(
                    ma_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_MA_KHACH_THUE)),
                    ho_ten = c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE)),
                    cccd = c.getString(c.getColumnIndex(KhachThue.CLM_CCCD)),
                    nam_sinh = c.getString(c.getColumnIndex(KhachThue.CLM_NAM_SINH)),
                    sdt_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_SDT_KHACH_THUE)),
                    que_quan = c.getString(c.getColumnIndex(KhachThue.CLM_QUE_QUAN)),
                    trang_thai_chu_hop_dong = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG)),
                    trang_thai_o = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_O)),
                    ma_phong = c.getString(c.getColumnIndex(KhachThue.CLM_MA_PHONG))
                )
                list+=khachthue
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getAllInNguoiDaOByMaKhu(id:String):List<KhachThue>{
        val list= mutableListOf<KhachThue>()
        val sql="""
            SELECT * FROM ${KhachThue.TB_NAME} JOIN ${Phong.TB_NAME} 
                        ON ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
                        JOIN ${KhuTro.TB_NAME} ON ${Phong.TB_NAME}.${Phong.CLM_MA_KHU}=${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}
                        WHERE ${KhuTro.TB_NAME}.${KhuTro.CLM_MA_KHU_TRO}="$id" AND ${KhachThue.TB_NAME}.${KhachThue.CLM_TRANG_THAI_O}=1
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            do{
                val khachthue=KhachThue(
                    ma_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_MA_KHACH_THUE)),
                    ho_ten = c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE)),
                    cccd = c.getString(c.getColumnIndex(KhachThue.CLM_CCCD)),
                    nam_sinh = c.getString(c.getColumnIndex(KhachThue.CLM_NAM_SINH)),
                    sdt_khach_thue = c.getString(c.getColumnIndex(KhachThue.CLM_SDT_KHACH_THUE)),
                    que_quan = c.getString(c.getColumnIndex(KhachThue.CLM_QUE_QUAN)),
                    trang_thai_chu_hop_dong = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_CHU_HOP_DONG)),
                    trang_thai_o = c.getInt(c.getColumnIndex(KhachThue.CLM_TRANG_THAI_O)),
                    ma_phong = c.getString(c.getColumnIndex(KhachThue.CLM_MA_PHONG))
                )
                list+=khachthue
            }while(c.moveToNext())
        }
        return list
    }
    @SuppressLint("Range")
    fun getTenNguoiDangOByMaPhong(id:String):String{
        val sql="""
            SELECT ${KhachThue.TB_NAME}.${KhachThue.CLM_HO_TEN_KHACH_THUE} FROM ${KhachThue.TB_NAME} JOIN ${HopDong.TB_NAME}
            ON ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_KHACH_THUE}=${HopDong.TB_NAME}.${HopDong.CLM_MA_KHACH_THUE}
            JOIN ${Phong.TB_NAME} ON ${HopDong.TB_NAME}.${HopDong.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
            WHERE ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_PHONG}="$id" AND ${KhachThue.TB_NAME}.${KhachThue.CLM_TRANG_THAI_O}=1
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return c.getString(c.getColumnIndex(KhachThue.CLM_HO_TEN_KHACH_THUE))
        }
        return "null"
    }
    @SuppressLint("Range")
    fun getMaNguoiDangOByMaPhong(id:String):String{
        val sql="""
            SELECT ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_KHACH_THUE} FROM ${KhachThue.TB_NAME} JOIN ${HopDong.TB_NAME}
            ON ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_KHACH_THUE}=${HopDong.TB_NAME}.${HopDong.CLM_MA_KHACH_THUE}
            JOIN ${Phong.TB_NAME} ON ${HopDong.TB_NAME}.${HopDong.CLM_MA_PHONG}=${Phong.TB_NAME}.${Phong.CLM_MA_PHONG}
            WHERE ${KhachThue.TB_NAME}.${KhachThue.CLM_MA_PHONG}="$id" AND ${KhachThue.TB_NAME}.${KhachThue.CLM_TRANG_THAI_O}=1
        """.trimIndent()
        val c=db.rawQuery(sql,null)
        if(c.moveToFirst()){
            return c.getString(c.getColumnIndex(KhachThue.CLM_MA_KHACH_THUE))
        }
        return "null"
    }
    fun updateTrangThaiKhachThueThanhDaO(maKhachThue:String):Int{
        val values=ContentValues()
        values.apply {
            put(KhachThue.CLM_TRANG_THAI_O,0)
        }
        return db.update(KhachThue.TB_NAME,values,"${KhachThue.CLM_MA_KHACH_THUE}=?", arrayOf(maKhachThue))
    }
}