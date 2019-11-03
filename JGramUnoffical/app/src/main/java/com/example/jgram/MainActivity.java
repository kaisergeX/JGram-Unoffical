package com.example.jgram;

import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.pm.Signature;
import android.os.Bundle;
//import android.util.Base64;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button buttonLoginRedirect, buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
//        try {
//            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.example.jgram", PackageManager.GET_SIGNATURES);
//            for(Signature signature: packageInfo.signatures){
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
//                messageDigest.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
//            }
//        }catch (Exception e){
//        }
        buttonLoginRedirect = findViewById(R.id.buttonLoginRedirect);
        buttonSignUp = findViewById(R.id.buttonSignUpRedirect);

        buttonLoginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//         Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            String username = email.substring(0,email.indexOf("@"));
            Toast.makeText(this, "Welcome back, "+username, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, JGramActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
