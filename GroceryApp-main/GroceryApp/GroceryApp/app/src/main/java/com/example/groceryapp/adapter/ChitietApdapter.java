package com.example.groceryapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryapp.R;
import com.example.groceryapp.model.Item;

import java.util.List;

public class ChitietApdapter extends RecyclerView.Adapter<ChitietApdapter.MyViewHolder> {
    Context context;
    List<Item> itemList;

    public ChitietApdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ChitietApdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChitietApdapter.MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtten.setText(item.getProduct_name()+ "");
        holder.txtsoluong.setText("Số lượng: "+item.getSoluong()+ "");
        holder.txtgia.setText("Giá: "+item.getGia()+ "");
        Glide.with(context).load(item.getProduct_img()).into(holder.imagechitiet);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagechitiet;
        TextView txtten, txtsoluong, txtgia;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagechitiet = itemView.findViewById(R.id.item_imgchitiet);
            txtten = itemView.findViewById(R.id.item_tenspchitiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);
            txtgia  = itemView.findViewById(R.id.item_giachitiet);
        }
    }
}
