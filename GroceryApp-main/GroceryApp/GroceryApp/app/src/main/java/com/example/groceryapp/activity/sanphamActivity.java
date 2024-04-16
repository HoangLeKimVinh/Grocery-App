package com.example.groceryapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.groceryapp.R;
import com.example.groceryapp.adapter.SanphamAdapter;
import com.example.groceryapp.model.Category;
import com.example.groceryapp.model.Product;
import com.example.groceryapp.retrofit.ApiGrocery;
import com.example.groceryapp.retrofit.RetrofitClient;
import com.example.groceryapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class sanphamActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiGrocery apiGrocery;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page=1;
    int category_id;

    SanphamAdapter sanphamAdapter;
    List<Product> productList;


    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham);
        setContentView(R.layout.activity_sanpham);
        apiGrocery = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiGrocery.class);
        category_id = getIntent().getIntExtra("category_id", 1);
        Anhxa();
        ActionBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == productList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }
    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                productList.add(null);
                sanphamAdapter.notifyItemInserted(productList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                productList.remove(productList.size()-1);
                sanphamAdapter.notifyItemRemoved(productList.size());
                page = page+1;
                getData(page);
                sanphamAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }
    private void getData(int page) {
        compositeDisposable.add(apiGrocery.getSanPham(page,category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if(productModel.isSuccess()){
                                if(sanphamAdapter == null) {
                                    productList = productModel.getResult();
                                    sanphamAdapter = new SanphamAdapter(getApplicationContext(), productList);
                                    recyclerView.setAdapter(sanphamAdapter);
                                }else{
                                    int vitri = productList.size()-1;
                                    int soluongadd = productModel.getResult().size();
                                    for(int i=0;i<soluongadd; i++){
                                        productList.add(productModel.getResult().get(i));
                                    }
                                    sanphamAdapter.notifyItemRangeInserted(vitri,soluongadd);
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Hết dữ liệu", Toast.LENGTH_LONG).show();
                                isLoading = true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không thể kết nối", Toast.LENGTH_LONG).show();
                        }

                ));
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview_sp);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        productList = new ArrayList<>();
        
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}