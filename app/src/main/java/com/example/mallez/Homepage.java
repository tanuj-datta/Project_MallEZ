package com.example.mallez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallez.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {

    Button logout;
    TextView name,email;
    FirebaseAuth firebaseAuth;
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        firebaseAuth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logout);
        bottomNavigationView = findViewById(R.id.menubar);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.home:
                        return true;
                    case R.id.wallet:
                        startActivity(new Intent(getApplicationContext(),wallet.class));
                        overridePendingTransition(0 , 0);
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent i = new Intent(Homepage.this,Login_Activity.class);
                startActivity(i);
                Toast.makeText(Homepage.this, "Logout Successfull", Toast.LENGTH_SHORT).show();
            }
        });



    }


}