package com.example.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groceryapp.R;
import com.example.groceryapp.retrofit.ApiGrocery;
import com.example.groceryapp.retrofit.RetrofitClient;
import com.example.groceryapp.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKyActivity extends AppCompatActivity {
    EditText fullName,email,phone,password,repassword;
    Button signUpBtn;

    ApiGrocery apiGrocery;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        initView();
        initControl();
    }

    private void initControl() {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }
        });
    }

    private void dangKy() {
        String str_fullName = fullName.getText().toString().trim();
        String str_email = email.getText().toString().trim();
        String str_pass = password.getText().toString().trim();
        String str_repass = repassword.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        if (TextUtils.isEmpty(str_fullName)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập username", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập pass", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(str_repass)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập repass", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(str_phone)){
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập phone", Toast.LENGTH_LONG).show();
        } else {
            if (str_pass.equals(str_repass)){
                compositeDisposable.add(apiGrocery.dangKy(str_email,str_pass, str_fullName, str_phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()){
                                        Utils.user_current.setEmail(str_email);
                                        Utils.user_current.setPassword(str_pass);
                                        Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                                }
                        ));
            }else {
                Toast.makeText(getApplicationContext(), "Pass không khớp", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initView() {
        apiGrocery = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiGrocery.class);
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signUpBtn = findViewById(R.id.signUpBtn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}