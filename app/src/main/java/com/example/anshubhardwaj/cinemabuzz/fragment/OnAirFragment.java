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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anshubhardwaj.cinemabuzz.Interface.LoadPagesInterface;
import com.example.anshubhardwaj.cinemabuzz.Interface.SharedItemClickListner;
import com.example.anshubhardwaj.cinemabuzz.R;
import com.example.anshubhardwaj.cinemabuzz.activity.TvShowDetailActivity;
import com.example.anshubhardwaj.cinemabuzz.adapter.TvShowAdapter;
import com.example.anshubhardwaj.cinemabuzz.api.ApiClient;
import com.example.anshubhardwaj.cinemabuzz.api.ApiInterface;
import com.example.anshubhardwaj.cinemabuzz.object.TvShowObject;
import com.example.anshubhardwaj.cinemabuzz.object.TvShowResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.anshubhardwaj.cinemabuzz.BuildConfig.API_KEY;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.INTENT_ACTIVITY;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.SAVEINSTANCE_CURRENT_PAGE;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.SAVEINSTANCE_LIST;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.SAVEINSTANCE_PAGES;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.SAVEINSTANCE_RECYCLERSTATE;

public class OnAirFragment extends Fragment implements SharedItemClickListner,LoadPagesInterface,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private TvShowAdapter tvShowAdapter;
    private Parcelable mRecyclerState;


    public static OnAirFragment newInstance(){
        return new OnAirFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_air,container,false);
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        tvShowAdapter = new TvShowAdapter(getContext(),this,this);
        recyclerView.setAdapter(tvShowAdapter);

        refreshLayout.setOnRefreshListener(this);

        if (savedInstanceState==null){
            getOnTheAirShow(1);
        }else {
            mRecyclerState = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE);
            tvShowAdapter.setTvShowList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST));
            tvShowAdapter.setTotalPages(savedInstanceState.getInt(SAVEINSTANCE_PAGES));
            tvShowAdapter.setCurrentPage(savedInstanceState.getInt(SAVEINSTANCE_CURRENT_PAGE));
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

        return view;
    }

    private void getOnTheAirShow(final int page){
        refreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShowResponse> call = apiInterface.getOnTheAirShow(API_KEY,page);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                List<TvShowObject> list = response.body().getResults();
                if (page==1){
                    tvShowAdapter.setTvShowList(list);
                    tvShowAdapter.setCurrentPage(1);
                }else if (page<=response.body().getTotalPages()){
                    tvShowAdapter.addTvShowList(list);
                }
                tvShowAdapter.setTotalPages(response.body().getTotalPages());
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE,mRecyclerState);
        outState.putInt(SAVEINSTANCE_PAGES,tvShowAdapter.getTotalPages());
        outState.putInt(SAVEINSTANCE_CURRENT_PAGE,tvShowAdapter.getCurrentPage());
        outState.putParcelableArrayList(SAVEINSTANCE_LIST,(ArrayList<? extends Parcelable>) tvShowAdapter.getTvShowList());
    }

    @Override
    public void loadPage(int page) {
        getOnTheAirShow(page);
    }

    @Override
    public void onItemClick(int id, ImageView imageView, String imageURL) {
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imageView,imageView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
        intent.putExtra("Id",id);
        intent.putExtra("ImageURL",imageURL);
        intent.putExtra(INTENT_ACTIVITY,AiringTodayFragment.TAG);
        startActivity(intent,bundle);
    }

    @Override
    public void onRefresh() {
        getOnTheAirShow(1);
    }
}
