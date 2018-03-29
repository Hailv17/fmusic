package com.hailv.fmusic;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hailv.fmusic.Activity.ForgotActivity;
import com.hailv.fmusic.Activity.MainActivity;
import com.hailv.fmusic.Activity.SignupActivity;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignup, tvForgot;
    TextInputLayout tilUsername, tilPassword;
    EditText etUsername, etPassword;
    Button btnLogin;
    private FirebaseAuth mAuth;
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
                //login thanh cong chuyen sang home page
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        Intent i = getIntent();
    }

    public void validateInfo(){
        String stringUsername = etUsername.getText().toString();
        if (!TextUtils.isEmpty(stringUsername)){
            tilUsername.setErrorEnabled(false);
        }else {
            tilUsername.setError("Username không được để trống");
            tilUsername.setErrorEnabled(true);
        }

        String stringPassword = etPassword.getText().toString();
        if (!TextUtils.isEmpty(stringPassword)){
            tilPassword.setErrorEnabled(false);
        }else {
            tilPassword.setError("Password không được để trống");
            tilPassword.setErrorEnabled(true);
        }
    }
}
