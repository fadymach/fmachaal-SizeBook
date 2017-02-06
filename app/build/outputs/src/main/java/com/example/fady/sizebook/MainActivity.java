package com.example.fady.sizebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void addEntries(View view){
        Intent add_intent = new Intent(this, addEntries.class);
        startActivity(add_intent);
    }

    public void viewEntries(View view){
        Intent view_intent = new Intent(this, viewEntries.class);
        startActivity(view_intent);
    }
}
