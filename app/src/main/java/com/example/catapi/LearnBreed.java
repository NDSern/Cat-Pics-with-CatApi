package com.example.catapi;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Random;

public class LearnBreed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_breed);

        showBreedOnCreate();
    }

    public void showBreedOnCreate(){
        String url = "https://api.thecatapi.com/v1/breeds?api=38b3b2d3-a0eb-483c-be13-4cca81355eea";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        breedStringToJson(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "parse json error", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "request error", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }

    public void showNewBreedOnCreate(View view){
        String url = "https://api.thecatapi.com/v1/breeds?api=38b3b2d3-a0eb-483c-be13-4cca81355eea";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        breedStringToJson(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "parse json error", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "request error", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }

    String breedId = null;

    public void breedStringToJson(String response) throws JSONException {
        // Get the JSON
        JSONArray jsonArray = new JSONArray(response);

        // Choose a random breed
        int size = jsonArray.length() + 1;
        int randomBreedId = new Random().nextInt(size);

        // Get json object for that breed id
        JSONObject breedInfo = jsonArray.getJSONObject(randomBreedId);

        // Process the information
        TextView name = findViewById(R.id.breedNameView);
        TextView origin = findViewById(R.id.breedOriginView);
        TextView lifespan = findViewById(R.id.breedLifespanView);
        TextView temperament = findViewById(R.id.breedTemperamentView);
        TextView description = findViewById(R.id.breedDescriptionView);
        TextView weight = findViewById(R.id.breedWeightView);

        //Get the breed ID
        breedId = breedInfo.getString("id");

        name.setText(breedInfo.getString("name"));
        origin.setText("Origin: " + breedInfo.getString("origin"));
        lifespan.setText("Lifespan: " + breedInfo.getString("life_span") + " years");
        temperament.setText(breedInfo.getString("temperament"));
        description.setText(breedInfo.getString("description"));
        weight.setText("Weight: " + breedInfo.getJSONObject("weight").getString("metric") + " kg");

        // Make the url into picture
        getBreedImage();
    }

    public void resetCatPic(View view){
        if (breedId == null){
            breedId = "abys";
            Toast.makeText(this, "fail getting image", Toast.LENGTH_LONG).show();
        }

        ImageView catPic = findViewById(R.id.catPic);
        String url = "https://api.thecatapi.com/v1/images/search?breed_ids=" + breedId + "&has_breeds=1&api=38b3b2d3-a0eb-483c-be13-4cca81355eea";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        String catUrl = jsonArray.getJSONObject(0).getString("url");
                        Glide.with(this).load(catUrl).into(catPic);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "parse json error", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "request error", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }

    public void getBreedImage(){
        ImageView catPic = findViewById(R.id.catPic);

        String url = "https://api.thecatapi.com/v1/images/search?breed_ids=" + breedId + "&has_breeds=1&api=38b3b2d3-a0eb-483c-be13-4cca81355eea";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        String catUrl = jsonArray.getJSONObject(0).getString("url");
                        Glide.with(this).load(catUrl).into(catPic);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "parse json error", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "request error", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }
}