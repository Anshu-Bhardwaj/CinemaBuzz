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
import com.example.anshubhardwaj.cinemabuzz.adapter.ViewPagerAdapter;

import butterknife.ButterKnife;

public class MoviesFragment extends Fragment {

    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private TabLayout tabs;
    private CharSequence[] Titles={"NOW PLAYING","UPCOMING","POPULAR","TOP RATED"};
    private int Numboftabs = 4;

    public static MoviesFragment newInstance(){
        return new MoviesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies,container,false);
        ButterKnife.bind(this,view);

        adapter =  new ViewPagerAdapter(getChildFragmentManager(),Titles,Numboftabs);

        pager = (ViewPager) view.findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
        tabs = (TabLayout) view.findViewById(R.id.sliding_layout);
        tabs.setupWithViewPager(pager);

        if (getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else {
            tabs.setTabMode(TabLayout.MODE_FIXED);
        }

        return view;
    }

}