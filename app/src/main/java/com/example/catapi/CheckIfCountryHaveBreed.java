package com.example.catapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class CheckIfCountryHaveBreed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_if_country_have_breed);

        getGeolocation();
    }

    public void getGeolocation() {
        String countryName = null;
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        for (String provider : locationManager.getAllProviders()) {
            // Check for permission, if not request for permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        0
                );
                return;
            }
            // Get location
            @SuppressWarnings("ResourceType") Location location = locationManager.getLastKnownLocation(provider);
            if(location != null) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if(addresses != null && addresses.size() > 0) {
                        countryName = addresses.get(0).getCountryName();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        checkCountryHaveBreed(countryName);
    }

    public void checkCountryHaveBreed(String countryName){
        // All the cat breeds
        String url = "https://api.thecatapi.com/v1/breeds?api=38b3b2d3-a0eb-483c-be13-4cca81355eea";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    breedStringToJson(response, countryName);
                },
                error -> {
                    Toast.makeText(this, "request error", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }

    public void breedStringToJson(String response, String countryName){
        boolean haveNoBreed = true;
        String result = countryName + " has breeds:";
        TextView textView = findViewById(R.id.checkBreedView);
        try{
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < 67; i++){
                String country = jsonArray.getJSONObject(i).getString("origin");
                if (countryName == country){
                    result += " " + country;
                    haveNoBreed = false;
                };
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "parse json error", Toast.LENGTH_LONG).show();
        }

        if (haveNoBreed == true) result = "No cat breed originate from your country";

        textView.setText(result);
    }
}