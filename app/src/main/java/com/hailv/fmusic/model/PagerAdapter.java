package com.hailv.fmusic.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hailv.fmusic.Fragment.HotFragment;
import com.hailv.fmusic.Fragment.NewFragment;

/**
 * Created by nomor on 4/3/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment = new NewFragment();
                break;
            case 1: fragment = new HotFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0: title = "New";
                break;
            case 1: title = "Hot";
                break;
        }
        return title;
    }
}