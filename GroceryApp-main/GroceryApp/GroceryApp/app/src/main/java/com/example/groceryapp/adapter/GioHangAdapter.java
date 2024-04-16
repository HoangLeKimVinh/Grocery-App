package com.example.groceryapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryapp.Interface.ImageClickListenner;
import com.example.groceryapp.R;
import com.example.groceryapp.model.EventBus.TinhTongEvent;
import com.example.groceryapp.model.Giohang;
import com.example.groceryapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import io.paperdb.Paper;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<Giohang> giohangList;

    public GioHangAdapter(Context context, List<Giohang> giohangList){
        this.context = context;
        this.giohangList = giohangList;
    }
    @NonNull
    @Override
    public GioHangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.MyViewHolder holder, int position) {
        Giohang giohang = giohangList.get(position);
        holder.item_giohang_tensp.setText(giohang.getTensp());
        holder.item_giohang_soluong.setText(giohang.getSoluong() + " ");
        Glide.with(context).load(giohang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText("Giá: "+decimalFormat.format((giohang.getGiasp()))+"Đ");
        long gia = giohang.getSoluong() * giohang.getGiasp();
        holder.item_giohang_giasp2.setText("Tổng tiền: "+decimalFormat.format(gia)+"Đ");
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    Utils.manggiohang.get(holder.getBindingAdapterPosition()).setChecked(true);
                    if(!Utils.mangmuahang.contains(giohang)){
                        Utils.mangmuahang.add(giohang);
                    }

                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }else{
                    Utils.manggiohang.get(holder.getBindingAdapterPosition()).setChecked(false);
                    for(int i = 0; i<Utils.mangmuahang.size(); i++){
                        if(Utils.mangmuahang.get(i).getIdsp() == giohang.getIdsp()){
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }
                }
            }
        });

        holder.checkBox.setChecked(giohang.isChecked());

        holder.setListenner(new ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG", "onImageClick: " + pos + "..." +giatri);
                if (giatri == 1){
                    if (giohangList.get(pos).getSoluong()>1){
                        int soluongmoi = giohangList.get(pos).getSoluong()-1;
                        giohangList.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_soluong.setText(giohangList.get(pos).getSoluong()+ " ");
                        long gia = giohangList.get(pos).getSoluong() * giohangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText("Tổng tiền"+decimalFormat.format(gia) +"Đ");
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else if (giohangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.mangmuahang.remove(giohang);
                                Utils.manggiohang.remove(pos);
                                Paper.book().write("giohang", Utils.manggiohang);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();

                    }
                }else if (giatri == 2){
                    if (giohangList.get(pos).getSoluong() < 11){
                        int soluongmoi = giohangList.get(pos).getSoluong()+1;
                        giohangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(giohangList.get(pos).getSoluong()+ " ");
                    long gia = giohangList.get(pos).getSoluong() * giohangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText("Tổng tiền:" + decimalFormat.format(gia)+"Đ");
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return giohangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image,item_giohang_tru, item_giohang_cong;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
        ImageClickListenner listenner;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            item_giohang_tru = itemView.findViewById(R.id.item_giohang_tru);
            item_giohang_cong = itemView.findViewById(R.id.item_giohang_cong);
            checkBox = itemView.findViewById(R.id.item_giohang_check);

//            event click
            item_giohang_cong.setOnClickListener(this);
            item_giohang_tru.setOnClickListener(this);
        }

        public void setListenner(ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == item_giohang_tru){
                listenner.onImageClick(view, getBindingAdapterPosition(), 1);
            }else if (view == item_giohang_cong){
                listenner.onImageClick(view, getBindingAdapterPosition(), 2);
            }
        }
    }
}
