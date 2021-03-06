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
import com.example.anshubhardwaj.cinemabuzz.activity.TvShowDetailActivity;
import com.example.anshubhardwaj.cinemabuzz.adapter.TvShowAdapter;
import com.example.anshubhardwaj.cinemabuzz.api.ApiClient;
import com.example.anshubhardwaj.cinemabuzz.api.ApiInterface;
import com.example.anshubhardwaj.cinemabuzz.object.PeopleTvShowResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.anshubhardwaj.cinemabuzz.BuildConfig.API_KEY;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.INTENT_ACTIVITY;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.SAVEINSTANCE_ID;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.SAVEINSTANCE_LIST;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.SAVEINSTANCE_RECYCLERSTATE;

public class PeopleShowsFragment extends Fragment implements SharedItemClickListner {

    private int id;
    private static final String INSTANCE_ID = "id";
    private TvShowAdapter tvShowAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.noResult)
    LinearLayout noResultLayout;
    private Parcelable mRecyclerState;
    public static final String TAG = "PeopleShowsFrament";

    public static PeopleShowsFragment newInstance(int id){
        PeopleShowsFragment peopleShowsFragment = new PeopleShowsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INSTANCE_ID,id);
        peopleShowsFragment.setArguments(bundle);
        return peopleShowsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            id = getArguments().getInt(INSTANCE_ID);
        }else {
            id = savedInstanceState.getInt(SAVEINSTANCE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_people_shows,container,false);

        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        tvShowAdapter = new TvShowAdapter(getContext(),null,this);
        tvShowAdapter.setCurrentPage(1);
        tvShowAdapter.setTotalPages(1);
        recyclerView.setAdapter(tvShowAdapter);

        if (savedInstanceState==null) {
            getPeopleTvShowList(id);
        }else {
            tvShowAdapter.setTvShowList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST));
            mRecyclerState = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE,mRecyclerState);
        outState.putParcelableArrayList(SAVEINSTANCE_LIST,(ArrayList<? extends Parcelable>) tvShowAdapter.getTvShowList());
        outState.putInt(SAVEINSTANCE_ID,id);
    }

    private void getPeopleTvShowList(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PeopleTvShowResponse> call = apiInterface.getPeopleTvShowList(id,API_KEY);
        call.enqueue(new Callback<PeopleTvShowResponse>() {
            @Override
            public void onResponse(Call<PeopleTvShowResponse> call, Response<PeopleTvShowResponse> response) {
                if (response!=null) {
                    PeopleTvShowResponse showResponse = response.body();
                    if (showResponse.getPeopleTvShowList()!=null && showResponse.getPeopleTvShowList().size()!=0) {
                        showResult();
                        tvShowAdapter.setTvShowList(response.body().getPeopleTvShowList());
                    }else {
                        showError();
                    }
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<PeopleTvShowResponse> call, Throwable t) {

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
        Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageURL);
        intent.putExtra(INTENT_ACTIVITY,TAG);
        startActivity(intent,bundle);
    }
}
