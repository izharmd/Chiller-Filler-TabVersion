package com.bws.musclefood;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.bws.musclefood.common.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class InsertUpdateProduct {

    public void callApi(Activity activity) {
        AsyncHttpClient client = new AsyncHttpClient();
         JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("CartItemID", "");
            jsonObject.put("Price", "2.50");
            jsonObject.put("ProductID", "20");
            jsonObject.put("Quantity", "1");
          //  jsonObject.put("SessionID", Constant.Companion.getSessionID());
            jsonObject.put("SessionID","0.2132243");
            jsonObject.put("TotalPrice", "2.50");
            jsonObject.put("UserID", "2");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();

        jsonArray.put(jsonObject);

        HttpEntity entity;
        try {
            entity = new StringEntity(String.valueOf(jsonArray), "UTF-8");
        } catch (IllegalArgumentException e) {
            Log.d("HTTP", "StringEntity: IllegalArgumentException");
            return;
        }
        String contentType = "application/json; charset=utf-8";

        client.post(activity, "http://food-hunt.co.uk/ChillerFiller/API/ChillerFillerMobileWS.svc/InsertUpdateCartDetails", entity, contentType, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(activity,"ssss======",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(activity,"fffff",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
