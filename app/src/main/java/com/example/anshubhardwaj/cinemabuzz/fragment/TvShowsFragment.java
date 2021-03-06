package com.example.anshubhardwaj.cinemabuzz.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anshubhardwaj.cinemabuzz.R;
import com.example.anshubhardwaj.cinemabuzz.adapter.TvShowsPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowsFragment extends Fragment {

    @BindView(R.id.sliding_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private final CharSequence[] titles = {"AIRING TODAY","ON THE AIR","POPULAR","TOP RATED"};

    public static TvShowsFragment newInstance(){
        return new TvShowsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_shows,container,false);
        ButterKnife.bind(this,view);

        int numOfTabs = 4;
        TvShowsPagerAdapter pagerAdapter = new TvShowsPagerAdapter(getChildFragmentManager(), titles, numOfTabs);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }

        return view;
    }
}
