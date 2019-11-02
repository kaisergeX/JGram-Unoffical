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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private TextInputEditText editTextUsername;
    private TextInputEditText editTextPassword;
    private TextInputLayout usernameWrapper, passwordWrapper;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        usernameWrapper = findViewById(R.id.textInputLayoutUsernameLogin);
        passwordWrapper = findViewById(R.id.textInputLayoutPasswordLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginJGramAccount();
                    return true;
                }
                return false;
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginJGramAccount();
            }
        });
    }

    private void loginJGramAccount() {
        String username = editTextUsername.getText().toString() + "@jgram.com";
        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(editTextUsername.getText().toString())) {
            usernameWrapper.setError("Username is required!");
            return;
        }else {
            usernameWrapper.setError(null);
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            passwordWrapper.setError("Password must be at least 6 characters");
        } else {
            passwordWrapper.setError(null);

            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, JGramActivity.class));
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Your username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
