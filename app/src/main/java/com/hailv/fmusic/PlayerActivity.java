package com.hailv.fmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class PlayerActivity extends AppCompatActivity {
    static MediaPlayer mp;
    ArrayList<File> mySongs;

    private double startTime = 0;
    private double finalTime = 0;

    public static int oneTimeOnly = 0;

    private Handler myHandler = new Handler();;

    SeekBar seekBar;
    ImageButton btnplay;
    ImageButton btnpreviou;
    ImageButton btnnext;
    ImageButton btnshuffle;
    ImageButton btnrepeat;

    TextView tvName, tvTime, tvTiming;

    int position;
    Uri u;

    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnplay = findViewById(R.id.play);
        btnpreviou = findViewById(R.id.previous);
        btnnext = findViewById(R.id.next);
        btnshuffle = findViewById(R.id.shuffle);
        btnrepeat = findViewById(R.id.repeat);

        tvName = findViewById(R.id.tvName);
        tvTime = findViewById(R.id.tvTime);
        tvTiming = findViewById(R.id.tvTiming);

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()) {
                    mp.pause();
                    btnplay.setImageResource(R.drawable.play);
                    btnplay.setEnabled(true);

                    finalTime = mp.getDuration();
                    startTime = mp.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekBar.setMax((int) finalTime);
                        oneTimeOnly = 1;
                    }

//                    tvTime.setText(String.format("%d:%d",
//                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
//                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
//                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
//                                            finalTime)))
//                    );
//
//                    tvTiming.setText(String.format("%d:%d",
//                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
//                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
//                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
//                                            startTime)))
//                    );

//                    myHandler.postDelayed(UpdateSongTime,100);

                }else {
                    mp.start();
                    btnplay.setImageResource(R.drawable.pause);
                }

            }
        });
        btnpreviou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();
                position = (position-1<0)? mySongs.size()-1: position-1;

                u = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                seekBar.setMax(mp.getDuration());
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.release();
                position = (position+1)%mySongs.size();
                u = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                seekBar.setMax(mp.getDuration());
            }
        });
        btnshuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        seekBar = findViewById(R.id.seekBar);
        updateSeekBar = new Thread(){
            @Override
            public void run() {

                int totalDuration = mp.getDuration();
                int curentPosition = 0;
                while (curentPosition < totalDuration){
                    try {
                        sleep(5000);
                        curentPosition = mp.getCurrentPosition();
                        seekBar.setProgress(curentPosition);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
//                super.run();
            }
        };

        if (mp!=null){
            mp.stop();
            mp.release();
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySongs = (ArrayList) b.getParcelableArrayList("songList");
        position = b.getInt("pos",0);


        u = Uri.parse(mySongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(),u);
        mp.start();
        myHandler.postDelayed(UpdateSongTime,100);
        btnplay.setImageResource(R.drawable.pause);
        seekBar.setMax(mp.getDuration());
        updateSeekBar.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });



    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            finalTime = mp.getDuration();
            startTime = mp.getCurrentPosition();
            tvTiming.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            tvTime.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    finalTime)))
            );
            seekBar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };




//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id){
//            case R.id.play:
//                if (mp.isPlaying()){
//                    mp.pause();
//                }else {
//                    mp.start();
//                }
//                break;
//            case R.id.previous:
//                mp.stop();
//                mp.release();
//                position = (position-1<0)? mySongs.size()-1: position-1;
//
//                u = Uri.parse(mySongs.get(position).toString());
//                mp = MediaPlayer.create(getApplicationContext(),u);
//                mp.start();
////                seekBar.setMax(mp.getDuration());
//                break;
//            case R.id.next:
//                mp.stop();
//                mp.release();
//                position = (position+1)%mySongs.size();
//                u = Uri.parse(mySongs.get(position).toString());
//                mp = MediaPlayer.create(getApplicationContext(),u);
//                mp.start();
////                seekBar.setMax(mp.getDuration());
//                break;
//            case R.id.repeat:
//                if (mp.isPlaying()){
//                    mp.pause();
//                }else mp.start();
//                break;
//            case R.id.shuffle:
//                if (mp.isPlaying()){
//                    mp.pause();
//                }else mp.start();
//                break;
//        }
//    }
}
