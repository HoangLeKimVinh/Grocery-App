package com.example.groceryapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryapp.R;
import com.example.groceryapp.adapter.CategoryAdapter;
import com.example.groceryapp.adapter.ProductAdapter;
import com.example.groceryapp.model.Category;
import com.example.groceryapp.model.Product;
import com.example.groceryapp.model.User;
import com.example.groceryapp.retrofit.ApiGrocery;
import com.example.groceryapp.retrofit.RetrofitClient;
import com.example.groceryapp.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    CategoryAdapter categoryAdapter;
    List<Category> mangcategory;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiGrocery apiGrocery;
    List<Product> mangproduct;
    ProductAdapter productAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;

    ImageView imgsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiGrocery = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiGrocery.class);
        Paper.init(this);
        if (Paper.book().read("user") !=null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }

        Anhxa();
        ActionBar();


        if (isConnected(this)){
            ActionViewFlipper();
            getCategory();
            getProduct();
            getEventClick();
        }else {
            Toast.makeText(getApplicationContext(), "khong co ket noi mang", Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent rautuoi = new Intent(getApplicationContext(), sanphamActivity.class);
                        rautuoi.putExtra("category_id",2);
                        startActivity(rautuoi);
                        break;
                    case 2:
                        Intent Traicay = new Intent(getApplicationContext(), sanphamActivity.class);
                        Traicay.putExtra("category_id",3);
                        startActivity(Traicay);
                        break;
                    case 7:
                        Intent donhang = new Intent(getApplicationContext(), XemDonActivity.class);
                        startActivity(donhang);
                        break;
                    case 8:
                        Paper.book().delete("user");
                        Intent dangxuat = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(dangxuat);
                        finish();
                        break;

                }
//                Category selectedCategory = mangcategory.get(i); // Lấy danh mục được chọn từ danh sách danh mục
//                int categoryId = selectedCategory.getId(); // Lấy ID của danh mục
//
//                Intent intent = new Intent(getApplicationContext(), sanphamActivity.class);
//                intent.putExtra("category_id", categoryId);
//                startActivity(intent);
            }
        });
    }

    private void getProduct(){
        compositeDisposable.add(apiGrocery.getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if (productModel.isSuccess()){
                                mangproduct = productModel.getResult();
                                productAdapter = new ProductAdapter(getApplicationContext(), mangproduct);
                                recyclerViewManHinhChinh.setAdapter(productAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không thể kết nối được với sever"+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getCategory(){
        compositeDisposable.add(apiGrocery.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryModel -> {
                            if(categoryModel.isSuccess()){
                                mangcategory = categoryModel.getResult();
                                mangcategory.add(0,new Category("Trang chủ", "https://c8.alamy.com/comp/T2XPTT/eco-house-green-home-icon-green-outline-isolated-vector-illustration-T2XPTT.jpg"));
                                mangcategory.add(new Category("Đơn hàng", "https://icon-library.com/images/inventory-icon-png/inventory-icon-png-4.jpg"));
                                mangcategory.add(new Category("Đăng xuất", "https://cdn-icons-png.flaticon.com/512/3351/3351670.png"));
                                //        Khoi tao adapter
                                categoryAdapter = new CategoryAdapter(getApplicationContext(), mangcategory);
                                listViewManHinhChinh.setAdapter(categoryAdapter);
                            }
                        }
                ));
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7bxZNNHfAeSOMxdcTomYGUXRgQIYw_lAK7thWxtAHTguR0CnruhRGGk_nsSUBRPd3p8g&usqp=CAU");
        mangquangcao.add("https://www.shutterstock.com/image-illustration/people-supermarket-process-buying-some-260nw-1205943802.jpg");
        mangquangcao.add("https://www.shutterstock.com/image-vector/grocery-shopping-discount-banner-paper-260nw-394204924.jpg");
        for (int i= 0; i<mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setInAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        imgsearch = findViewById(R.id.imgsearch);
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
//        Khoi tao mang
        mangcategory = new ArrayList<>();
        mangproduct = new ArrayList<>();

        if (Paper.book().read("giohang") != null){
            Utils.manggiohang = Paper.book().read("giohang");
        }
        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else {
            int totalItem = 0;
            for(int i = 0; i<Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for(int i = 0; i<Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi!= null && wifi.isConnected()) || (mobile !=null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}