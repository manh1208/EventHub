package com.linhv.eventhub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.OrganizerAdapter;
import com.linhv.eventhub.model.Organizer;
import com.linhv.eventhub.model.response_model.GetOrganizerResponseModel;
import com.linhv.eventhub.services.RestService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/17/2016.
 */
public class EventOrganizerFragment extends Fragment {

    private int eventId;
    private RestService restService;
    private ViewHolder viewHolder;
    private OrganizerAdapter organizerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_organizer, container, false);
        eventId = getArguments().getInt("eventId", -1);
        initView(v);
        return v;
    }

    private void initView(View v) {
        viewHolder = new ViewHolder();
        restService = new RestService();
        viewHolder.listView = (RecyclerView) v.findViewById(R.id.lv_organizer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        viewHolder.listView.setLayoutManager(mLayoutManager);
        organizerAdapter = new OrganizerAdapter(getActivity(), new Organizer());
        viewHolder.listView.setItemAnimator(new DefaultItemAnimator());
        viewHolder.listView.setAdapter(organizerAdapter);
        restService.getEventService().getOrganizer(eventId, new Callback<GetOrganizerResponseModel>() {
            @Override
            public void success(GetOrganizerResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()) {
                    organizerAdapter.setOrganizer(responseModel.getOrganizer());
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
