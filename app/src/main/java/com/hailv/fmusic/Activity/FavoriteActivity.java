package com.hailv.fmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        lvMusic = findViewById(R.id.lvFavorite);
        songs = new ArrayList<>();
        songAdapter = new HomeAdapter(this,R.layout.item_home_layout,songs);

        lvMusic.setAdapter(songAdapter);

        createSong();
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
}
