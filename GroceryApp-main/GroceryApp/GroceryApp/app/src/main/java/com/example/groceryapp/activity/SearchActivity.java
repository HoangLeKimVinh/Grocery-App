package com.example.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groceryapp.R;
import com.example.groceryapp.adapter.SanphamAdapter;
import com.example.groceryapp.model.Product;
import com.example.groceryapp.retrofit.ApiGrocery;
import com.example.groceryapp.retrofit.RetrofitClient;
import com.example.groceryapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    SanphamAdapter adaptersp;
    List<Product> productList;
    ApiGrocery apiGrocery;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText edtsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        ActionBar();
    }

    private void initView() {
        productList = new ArrayList<>();
        apiGrocery = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiGrocery.class);
        edtsearch = findViewById(R.id.edtsearch);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    productList.clear();
                    adaptersp = new SanphamAdapter(getApplicationContext(), productList);
                    recyclerView.setAdapter(adaptersp);
                }else {
                    getDataSearch(charSequence.toString());
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getDataSearch(String s) {
        productList.clear();
        compositeDisposable.add(apiGrocery.search(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productModel -> {
                            if (productModel.isSuccess()){
                                productList = productModel.getResult();
                                adaptersp = new SanphamAdapter(getApplicationContext(), productList);
                                recyclerView.setAdapter(adaptersp);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();


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

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}