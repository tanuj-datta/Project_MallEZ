package com.example.mallez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class wallet extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        bottomNavigationView = findViewById(R.id.menubar);
        bottomNavigationView.setSelectedItemId(R.id.wallet);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.home:
                       startActivity(new Intent(getApplicationContext(),Homepage.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.wallet:
                        return true;
                    case R.id.add:
                        startActivity(new Intent(getApplicationContext(),addvehicle.class));
                        overridePendingTransition(0 , 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0 , 0);
                        return true;


                }
                return false;
            }
        });
    }
}