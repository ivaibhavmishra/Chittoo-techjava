package com.example.chitto_techjava;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Chittotechjava);
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(MainActivity.this, FirstPage.class);
        startActivity(intent);


        finish();
    }
}
