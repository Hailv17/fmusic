package com.hailv.fmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class PlayerActivity extends AppCompatActivity {
    static MediaPlayer mp;
    ArrayList<File> mySongs;

    private boolean isShuffle = false;
    private boolean isRepeat = false;

    private double startTime = 0;
    private double finalTime = 0;

    public static int oneTimeOnly = 0;

    private Handler myHandler = new Handler();

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
                playSong(position);
                seekBar.setMax(mp.getDuration());
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mp.stop();
//                mp.release();
//                position = (position+1)%mySongs.size();
//                u = Uri.parse(mySongs.get(position).toString());
//                mp = MediaPlayer.create(getApplicationContext(),u);
//                mp.start();
//                seekBar.setMax(mp.getDuration());

                if (isShuffle) {
                    Random rand = new Random();
                    oneTimeOnly = rand.nextInt((mySongs.size() - 1) - 0 + 2) + 0;
                    playSong(oneTimeOnly);
                    seekBar.setMax(mp.getDuration());
                }
                else {
                    mp.stop();
                    mp.release();
                    position = (position+1)%mySongs.size();
                    u = Uri.parse(mySongs.get(position).toString());
                    mp = MediaPlayer.create(getApplicationContext(),u);
                    playSong(position);
                    seekBar.setMax(mp.getDuration());
                }
            }
        });
        btnshuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShuffle){
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnshuffle.setImageResource(R.drawable.shuffle_inactive);
                }else{
                    // make repeat to true
                    isShuffle= true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnshuffle.setImageResource(R.drawable.shuffle_active);
                    btnrepeat.setImageResource(R.drawable.repeat_inactive);
                }
            }
        });
        btnrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRepeat){
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    btnrepeat.setImageResource(R.drawable.repeat_inactive);
                }else{
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isShuffle = false;
                    btnrepeat.setImageResource(R.drawable.repeat_active);
                    btnshuffle.setImageResource(R.drawable.shuffle_inactive);
                }
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
        playSong(position);
        mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                    if (isShuffle){
                        Random rand = new Random();
                        position = rand.nextInt((mySongs.size() - 1) - 0 + 1) + 0;
                        playSong(position);
                    } else if (isRepeat) {
                        playSong(position);
                    } else {
                        position++;
                        if (position >= mySongs.size())
                            position = 0;
                        playSong(position);
                    }
            }
        });

        myHandler.postDelayed(UpdateSongTime,100);
        btnplay.setImageResource(R.drawable.pause);
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

    public void updateProgressBar() {
        myHandler.postDelayed(UpdateSongTime, 100);
    }

    public void  playSong(int songIndex){
        // Play song
        try {
            mp.reset();
            mp.setDataSource(mySongs.get(songIndex).getPath());
            mp.prepare();
            mp.start();


            // Displaying Song title
            String songTitle = mySongs.get(songIndex).getName();
            tvName.setText(songTitle);

            // Changing Button Image to pause image
            btnplay.setImageResource(R.drawable.pause);

            // set Progress bar values
            seekBar.setProgress(0);
            seekBar.setMax(mp.getDuration());

            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public void onCompletion(MediaPlayer mp) {
//
//        // check for repeat is ON or OFF
//        if(isRepeat){
//            // repeat is on play same song again
//            playSong(oneTimeOnly);
//
//        } else if(isShuffle){
//            // shuffle is on - play a random song
//            Random rand = new Random();
//            oneTimeOnly = rand.nextInt((mySongs.size() - 1) - 0 + 1) + 0;
//            playSong(oneTimeOnly);
//        } else{
//            // no repeat or shuffle ON - play next song
//            if(oneTimeOnly < (mySongs.size() - 1)){
//                playSong(position + 1);
//                oneTimeOnly = oneTimeOnly + 1;
//            }else{
//                // play first song
//                position++;
//                if (position >= mySongs.size())
//                    position = 0;
//                playSong(position);
//
//            }
//        }
//    }


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
