package com.example.myapplication.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String PRODUCT_TAG = "products";
    private static Database instance;
    Context context;
    SharedPreferences sharedPreferences;

    private Database(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("grocerylab", Context.MODE_PRIVATE);
    }

    public static Database withContext(Context context){
        if (instance == null)
            instance = new Database(context);
        return instance;
    }

    public List<Product> getProductList(){
        List<Product> productList = new ArrayList<>();
        String jsonData = sharedPreferences.getString(PRODUCT_TAG, "");
        if (!jsonData.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>() {
            }.getType();
            productList = gson.fromJson(jsonData, type);
        }
        return productList;
    }


    public List<String> getProductStringList(){
        List<String> stringList = new ArrayList<>();
        for (Product product : getProductList())
            stringList.add(product.getName());
        return stringList;
    }
}
