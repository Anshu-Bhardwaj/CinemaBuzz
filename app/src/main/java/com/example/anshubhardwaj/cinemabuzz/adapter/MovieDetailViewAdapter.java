package com.example.anshubhardwaj.cinemabuzz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.anshubhardwaj.cinemabuzz.fragment.CastFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.InfoFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.ReviewFragment;

public class MovieDetailViewAdapter extends FragmentStatePagerAdapter {

    private CharSequence[] titles;
    private int numOfTabs;
    private int id;

    public MovieDetailViewAdapter(FragmentManager fm,CharSequence[] titles,int numOfTabs,int id){
        super(fm);
        this.titles = titles;
        this.numOfTabs = numOfTabs;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return InfoFragment.newInstance(id);
        }else if (position==1){
            return CastFragment.newInstance(id);
        }else {
            return ReviewFragment.newInstance(id);
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
