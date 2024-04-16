package com.example.groceryapp.utils;

import com.example.groceryapp.model.Category;
import com.example.groceryapp.model.Giohang;
import com.example.groceryapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL="http://192.168.1.2/grocerydata/";
    public static List<Giohang> manggiohang;
    public static List<Giohang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
}
