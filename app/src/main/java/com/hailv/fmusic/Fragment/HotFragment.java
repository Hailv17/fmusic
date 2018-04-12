package com.hailv.fmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hailv.fmusic.PlayerActivity;
import com.hailv.fmusic.R;
import com.hailv.fmusic.model.HomeAdapter;
import com.hailv.fmusic.model.Songs;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nomor on 4/3/2018.
 */

public class HotFragment extends Fragment {
    private ArrayList<Songs> songs;
    private HomeAdapter songAdapter;
    private ListView lvMusic;
    String[] item;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);

        lvMusic = view.findViewById(R.id.lvHot);
        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());

        item = new String[mySongs.size() ];
        for (int i = 0; i < mySongs.size(); i++){
            item[i] = mySongs.get(i).getName().toString().replace(".mp3","").replace("wav","");
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),R.layout.item_home_layout,R.id.tvName,item);
        lvMusic.setAdapter(adp);
        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(),PlayerActivity.class).putExtra("pos",position).putExtra("songList",mySongs));
            }
        });

        lvMusic.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });


        return view;
    }


//    public void LoadListView(){
//        HomeAdapter adapter = new HomeAdapter(getContext(),R.layout.item_home_layout, songs);
//        lvMusic.setAdapter(songAdapter);
//        adapter.notifyDataSetChanged();
//    }

    public ArrayList<File> findSongs(File root){
        ArrayList<File> a1 = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                a1.addAll(findSongs(singleFile));
            }
            else {
                if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                    a1.add(singleFile);
                }
            }
        }
        return a1;
    }
}
