package com.example.intentchallenge;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView ivMood, ivPhone, ivWeb, ivLocation;
    Button btnCreate;

    final int CREATE_CONTACT = 1;
    String name = "", numberPhone = "", web = "", map = "", mood = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      declare component on layout connected to the variable name
        ivMood = findViewById(R.id.ivMood);
        ivPhone = findViewById(R.id.ivPhone);
        ivWeb = findViewById(R.id.ivWeb);
        ivLocation = findViewById(R.id.ivLocation);
        btnCreate = findViewById(R.id.btnCreate);

//        hidden icon
        ivMood.setVisibility(View.GONE);
        ivPhone.setVisibility(View.GONE);
        ivWeb.setVisibility(View.GONE);
        ivLocation.setVisibility(View.GONE);

//        this code is executed when the user click on Create button
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, com.example.intentchallenge.CreateContact.class);
                startActivityForResult(intent, CREATE_CONTACT);

            }
        });

//        this code is executed when user click on Phone icon (image view)
        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numberPhone));
                startActivity(intent);
            }
        });

//        this code is executed when user click on Web icon (image view)
        ivWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + web));
                startActivity(intent);
            }
        });

//        this code is executed when user click on Location icon (image view)
        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:" + map);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

    }

    //    this code is executed when CreateContact.java send a result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        check result of CreateContact activity exactly
        if (requestCode == CREATE_CONTACT) {

            if (resultCode == RESULT_OK) {
                ivMood.setVisibility(View.VISIBLE);
                ivPhone.setVisibility(View.VISIBLE);
                ivWeb.setVisibility(View.VISIBLE);
                ivLocation.setVisibility(View.VISIBLE);

                name = data.getStringExtra("name");
                numberPhone = data.getStringExtra("numberPhone");
                web = data.getStringExtra("web");
                map = data.getStringExtra("map");
                mood = data.getStringExtra("mood");

                if (mood.equals("happy")) {
                    ivMood.setImageResource(R.drawable.happy);
                } else if (mood.equals("sad")) {
                    ivMood.setImageResource(R.drawable.sad);
                } else {
                    ivMood.setImageResource(R.drawable.ok);
                }
            } else {
                Toast.makeText(this, "No data press through!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}