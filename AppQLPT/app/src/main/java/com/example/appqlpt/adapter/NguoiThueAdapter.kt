package com.example.appqlpt.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.appqlpt.database.KhachThueDao
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.example.appqlpt.database.PhongDao
import com.example.appqlpt.databinding.DialogChiTietNguoiThueBinding
import com.example.appqlpt.databinding.LayoutItemNguoiThueBinding
import com.example.appqlpt.databinding.LayoutItemPhongBinding
import com.example.appqlpt.model.KhachThue
import com.example.appqlpt.model.Phong

const val MA_PHONG_NGUOI_DUNG_KEY="ma phong de lay hoa don"
class NguoiThueViewHolder(
    val binding:LayoutItemNguoiThueBinding

):RecyclerView.ViewHolder(binding.root){
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(nguoiDung: KhachThue){

       binding.tvTenPhong.text =PhongDao(binding.root.context).getTenPhongById(nguoiDung.ma_phong)
        binding.tvMaNguoiDung.text = nguoiDung.ma_khach_thue
        binding.tvSDT.text = "SĐT: "+nguoiDung.sdt_khach_thue.toString()
        binding.tvTenNguoiThue.text = "Họ tên: " +nguoiDung.ho_ten.toString()
        binding.edTrangThaiO.isChecked = nguoiDung.trang_thai_o==1
//        if(binding.tvTenNguoiThue.text=="Họ tên: "+NguoiDungDao(binding.root.context).getTenNguoiDangOByMaPhong(nguoiDung.ma_phong)){
//            binding.edTrangThaiChuHopDong.isChecked = true
//        }else{
//            binding.edTrangThaiChuHopDong.isChecked = false
//        }

        if(binding.tvMaNguoiDung.text== KhachThueDao(binding.root.context).getMaNguoiDangOByMaPhong(nguoiDung.ma_phong)){
            binding.edTrangThaiChuHopDong.isChecked = true
        }else{
            binding.edTrangThaiChuHopDong.isChecked = false
        }
        binding.layoutPhone.setOnClickListener {
            goiDien(nguoiDung.sdt_khach_thue,binding.root.context)
        }
        binding.layoutMessage.setOnClickListener {
            nhanTin(nguoiDung.sdt_khach_thue,"",binding.root.context)
        }
//
//        if(binding.tvSDT.text=="SĐT: "+NguoiDungDao(binding.root.context).getSDTNguoiDangOByMaPhong(nguoiDung.ma_phong)){
//            binding.edTrangThaiChuHopDong.isChecked = true
//        }else{
//            binding.edTrangThaiChuHopDong.isChecked = false
//        }
//        if(binding.tvTenNguoiThue.text == NguoiDungDao(binding.root.context).getNguoiDungByTrangThai(nguoiDung.ma_phong,1)){

////  ||binding.tvTenNguoiThue.text=="Họ tên: "+NguoiDungDao(binding.root.context).getTenNguoiDaOByMaPhong(nguoiDung.ma_phong)          Log.d("aaaa", "getNguoiDungByTrangThai: "+NguoiDungDao(binding.root.context).getNguoiDungByTrangThai(nguoiDung.ma_phong,1))
//        Toast.makeText(binding.root.context, NguoiDungDao(binding.root.context).getTenNguoiDaOByMaPhong(nguoiDung.ma_phong), Toast.LENGTH_SHORT).show()
        binding.layoutChuyenChiTietNguoiThue.setOnClickListener {
//            val dialog = DialogChiTietNguoiThueBinding.inflate(LayoutInflater.from(binding.root.context))
//            val builder = AlertDialog.Builder(binding.root.context).create()
            val dialog = DialogChiTietNguoiThueBinding.inflate(LayoutInflater.from(binding.root.context))

            var bottomSheetDialog: BottomSheetDialog
            bottomSheetDialog = BottomSheetDialog(binding.root.context)

            bottomSheetDialog.setContentView(dialog.root)
            dialog.tvChiTietNguoiDungTenPhong.text = "Tên phòng: "+PhongDao(binding.root.context).getTenPhongById(nguoiDung.ma_phong)
            dialog.tvChiTietNguoiThueHoTen.text = nguoiDung.ho_ten
            dialog.tvChiTietNguoiThueSDT.text = nguoiDung.sdt_khach_thue
            dialog.tvChiTietNguoiThueNgaySinh.text = nguoiDung.nam_sinh
            dialog.tvChiTietNguoiThueCCCD.text = nguoiDung.cccd
            dialog.tvChiTietNguoiThueQueQuan.text = nguoiDung.que_quan
            if(binding.tvTenNguoiThue.text=="Họ tên: "+KhachThueDao(binding.root.context).getTenNguoiDangOByMaPhong(nguoiDung.ma_phong)){
                dialog.tvLoaiNguoiThue.text = "Là chủ hợp đồng"
            }else{
                dialog.tvLoaiNguoiThue.text = "Là thành viên"
            }
            dialog.btnDongChiTietNguoiThue.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
//            dialog.btnCapNhatChiTietNguoiThue.setOnClickListener {
//                ///cap nhat vao nhe!!!!
////                truyenDuLieu(binding.root.context,nguoiDung.ma_phong)
//                truyenDuLieu(binding.root.context)
//                bottomSheetDialog.dismiss()
//            }

            bottomSheetDialog.show()

        }

    }
}
class NguoiThueAdapter(
    val listNguoiDung: List<KhachThue>, val onClick:KhachThueInterface
):RecyclerView.Adapter<NguoiThueViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NguoiThueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemNguoiThueBinding.inflate(inflater,parent,false)
        return NguoiThueViewHolder(binding)
    }

    override fun getItemCount()=listNguoiDung.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NguoiThueViewHolder, position: Int) {
        val user = listNguoiDung[position]
        holder.apply {
            bind(user)
        }
        holder.itemView.setOnClickListener {

           onClick.OnClickKhachThue(position)
        }
    }
    fun nhanTin(sdt:String, message:String, context: Context){

        val uri = Uri.parse("smsto:+$sdt")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        with(intent) {
            putExtra("address", "+$sdt")
            putExtra("sms_body", message)
        }
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context)
                if (defaultSmsPackageName != null) intent.setPackage(defaultSmsPackageName)
                context.startActivity(intent)
            }
            else -> context.startActivity(intent)
        }
    }

}

fun nhanTin(sdt:String, message:String, context: Context){

    val uri = Uri.parse("smsto:+$sdt")
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    with(intent) {
        putExtra("address", "+$sdt")
        putExtra("sms_body", message)
    }
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
            val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context)
            if (defaultSmsPackageName != null) intent.setPackage(defaultSmsPackageName)
            context.startActivity(intent)
        }
        else -> context.startActivity(intent)
    }
}
fun goiDien(sdt:String, context:Context){
    val dialIntent = Intent(Intent.ACTION_DIAL)
    dialIntent.data = Uri.parse("tel:$sdt")
    context.startActivity(dialIntent)
}
//class DanhSachPhongAdapter(
//    val list:List<Phong>
//): RecyclerView.Adapter<DanhSachPhongViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DanhSachPhongViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = LayoutItemPhongBinding.inflate(inflater,parent,false)
//        return DanhSachPhongViewHolder(binding)
//    }
//
//    override fun getItemCount()= list.size
//
//    override fun onBindViewHolder(holder: DanhSachPhongViewHolder, position: Int) {
//        val user = list[position]
//        holder.bind(user)
//    }
//}