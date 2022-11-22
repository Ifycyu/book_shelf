package com.jnu.myapplication.data;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataDownloader {
    @NonNull
    public String download(String path)  {


        try{
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStreamReader inputStreamReader =new InputStreamReader(conn.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String tempLine = null;
                StringBuffer resultBuffer = new StringBuffer();
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }

                Log.i("test Data",resultBuffer.toString());
                return resultBuffer.toString();
            }}
        catch (Exception exception){

            Log.e("error",exception.getMessage());

        }

        return "";
    }
    public BookJson parsonJson(String jsonText)
    {
        BookJson bookJson=new BookJson();
        try {
            JSONObject root = new JSONObject(jsonText);

            JSONObject shop = root.getJSONObject("data");

            bookJson.setBookName(shop.getString("bookName"));
            bookJson.setAuthor(shop.getString("author"));
            bookJson.setPress(shop.getString("press"));
            bookJson.setIsbn(shop.getString("isbn"));
            bookJson.setYear(shop.getJSONArray("pressDate").get(0).toString());
            bookJson.setMonth(shop.getJSONArray("pressDate").get(1).toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  bookJson;
    }
}
