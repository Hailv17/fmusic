package com.hailv.fmusic.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hailv.fmusic.LoginActivity;
import com.hailv.fmusic.R;
import com.hailv.fmusic.model.PagerAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView navtvName, navtvEmail;
    ViewPager pager;
    //NavigationView navi;
    TabLayout tabLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        //navi = findViewById(R.id.nav_view);
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        navtvName = header.findViewById(R.id.nav_tvName);
        navtvEmail = header.findViewById(R.id.nav_tvEmail);
        updateUserInfo();

        //Navigation

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //View Pager
        pager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter pagerAdapter = new PagerAdapter(manager);
        pager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_btnHome) {
            Toast.makeText(this, "Home page", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_btnMymusic) {
            startActivity(new Intent(MainActivity.this, MyMusicActivity.class));
            Toast.makeText(this, "My music", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_btnFavorite) {
            startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_btnLogout) {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            Toast.makeText(this, "Sign out account", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateUserInfo(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in to Firebase, but we can only get
            // basic info like name, email, and profile photo url
            String name = user.getDisplayName();
            String email = user.getEmail();


            navtvName.setText(name);
            navtvEmail.setText(email);
        } else {
            // User is signed out of Firebase
        }
    }
}
