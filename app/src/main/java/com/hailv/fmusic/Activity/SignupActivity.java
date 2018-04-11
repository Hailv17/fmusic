package com.hailv.fmusic.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hailv.fmusic.R;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText suedtName, suedtPassword, suedtEmail, suedtRePassword;
    private Button btnSignup, btnClear;
    private TextInputLayout sutilName, sutilPassword, sutilEmail, sutilRePassword;
    private FirebaseAuth firebaseAuth;
    private String stringName, stringEmail, stringPassword, stringRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        //Get Firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        sutilName= findViewById(R.id.tilName);
        sutilPassword= findViewById(R.id.tilPassword);
        sutilEmail= findViewById(R.id.tilEmail);
        sutilRePassword= findViewById(R.id.tilRePassword);

        suedtName= findViewById(R.id.edtName);
        suedtPassword= findViewById(R.id.edtPassword);
        suedtEmail= findViewById(R.id.edtEmail);
        suedtRePassword= findViewById(R.id.edtRePassword);

        btnSignup= findViewById(R.id.btnSignup);
        btnClear= findViewById(R.id.btnClear);

        stringName = suedtName.getText().toString().trim();
        stringEmail = suedtEmail.getText().toString().trim();
        stringPassword = suedtPassword.getText().toString().trim();
        stringRePassword = suedtRePassword.getText().toString().trim();

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearForm();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInfo();
                signUp();
            }
        });

    }
    public void signUp(){
        //create user
        firebaseAuth.createUserWithEmailAndPassword(stringEmail, stringPassword)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "createUserWithEmail:success");
                            String userID = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

                            Map userInfo = new HashMap<>();
                            userInfo.put("email",stringEmail);
                            userInfo.put("name",stringName);

                            currentUserDb.updateChildren(userInfo);
                            Toast.makeText(SignupActivity.this, "Đăng ký thành công",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SplashScreen.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Đăng ký thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void validateInfo(){
        stringName = suedtName.getText().toString().trim();
        if (!TextUtils.isEmpty(stringName)){
            sutilName.setErrorEnabled(false);
        }else {
            sutilName.setError("Name không được để trống");
            sutilName.setErrorEnabled(true);
        }
        //
        stringEmail = suedtEmail.getText().toString().trim();
        if (!TextUtils.isEmpty(stringEmail)){
            sutilEmail.setErrorEnabled(false);
        }else {
            sutilEmail.setError("Email không được để trống");
            sutilEmail.setErrorEnabled(true);
        }
        //
        stringPassword = suedtPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(stringPassword)){
            sutilPassword.setErrorEnabled(false);
        }else if(stringPassword.length() < 6){
            sutilPassword.setError("Password phải lớn hơn 6 kí tự");
            sutilPassword.setErrorEnabled(true);
        }else {
            sutilPassword.setError("Password không được để trống");
            sutilPassword.setErrorEnabled(true);
        }
        //
        stringRePassword = suedtRePassword.getText().toString().trim();
        if (!TextUtils.isEmpty(stringRePassword)){
            sutilRePassword.setErrorEnabled(false);
        }else if(stringPassword!=stringRePassword){
            sutilRePassword.setError("Password phải lớn hơn 6 kí tự");
            sutilRePassword.setErrorEnabled(true);
        }else {
            sutilRePassword.setError("Password không được để trống");
            sutilRePassword.setErrorEnabled(true);
        }
    }

    public void clearForm(){
        suedtName.setText("");
        suedtEmail.setText("");
        suedtPassword.setText("");
        suedtRePassword.setText("");
    }
}
