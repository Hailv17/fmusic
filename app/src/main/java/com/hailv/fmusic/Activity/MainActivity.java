package com.hailv.fmusic.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.hailv.fmusic.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_hamburg);
//        ActionBar n = getSupportActionBar();
//        n.setHomeAsUpIndicator(android.R.drawable.ic_menu_add);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemHome:
                Toast.makeText(this,"home",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.itemMyMusic:
                Toast.makeText(this,"my music",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this,MyMusicActivity.class);
                startActivity(intent1);
                break;
            case R.id.itemFavorite:
                Toast.makeText(this,"favorite",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this,FavoriteActivity.class);
                startActivity(intent2);
                break;
            case R.id.itemLogout:
                Toast.makeText(this,"log out",Toast.LENGTH_SHORT).show();
                break;
        }
        //thay doi cac fragment cho cac man hinh tai day
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
