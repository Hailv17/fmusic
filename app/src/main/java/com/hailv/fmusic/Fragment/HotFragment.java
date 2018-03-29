package com.hailv.fmusic.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hailv.fmusic.R;
import com.hailv.fmusic.model.HomeAdapter;
import com.hailv.fmusic.model.Songs;

import java.util.ArrayList;

/**
 * Created by msi on 3/22/2018.
 */

public class HotFragment extends Fragment {
    private ListView lvHot;
    private ArrayList<Songs> songs;
    private HomeAdapter songAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View setting = inflater.inflate(R.layout.hots_fragment_layout,container,false);

        lvHot = setting.findViewById(R.id.lvHot);

        songs = new ArrayList<>();


        songAdapter = new HomeAdapter(getActivity(), R.layout.item_home_layout, songs);
        lvHot.setAdapter(songAdapter);

        createSong();



        return setting;
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
