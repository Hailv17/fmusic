package com.hailv.fmusic.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hailv.fmusic.Fragment.HotFragment;
import com.hailv.fmusic.Fragment.NewFragment;
import com.hailv.fmusic.R;

public class HomeActivity extends AppCompatActivity {
    private Button btnNew, btnHot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnHot = findViewById(R.id.btnHot);
        btnNew = findViewById(R.id.btnNew);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new NewFragment());
                Toast.makeText(HomeActivity.this,"News",Toast.LENGTH_SHORT).show();
            }
        });

        btnHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new HotFragment());
                Toast.makeText(HomeActivity.this,"Hots",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag,fragment);
        fragmentTransaction.commit();
    }
}
