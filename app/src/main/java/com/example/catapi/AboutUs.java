package com.example.catapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Create hyperlink for the last line
        TextView linkToCatApi = findViewById(R.id.catApiText);
        linkToCatApi.setMovementMethod(LinkMovementMethod.getInstance());
        linkToCatApi.setLinkTextColor(R.color.redCatApi);
    }
}