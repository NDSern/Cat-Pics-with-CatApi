package com.example.catapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cat Image on launch
        getCatURL();
    }

    private void getCatURL() {
        // URL give a random cat pic everytime you call it on launch
        String url = "https://api.thecatapi.com/v1/images/search?format=json?api=38b3b2d3-a0eb-483c-be13-4cca81355eea";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    getCatImage(response);
                },
                error -> {
                    Toast.makeText(this, "request error", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }

    public void getCatURL(View view) {
        // URL give a random cat pic everytime you call it
        String url = "https://api.thecatapi.com/v1/images/search?format=json?api=38b3b2d3-a0eb-483c-be13-4cca81355eea";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    getCatImage(response);
                },
                error -> {
                    Toast.makeText(this, "request error", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }

    public void getCatImage(String response) {
        // Make the url into picture
        ImageView catPic = findViewById(R.id.catPic);
        try {
            JSONArray cat_url = new JSONArray(response);
            String url = cat_url.getJSONObject(0).getString("url");
            Glide.with(this).load(url).into(catPic);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "make image error", Toast.LENGTH_LONG).show();
        }
    }

    public void checkBreedPage(View view) {
        // Create intent, in this case, a new activity
        Intent openCheckBreed = new Intent(this, CheckIfCountryHaveBreed.class);

        // Start new activity
        startActivity(openCheckBreed);
    }

    public void aboutUsPage(View view) {
        // Create intent, in this case, a new activity
        Intent openAboutUs = new Intent(this, AboutUs.class);

        // Start new activity
        startActivity(openAboutUs);
    }

    public void learnBreedPage(View view) {
        Intent openLearnBreed = new Intent(this, LearnBreed.class);

        startActivity(openLearnBreed);
    }
}