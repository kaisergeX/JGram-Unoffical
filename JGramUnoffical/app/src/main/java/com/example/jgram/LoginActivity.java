package com.example.jgram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "FacebookLogin";

    private Button buttonLogin;
    private TextInputEditText editTextUsername;
    private TextInputEditText editTextPassword;
    private TextInputLayout usernameWrapper, passwordWrapper;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private LoginButton buttonLoginFacebook;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        usernameWrapper = findViewById(R.id.textInputLayoutUsernameLogin);
        passwordWrapper = findViewById(R.id.textInputLayoutPasswordLogin);
        buttonLoginFacebook = findViewById(R.id.buttonLoginFacebook);

        firebaseAuth = FirebaseAuth.getInstance();
        loginFacebook();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//         Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void loginJGramAccount() {
        String username = editTextUsername.getText().toString() + "@jgram.com";
        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(editTextUsername.getText().toString())) {
            usernameWrapper.setError("Username is required!");
            return;
        } else {
            usernameWrapper.setError(null);
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
//            passwordWrapper.setError("Password must be at least 6 characters");
            Toast.makeText(LoginActivity.this, "Your username or password is incorrect", Toast.LENGTH_SHORT).show();
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

    private void loginFacebook() {
        callbackManager = CallbackManager.Factory.create();
        buttonLoginFacebook.setPermissions("email", "public_profile");
        buttonLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "RegisterCallback failed. "+error.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, JGramActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Login FB Successful!", Toast.LENGTH_SHORT).show();
//                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
