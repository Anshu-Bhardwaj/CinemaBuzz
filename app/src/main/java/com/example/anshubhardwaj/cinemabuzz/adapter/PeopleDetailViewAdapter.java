package com.example.anshubhardwaj.cinemabuzz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.anshubhardwaj.cinemabuzz.fragment.PeopleInfoFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.PeopleMoviesFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.PeopleShowsFragment;

public class PeopleDetailViewAdapter extends FragmentStatePagerAdapter {

    private int id;
    private CharSequence[] titles;
    private int numOfTabs;

    public PeopleDetailViewAdapter(FragmentManager fm, CharSequence[] titles, int numOfTabs, int id) {
        super(fm);
        this.id = id;
        this.titles = titles;
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PeopleInfoFragment.newInstance(id);
        } else if (position == 1) {
            return PeopleMoviesFragment.newInstance(id);
        } else {
            return PeopleShowsFragment.newInstance(id);
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