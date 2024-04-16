package com.example.groceryapp.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.groceryapp.R;
import com.example.groceryapp.model.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    List<Category> array;
    Context context;

    public CategoryAdapter(Context context,List<Category> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView textcatname;
        ImageView imghinhanh;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_category,null);
            viewHolder.textcatname = view.findViewById(R.id.item_name);
            viewHolder.imghinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.textcatname.setText(array.get(i).getCategory_name());
        Glide.with(context).load(array.get(i).getCategory_image()).into(viewHolder.imghinhanh);

        return view;
    }
}
