package com.example.jgram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private Button buttonSignUp;
    private TextInputEditText editTextSignUpUsername, editTextSignUpPassword, editTextSignUpConfirm;
    private TextInputLayout textInputLayoutUsername, textInputLayoutPassword, textInputLayoutConfirm;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        editTextSignUpUsername = findViewById(R.id.editTextSignUpUsername);
        editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);
        editTextSignUpConfirm = findViewById(R.id.editTextSignUpConfirm);
        textInputLayoutConfirm = findViewById(R.id.textInputLayoutConfirm);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextSignUpConfirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signUpJGramAccount();
                    return true;
                }
                return false;
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpJGramAccount();
            }
        });
    }

    private void signUpJGramAccount() {
        String username = editTextSignUpUsername.getText().toString() + "@jgram.com";
        String password = editTextSignUpPassword.getText().toString();
        String confirmPassword = editTextSignUpConfirm.getText().toString();
        if (TextUtils.isEmpty(editTextSignUpUsername.getText().toString())) {
            textInputLayoutUsername.setError("Username is required!");
            return;
        }else {
            textInputLayoutUsername.setError(null);
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            textInputLayoutPassword.setError("Password must be at least 6 characters");
        } else {
            textInputLayoutPassword.setError(null);
            if (password.matches(confirmPassword)) {
                firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "We can not create your account!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                textInputLayoutConfirm.setError("Password is not matched!");
            }
        }
    }
}
