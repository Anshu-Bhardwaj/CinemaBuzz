package com.example.anshubhardwaj.cinemabuzz.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anshubhardwaj.cinemabuzz.R;
import com.example.anshubhardwaj.cinemabuzz.fragment.FavouriteFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.MoviesFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.PopularPeopleFragment;
import com.example.anshubhardwaj.cinemabuzz.fragment.TvShowsFragment;
import com.example.anshubhardwaj.cinemabuzz.search.SearchResultActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;


    public static int currentFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_movies);

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_frame, MoviesFragment.newInstance(),null)
                    .commit();
            currentFragment = 0;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        ComponentName cn = new ComponentName(this,SearchResultActivity.class);
        searchView.setSearchableInfo(searchManager != null ? searchManager.getSearchableInfo(cn) : null);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movies) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame,MoviesFragment.newInstance(),null)
                    .commit();
            currentFragment = 0;
        } else if (id == R.id.nav_tv_shows) {
            TvShowsFragment tvShowsFragment = TvShowsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame,tvShowsFragment,null)
                    .commit();
            currentFragment = 1;
        } else if (id == R.id.nav_popular_people) {
            PopularPeopleFragment popularPeopleFragment = PopularPeopleFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame,popularPeopleFragment,null)
                    .commit();
            currentFragment = 2;

        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this,ContactActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
