package com.linhv.eventhub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.ComponentAdapter;
import com.linhv.eventhub.adapter.ContentAdapter;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.response_model.GetEventDetailResponseModel;
import com.linhv.eventhub.services.RestService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class ContentEventFragment extends Fragment {
    ViewHolder viewHolder;
    private ContentAdapter contentAdapter;
    RestService restService;
    private int eventId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_content,container,false);
        eventId = getArguments().getInt("eventId",-1);
        initView(v);
        return v;

    }

    private void initView(View v) {

        viewHolder = new ViewHolder();
        restService = new RestService();
        viewHolder.listView = (RecyclerView) v.findViewById(R.id.lv_content);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        viewHolder.listView.setLayoutManager(mLayoutManager);
        Event event = new Event();
        contentAdapter = new ContentAdapter(getActivity(),event);
        viewHolder.listView.setItemAnimator(new DefaultItemAnimator());
        viewHolder.listView.setAdapter(contentAdapter);
        restService.getEventService().getEvent(eventId, new Callback<GetEventDetailResponseModel>() {
            @Override
            public void success(GetEventDetailResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()){
                    contentAdapter.setEvent(responseModel.getEvent());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private class ViewHolder {
        RecyclerView listView;
    }
}
