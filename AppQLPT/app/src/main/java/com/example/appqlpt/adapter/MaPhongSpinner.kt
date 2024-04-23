package com.example.appqlpt.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.appqlpt.R
import com.example.appqlpt.model.Phong

class MaPhongSpinner(reContext: Context, val list: List<Phong>): ArrayAdapter<Phong>(reContext,R.layout.layout_item_spinner_ma_phong){
    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }
    @SuppressLint("MissingInflatedId")

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View{
        val rowView : View = LayoutInflater.from(context).inflate(R.layout.layout_item_spinner_ma_phong,parent,false)
        val tvTenPhong = rowView.findViewById<TextView>(R.id.tvTenPhongSpinner)
        tvTenPhong.text = list[position].ten_phong
        return rowView
    }
}
