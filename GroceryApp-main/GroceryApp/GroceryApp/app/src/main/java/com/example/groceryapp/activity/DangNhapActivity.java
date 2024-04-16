package com.example.groceryapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groceryapp.R;
import com.example.groceryapp.retrofit.ApiGrocery;
import com.example.groceryapp.retrofit.RetrofitClient;
import com.example.groceryapp.utils.Utils;
import com.google.gson.Gson;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangky,txtresetpass;
    EditText edt_email, edt_pass;
    Button loginBtn;

    ApiGrocery apiGrocery;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressBar progressBar;
    boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();
    }

    private void initControl() {
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });

        txtresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResetPassActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = edt_email.getText().toString().trim();
                String str_pass = edt_pass.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(str_pass)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập pass", Toast.LENGTH_LONG).show();
                } else {
                    Paper.book().write("email",str_email);
                    Paper.book().write("email",str_pass);
                    progressBar.setVisibility(View.VISIBLE);
                    dangNhap(str_email,str_pass);

                }
            }
        });
    }


    private void initView() {
        Paper.init(this);
        apiGrocery = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiGrocery.class);
        txtdangky = findViewById(R.id.txtdangky);
        edt_email = findViewById(R.id.edt_email);
        txtresetpass = findViewById(R.id.txtresetpass);
        edt_pass = findViewById(R.id.edt_pass);
        loginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progress_bar);


//        real data
        if(Paper.book().read("email") != null && Paper.book().read("pass") != null) {
            edt_email.setText(Paper.book().read("email"));
            edt_pass.setText(Paper.book().read("pass"));

            if(Paper.book().read("isLogin") != null){
                boolean flag = Paper.book().read("isLogin");
                if (flag)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            dangNhap(Paper.book().read("email"),Paper.book().read("pass"));
                        }
                    }, 1000);
                }
            }
        }
    }

    private void dangNhap(String email, String pass) {
        compositeDisposable.add(apiGrocery.dangNhap(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                isLogin = true;
                                Paper.book().write("isLogin", isLogin);
                                Utils.user_current = userModel.getResult().get(0);
                                Paper.book().write("user", userModel.getResult().get(0));
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Tai khoan hoac mat khau sai", Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                ));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getEmail() != null && Utils.user_current.getPassword() != null) {
            edt_email.setText(Utils.user_current.getEmail());
            edt_pass.setText(Utils.user_current.getPassword());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}