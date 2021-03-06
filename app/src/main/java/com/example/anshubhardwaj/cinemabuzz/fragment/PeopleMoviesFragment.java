package com.example.anshubhardwaj.cinemabuzz.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.anshubhardwaj.cinemabuzz.Interface.SharedItemClickListner;
import com.example.anshubhardwaj.cinemabuzz.R;
import com.example.anshubhardwaj.cinemabuzz.activity.MovieDetailActivity;
import com.example.anshubhardwaj.cinemabuzz.adapter.MovieAdapter;
import com.example.anshubhardwaj.cinemabuzz.api.ApiClient;
import com.example.anshubhardwaj.cinemabuzz.api.ApiInterface;
import com.example.anshubhardwaj.cinemabuzz.object.PeopleMovieResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.anshubhardwaj.cinemabuzz.BuildConfig.API_KEY;
import static com.example.anshubhardwaj.cinemabuzz.fragment.NowPlayingFragment.ACTIVITY_NAME;

public class PeopleMoviesFragment extends Fragment implements SharedItemClickListner {

    public static final String TAG = "PeopleMoviesFragment";
    private int id;
    private static final String INSTANCE_ID = "id";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.noResult)
    LinearLayout noResultLayout;
    private MovieAdapter movieAdapter;
    private static final String SAVE_ID = "id";
    private static final String SAVE_RECYCLER_STATE = "recyclerState";
    private static final String SAVE_LIST = "list";
    private Parcelable mRecyclerState;

    public static PeopleMoviesFragment newInstance(int id){
        PeopleMoviesFragment peopleMoviesFragment = new PeopleMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        peopleMoviesFragment.setArguments(bundle);
        return peopleMoviesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            id = getArguments().getInt(INSTANCE_ID);
        }else {
            id = savedInstanceState.getInt(SAVE_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_ID,id);
        outState.putParcelableArrayList(SAVE_LIST, (ArrayList<? extends Parcelable>) movieAdapter.getMovieList());
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVE_RECYCLER_STATE,mRecyclerState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people_movies,container,false);
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        movieAdapter = new MovieAdapter(getContext(),null,this);
        movieAdapter.setCurrentPage(1);
        movieAdapter.setTotalPages(1);
        recyclerView.setAdapter(movieAdapter);

        if (savedInstanceState==null) {
            getPeopleMoviesList(id);
        }else {
            movieAdapter.setMovieList((ArrayList)savedInstanceState.getParcelableArrayList(SAVE_LIST));
            mRecyclerState = savedInstanceState.getParcelable(SAVE_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

        return view;
    }

    private void getPeopleMoviesList(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PeopleMovieResponse> call = apiInterface.getPeopleMovieList(id,API_KEY);
        call.enqueue(new Callback<PeopleMovieResponse>() {
            @Override
            public void onResponse(Call<PeopleMovieResponse> call, Response<PeopleMovieResponse> response) {
                if (response!=null) {
                    PeopleMovieResponse peopleMovieResponse = response.body();
                    if (peopleMovieResponse.getPeopleMoviesList()!=null && peopleMovieResponse.getPeopleMoviesList().size()!=0) {
                        showResult();
                        movieAdapter.setMovieList(peopleMovieResponse.getPeopleMoviesList());
                    }else {
                        showError();
                    }
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<PeopleMovieResponse> call, Throwable t) {

            }
        });
    }

    private void showError(){
        recyclerView.setVisibility(View.GONE);
        noResultLayout.setVisibility(View.VISIBLE);
    }

    private void showResult(){
        recyclerView.setVisibility(View.VISIBLE);
        noResultLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int id, ImageView imageView, String imageURL) {
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.people_movies_transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imageView,imageView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageURL);
        intent.putExtra(ACTIVITY_NAME,TAG);
        startActivity(intent,bundle);
    }
}
