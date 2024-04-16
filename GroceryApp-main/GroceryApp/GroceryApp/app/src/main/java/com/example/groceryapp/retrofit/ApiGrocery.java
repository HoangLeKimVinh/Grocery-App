package com.example.groceryapp.retrofit;

import io.reactivex.rxjava3.core.Observable;

import com.example.groceryapp.model.CategoryModel;
import com.example.groceryapp.model.DonHangModel;
import com.example.groceryapp.model.Product;
import com.example.groceryapp.model.ProductModel;
import com.example.groceryapp.model.UserModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiGrocery {
    @GET("get_category.php")
    Observable<CategoryModel> getCategory();

    @GET("get_product.php")
    Observable<ProductModel> getProduct();

    @POST("Chitiet.php")
    @FormUrlEncoded
    Observable<ProductModel> getSanPham(
            @Field("page") int page,
            @Field("category_id") int category_id
    );

    @POST("dangky.php")
    @FormUrlEncoded
    Observable<UserModel> dangKy(
            @Field("Email") String Email,
            @Field("Password") String Password,
            @Field("UserName") String UserName,
            @Field("Mobile") String Mobile
    );
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("Email") String Email,
            @Field("Password") String Password
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("Email") String Email
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> createOrder(
            @Field("email") String email,
            @Field("sdt") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int id,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );

    @POST("search.php")
    @FormUrlEncoded
    Observable<ProductModel> search(
            @Field("search") String search
    );

}
