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
import com.example.anshubhardwaj.cinemabuzz.activity.PeopleDetailActivity;
import com.example.anshubhardwaj.cinemabuzz.adapter.PopularPeopleAdapter;
import com.example.anshubhardwaj.cinemabuzz.api.ApiClient;
import com.example.anshubhardwaj.cinemabuzz.api.ApiInterface;
import com.example.anshubhardwaj.cinemabuzz.object.PeopleObject;
import com.example.anshubhardwaj.cinemabuzz.object.PeopleResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.anshubhardwaj.cinemabuzz.BuildConfig.API_KEY;
import static com.example.anshubhardwaj.cinemabuzz.utils.Constants.INTENT_ACTIVITY;

public class PopularPeopleFragment extends Fragment implements LoadPagesInterface,SharedItemClickListner {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PopularPeopleAdapter popularPeopleAdapter;
    public static final String INTENT_ID = "id";
    public static final String INTENT_IMAGE = "image";
    private Parcelable mRecyclerState;
    private static final String SAVEINSTANCE_RECYCLERSTATE = "RecyclerState";
    private static final String SAVEINSTANCE_LIST = "movielist";
    private static final String SAVEINSTANCE_PAGES = "pages";
    private static final String SAVEINSTANCE_CURRENT_PAGE = "page";
    public static final String TAG = "PopularPeopleFragment";

    public static PopularPeopleFragment newInstance(){
        return new PopularPeopleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_popular_people,container,false);
        ButterKnife.bind(this,view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        popularPeopleAdapter = new PopularPeopleAdapter(getContext(),this,this);
        recyclerView.setAdapter(popularPeopleAdapter);

        if (savedInstanceState==null){
            getPopularPeople(1);
        }else {
            popularPeopleAdapter.setCurrentPage(savedInstanceState.getInt(SAVEINSTANCE_CURRENT_PAGE));
            popularPeopleAdapter.setTotalPages(savedInstanceState.getInt(SAVEINSTANCE_PAGES));
            popularPeopleAdapter.setPeopleList((ArrayList)savedInstanceState.getParcelableArrayList(SAVEINSTANCE_LIST));
            mRecyclerState = savedInstanceState.getParcelable(SAVEINSTANCE_RECYCLERSTATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putInt(SAVEINSTANCE_PAGES,popularPeopleAdapter.getTotalPages());
        outState.putInt(SAVEINSTANCE_CURRENT_PAGE,popularPeopleAdapter.getCurrentPage());
        outState.putParcelable(SAVEINSTANCE_RECYCLERSTATE,mRecyclerState);
        outState.putParcelableArrayList(SAVEINSTANCE_LIST, (ArrayList<? extends Parcelable>) popularPeopleAdapter.getPeopleList());
    }

    private void getPopularPeople(final int page){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PeopleResponse> call = apiInterface.getPopularPeople(API_KEY,page);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                List<PeopleObject> list = response.body().getResult();
                if (page == 1){
                    popularPeopleAdapter.setPeopleList(list);
                    popularPeopleAdapter.setCurrentPage(1);
                }else if (page<=response.body().getTotalPages()){
                    popularPeopleAdapter.addPeopleList(list);
                }
                popularPeopleAdapter.setTotalPages(response.body().getTotalPages());
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadPage(int page) {
        getPopularPeople(page);
    }

    @Override
    public void onItemClick(int id, ImageView imageView, String imageURL) {
        Bundle bundle = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.transition_photo));
            bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imageView,imageView.getTransitionName()).toBundle();
        }
        Intent intent = new Intent(getContext(),PeopleDetailActivity.class);
        intent.putExtra(INTENT_ID,id);
        intent.putExtra(INTENT_IMAGE,imageURL);
        intent.putExtra(INTENT_ACTIVITY,TAG);
        startActivity(intent,bundle);
    }
}
