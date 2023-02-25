package com.example.mallez;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallez.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button signup;

    FirebaseFirestore firebaseFirestore;
    ActivityMainBinding binding;
    ProgressDialog progressDialog;
    Button login;
    EditText name;
    EditText email;
    EditText password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        login= findViewById(R.id.button2);

        firebaseAuth = FirebaseAuth.getInstance();
       firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        signup = findViewById(R.id.signup);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Login_Activity.class);
                startActivity(intent);
            }
        });
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setContentView(binding.getRoot());
                String name = binding.name.getText().toString();
                String email = binding.email.getText().toString().trim();
                String password = binding.password.getText().toString();

                progressDialog.show();
               firebaseAuth.createUserWithEmailAndPassword(email,password)
                       .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                           @Override
                           public void onSuccess(AuthResult authResult) {
                               Intent intent = new Intent(MainActivity.this,Homepage.class);
                               startActivity(intent);
                            progressDialog.cancel();

                            firebaseFirestore.collection("User")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .set(new UserModel(name,email));
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(Exception e) {
                               Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                           }
                       });
            }
        });

        }



    }

