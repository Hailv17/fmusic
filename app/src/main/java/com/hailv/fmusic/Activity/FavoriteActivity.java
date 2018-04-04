package com.hailv.fmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.hailv.fmusic.R;
import com.hailv.fmusic.model.HomeAdapter;
import com.hailv.fmusic.model.Songs;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private ArrayList<Songs> songs;
    private HomeAdapter songAdapter;
    private ListView lvMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvMusic = findViewById(R.id.lvFavorite);
        songs = new ArrayList<>();
        songAdapter = new HomeAdapter(this,R.layout.item_home_layout,songs);

        lvMusic.setAdapter(songAdapter);

        createSong();
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

    private void createSong(){
        Songs song1 = new Songs("abc","abc");
        Songs song2 = new Songs("abc","abc");
        Songs song3 = new Songs("abc","abc");
        Songs song4 = new Songs("abc","abc");
        Songs song5 = new Songs("abc","abc");

        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
        songs.add(song5);

        songAdapter.notifyDataSetChanged();
    }

    public void LoadListView(){
        HomeAdapter adapter = new HomeAdapter(getApplicationContext(),R.layout.item_home_layout, songs);
        lvMusic.setAdapter(songAdapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}