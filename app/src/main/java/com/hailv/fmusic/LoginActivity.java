package com.hailv.fmusic;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.hailv.fmusic.Activity.ForgotActivity;
import com.hailv.fmusic.Activity.MainActivity;
import com.hailv.fmusic.Activity.SignupActivity;
import com.hailv.fmusic.Activity.SplashScreen;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignup, tvForgot;
    TextInputLayout tilUsername, tilPassword;
    EditText etUsername, etPassword;
    Button btnLogin;
    private FirebaseAuth mAuth;
    String stringUsername, stringPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        tvSignup = findViewById(R.id.login_tvSignup);
        tvForgot = findViewById(R.id.login_tvForgot);
        tilUsername = findViewById(R.id.login_tilUsername);
        tilPassword = findViewById(R.id.login_tilPassword);
        etUsername = findViewById(R.id.login_etUsername);
        etPassword = findViewById(R.id.login_etPassword);
        btnLogin = findViewById(R.id.login_btnLogin);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyen sang man hinh signup
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyen sang man hinh forgot password
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiem tra thong tin khong duoc trong
                validateInfo();
                signinFirebase();
            }
        });

        Intent i = getIntent();
    }

    public void validateInfo(){
        stringUsername = etUsername.getText().toString();
        if (!TextUtils.isEmpty(stringUsername)){
            tilUsername.setErrorEnabled(false);
        }else {
            tilUsername.setError("Username không được để trống");
            tilUsername.setErrorEnabled(true);
        }

        stringPassword = etPassword.getText().toString();
        if (!TextUtils.isEmpty(stringPassword)){
            tilPassword.setErrorEnabled(false);
        }else {
            tilPassword.setError("Password không được để trống");
            tilPassword.setErrorEnabled(true);
        }
    }

    public void signinFirebase(){
        stringUsername = etUsername.getText().toString();
        stringPassword = etPassword.getText().toString();
        if(!TextUtils.isEmpty(stringUsername) && !TextUtils.isEmpty(stringPassword)){
            mAuth.signInWithEmailAndPassword(stringUsername, stringPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("login success", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //login thanh cong chuyen sang home page
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName("User")
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("Update display name", "User profile updated.");
                                                }
                                            }
                                        });

//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("login failure", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }
}
