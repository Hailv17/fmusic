package com.hailv.fmusic.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hailv.fmusic.R;
import com.hailv.fmusic.model.HomeAdapter;
import com.hailv.fmusic.model.Songs;

import java.util.ArrayList;

/**
 * Created by nomor on 4/3/2018.
 */

public class HotFragment extends Fragment {
    private ArrayList<Songs> songs;
    private HomeAdapter songAdapter;
    private ListView lvMusic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);

        lvMusic = view.findViewById(R.id.lvHot);
        songs = new ArrayList<>();
        songAdapter = new HomeAdapter(getContext(),R.layout.item_home_layout,songs);

        lvMusic.setAdapter(songAdapter);

        createSong();

        return view;
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
        HomeAdapter adapter = new HomeAdapter(getContext(),R.layout.item_home_layout, songs);
        lvMusic.setAdapter(songAdapter);
        adapter.notifyDataSetChanged();
    }
}
