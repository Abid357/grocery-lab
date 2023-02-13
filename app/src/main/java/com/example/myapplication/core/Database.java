package com.example.myapplication.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String PRODUCT_TAG = "products";
    private static final String BRAND_TAG = "brands";
    public static final int INSERT = 1;
    public static final int EDIT = 2;
    public static final int DELETE = 3;
    private static Database instance;
    private Context context;
    private SharedPreferences sharedPreferences;
    private List<Product> productList;
    private List<Brand> brandList;

    private Database(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("grocerylab", Context.MODE_PRIVATE);
        productList = new ArrayList<>();
        brandList = new ArrayList<>();
    }

    public static Database withContext(Context context) {
        if (instance == null)
            instance = new Database(context);
        return instance;
    }

    public boolean addBrand(Brand brand) {
        for (Brand b : brandList)
            if (b.getBrandName().toLowerCase().equals(brand.getBrandName().toLowerCase()) &&
                    b.getProductName().equals(brand.getProductName())) {
                Toast.makeText(context, "Duplicate brand.", Toast.LENGTH_SHORT).show();
                return false;
            }
        brandList.add(0, brand);
        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();
        try {
            for (Brand b : brandList)
                jsonArray.put(new JSONObject(gson.toJson(b)));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(BRAND_TAG, jsonArray.toString());
            editor.apply();
            Toast.makeText(context, "Brand added.", Toast.LENGTH_SHORT).show();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteBrand(int position) {
        brandList.remove(position);
        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();
        try {
            for (Brand b : brandList)
                jsonArray.put(new JSONObject(gson.toJson(b)));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(BRAND_TAG, jsonArray.toString());
            editor.apply();
            Toast.makeText(context, "Brand deleted.", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public List<Brand> getBrandList() {
        if (!brandList.isEmpty())
            return brandList;
        String jsonData = sharedPreferences.getString(BRAND_TAG, "");
        if (!jsonData.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Brand>>() {
            }.getType();
            brandList = gson.fromJson(jsonData, type);
        }
        return brandList;
    }

    public boolean addProduct(Product product) {
        for (Product p : productList)
            if (p.getName().toLowerCase().equals(product.getName().toLowerCase())) {
                Toast.makeText(context, "Duplicate product.", Toast.LENGTH_SHORT).show();
                return false;
            }
        productList.add(0, product);
        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();
        try {
            for (Product p : productList)
                jsonArray.put(new JSONObject(gson.toJson(p)));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PRODUCT_TAG, jsonArray.toString());
            editor.apply();
            Toast.makeText(context, "Product added.", Toast.LENGTH_SHORT).show();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteProduct(int position) {
        productList.remove(position);
        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();
        try {
            for (Product p : productList)
                jsonArray.put(new JSONObject(gson.toJson(p)));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PRODUCT_TAG, jsonArray.toString());
            editor.apply();
            Toast.makeText(context, "Product deleted.", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProductList() {
        if (!productList.isEmpty())
            return productList;
        String jsonData = sharedPreferences.getString(PRODUCT_TAG, "");
        if (!jsonData.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>() {
            }.getType();
            productList = gson.fromJson(jsonData, type);
        }
        return productList;
    }

    public List<String> getProductStringList() {
        List<String> stringList = new ArrayList<>();
        for (Product product : getProductList())
            stringList.add(product.getName());
        return stringList;
    }
}
