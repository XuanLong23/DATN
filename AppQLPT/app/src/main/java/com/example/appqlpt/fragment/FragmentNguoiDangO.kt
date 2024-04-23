package com.example.appqlpt.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appqlpt.activity.ActivityCapNhatKhachThue
import com.example.appqlpt.adapter.*
import com.example.appqlpt.database.KhachThueDao
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.DialogThemKhachThueBinding
import com.example.appqlpt.databinding.FragmentNguoiDangOBinding
import com.example.appqlpt.model.KhachThue
import java.util.*


class FragmentNguoiDangO: Fragment() {
    private lateinit var binding: FragmentNguoiDangOBinding
    private var maKhu=""
    private var maPhong=""
    var listNguoiDung= listOf<KhachThue>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNguoiDangOBinding.inflate(LayoutInflater.from(context))

        // Inflate the layout for this fragment
        val srf = binding.root.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        maKhu = srf.getString(MA_KHU_KEY, "")!!
//        listNguoiDung=NguoiDungDao(requireActivity()).getAllInNguoiDungByMaKhu(maKhu)
        listNguoiDung=KhachThueDao(requireActivity()).getAllInNguoiDangOByMaKhu(maKhu)
//        Toast.makeText(activity, maKhu, Toast.LENGTH_SHORT).show()
        //search
//        binding.rcyNguoiDangO.setOn
        binding.searchTenPhong.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var list = KhachThueDao(binding.root.context).getAllInKhachThueByMaKhu(maKhu)
                if(newText!=null){
                    list= KhachThue.timkiemKhach(list,newText)
                }
                val adapter = NguoiThueAdapter(list,object :KhachThueInterface{
                    override fun OnClickKhachThue(pos: Int) {
                        val nguoiDung = listNguoiDung.get(pos)
                        val intent = Intent(requireContext(), ActivityCapNhatKhachThue::class.java)
                        intent.putExtra("khachThue",nguoiDung)
                        startActivity(intent)
                    }

                })
                binding.rcyNguoiDangO.adapter = adapter
                return false

            }

        })

        //search
        binding.imgAddNguoiThue.setOnClickListener {
            val dialog=DialogThemKhachThueBinding.inflate(LayoutInflater.from(activity))
            val build=AlertDialog.Builder(activity).create()
            val listPhong=PhongDao(requireActivity()).getAllInPhongByMaKhu(maKhu)
            val spinner=MaPhongSpinner(requireActivity(), listPhong)
            dialog.spinnerThemNguoiDung.adapter=spinner
            dialog.spinnerThemNguoiDung.onItemSelectedListener=object : OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    maPhong=listPhong[position].ma_phong
//                    Toast.makeText(activity, maPhong, Toast.LENGTH_SHORT).show()
                    Toast.makeText(activity, "Tên phòng: "+listPhong[position].ten_phong, Toast.LENGTH_SHORT).show()

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            dialog.btnLuuThemNguoiDung.setOnClickListener {
                if(dialog.edHoTenThemNguoiDung.text.toString().isNotBlank()&&dialog.edNgaySinhThemNguoiDung.text.toString().isNotBlank()
                    &&dialog.edSDTThemNguoiDung.text.toString().isNotBlank()&&dialog.edCCCDThemNguoiDung.text.toString().isNotBlank()
                    &&dialog.edQueQuanThemNguoiDung.text.toString().isNotBlank()){
                    //check ho ten
//                    val hoTen =  dialog.edHoTenThemNguoiDung.text.toString()
//                    val kiemTraHoTen = ("^[a-zA-Z]")
//                    val pattern3 = Pattern.compile(kiemTraHoTen)
//                    val matcher3 = pattern3.matcher(hoTen)
//                    if(!matcher3.find()){
//                        thongBaoLoi("Nhập lại họ tên khách thuê")
//                        return@setOnClickListener
//
//                    }
//                    ///kiem tra ngay
//                    try{
//                        val spf = SimpleDateFormat("dd/MM/yyyy")
//                        val objDate:Date = spf.parse(dialog.edNgaySinhThemNguoiDung.text.toString().trim())
//                        val dateFormat = android.text.format.DateFormat.format("dd-MM-yyyy",objDate)
//                        val ngay: String = dateFormat.toString()
////                        val nguoiDung:NguoiDung
//////                        nguoiDung.nam_sinh(ngay)
//
//                    }catch (e:java.lang.Exception){
//                        thongBaoLoi("Ngày sinh không đúng định dạng dd/MM/yyyy")
//                        return@setOnClickListener
//                    }
//                    //check sdt
//                    val sdt =  dialog.edSDTThemNguoiDung.text.toString()
//                    val kiemTraSDT = ("^(0)+[0-9]{9}$")
//                    val pattern1 = Pattern.compile(kiemTraSDT)
//                    val matcher1 = pattern1.matcher(sdt)
//                    if(!matcher1.find()){
//                        thongBaoLoi("Nhập lại số điện thoại khách thuê")
//                        return@setOnClickListener
//
//                    }
//                    //check cccd
//                    val cccd =  dialog.edCCCDThemNguoiDung.text.toString()
//                    val kiemTraCCCD = ("^(0)+[0-9]{11}$")
//                    val pattern2 = Pattern.compile(kiemTraCCCD)
//                    val matcher2 = pattern2.matcher(cccd)
//                    if(!matcher2.find()){
//                        thongBaoLoi("Nhập lại số cccd khách thuê")
//                        return@setOnClickListener
//
//                    }
                    val listNguoiDungByMaPhong = KhachThueDao(requireActivity()).getListKhachThueByMaPhong(maPhong)
                    val soNguoiO = KhachThueDao(requireActivity()).getSoNguoiOByMaPhong(maPhong)

                    if(listNguoiDungByMaPhong.size<soNguoiO || soNguoiO==0){
                        themNguoiDung(dialog,build,it)
                    }else{
                        thongBaoLoi("Phòng đã đủ người")
                    }


                }else{
                    thongBaoLoi("Dữ liệu không được để trống!!!")
                }


            }
            dialog.btnHuyThemNguoiDung.setOnClickListener {
                build.dismiss()
                onResume()
            }

            build.setView(dialog.root)
            build.show()
            onPause()
        }

        return  binding.root
    }

    private fun themNguoiDung(dialog: DialogThemKhachThueBinding, build: AlertDialog, view: View) {
        val maNguoiDung = UUID.randomUUID().toString()
        val nguoiDung = KhachThue(
            ma_khach_thue = maNguoiDung,
            ho_ten = dialog.edHoTenThemNguoiDung.text.toString(),
            nam_sinh = dialog.edNgaySinhThemNguoiDung.text.toString(),
            sdt_khach_thue = dialog.edSDTThemNguoiDung.text.toString(),
            que_quan = dialog.edQueQuanThemNguoiDung.text.toString(),
            cccd = dialog.edCCCDThemNguoiDung.text.toString(),
            trang_thai_chu_hop_dong = 0,
            trang_thai_o = 1,
            ma_phong = maPhong
        )
        val dao = KhachThueDao(dialog.root.context).insertKhachThue(nguoiDung)
        val updatePhong = PhongDao(binding.root.context).updateTrangThaiPhongThanhDangO(maPhong)
        if (dao > 0 && updatePhong >0) {
            thongBaoThanhCong("Thêm người dùng thành công",build)
            //Snackbar.make(view, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show()
        } else {
            //Snackbar.make(view, "Thêm không thành công", Toast.LENGTH_SHORT).show()
           thongBaoLoi("Thêm người dùng không thành công")
        }
        ///
//                        dialog.edHoTenThemNguoiDung.setText("")
//                        dialog.edSDTThemNguoiDung.setText("")
//                        dialog.edCCCDThemNguoiDung.setText("")
//                        dialog.edNgaySinhThemNguoiDung.setText("")
//                        dialog.edQueQuanThemNguoiDung.setText("")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nguoiThueAdapter = NguoiThueAdapter(listNguoiDung,object :KhachThueInterface{
            override fun OnClickKhachThue(pos: Int) {
                val nguoiDung = listNguoiDung.get(pos)
                val intent = Intent(requireContext(), ActivityCapNhatKhachThue::class.java)
                intent.putExtra("khachThue",nguoiDung)
                startActivity(intent)
            }

        })
        binding.rcyNguoiDangO.adapter = nguoiThueAdapter
        binding.rcyNguoiDangO.layoutManager = LinearLayoutManager(activity)
    }
    fun thongBaoLoi(loi : String){
        val bundle = androidx.appcompat.app.AlertDialog.Builder(binding.root.context)
        bundle.setTitle("Thông Báo Lỗi")
        bundle.setMessage(loi)
        bundle.setNegativeButton("Ok", DialogInterface.OnClickListener { dialog, which ->

            dialog.cancel()
        })
        bundle.show()
    }
    override fun onResume() {
        super.onResume()
        reload()
    }
    private fun reload(){
        val nguoiDungDao= activity?.let { KhachThueDao(it) }!!
//        listNguoiDung=nguoiDungDao.getAllInNguoiDungByMaKhu(maKhu)
        listNguoiDung=nguoiDungDao.getAllInNguoiDangOByMaKhu(maKhu)
        val nguoiThueAdapter=NguoiThueAdapter(listNguoiDung,object :KhachThueInterface{
            override fun OnClickKhachThue(pos: Int) {
                val nguoiDung = listNguoiDung.get(pos)
                val intent = Intent(requireContext(), ActivityCapNhatKhachThue::class.java)
                intent.putExtra("khachThue",nguoiDung)
                startActivity(intent)
            }

        })
        binding.rcyNguoiDangO.adapter=nguoiThueAdapter
        binding.rcyNguoiDangO.layoutManager=LinearLayoutManager(context)
    }
    fun thongBaoThanhCong(loi: String, build: AlertDialog){
        val bundle = androidx.appcompat.app.AlertDialog.Builder(binding.root.context)
        bundle.setTitle("Thông Báo")
        bundle.setMessage(loi)
        bundle.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
            build.dismiss()
            onResume()
//            onResume()

        })
//        bundle.setPositiveButton("Hủy", DialogInterface.OnClickListener { dialog, which ->
//            dialog.cancel()
//        })
        bundle.show()
    }
//    private fun updateNDTrongPhong(ma:Phong):Int {
//        val update = NguoiDungDao(binding.root.context).updateNguoiDung(nguoiDungNew)
//        return update
//    }

}