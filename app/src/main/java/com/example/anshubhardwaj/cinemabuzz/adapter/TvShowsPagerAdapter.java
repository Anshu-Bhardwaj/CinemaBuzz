package com.example.anshubhardwaj.cinemabuzz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.anshubhardwaj.cinemabuzz.fragment.AiringTodayFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.OnAirFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.TvShowPopularFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.TvShowTopRatedFragment;

public class TvShowsPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence[] titles;
    private int numOfTabs;

    public TvShowsPagerAdapter(FragmentManager fm, CharSequence[] titles, int numOfTabs) {
        super(fm);
        this.titles = titles;
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return AiringTodayFragment.newInstance();
        }else if (position==1){
            return OnAirFragment.newInstance();
        }else if (position==2){
            return TvShowPopularFragment.newInstance();
        }else {
            return TvShowTopRatedFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
