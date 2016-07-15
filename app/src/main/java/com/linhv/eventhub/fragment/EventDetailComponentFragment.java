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
import android.widget.ListView;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.ComponentAdapter;
import com.linhv.eventhub.adapter.EventComponentItemAdapter;
import com.linhv.eventhub.model.ComponentItem;
import com.linhv.eventhub.model.response_model.GetComponentItemsResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class EventDetailComponentFragment extends Fragment {
    private int componentId;
    private ViewHolder viewHolder;
    private EventComponentItemAdapter itemAdapter;
    private List<ComponentItem> componentItems;
    private RestService restService;
    ComponentAdapter componentAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_component, container, false);
        componentId = getArguments().getInt("componentId", -1);
        initView(v);
        getComponentItem();
        return v;
    }

    private void getComponentItem() {
        restService.getEventService().getComponentItem(componentId, new Callback<GetComponentItemsResponseModel>() {
            @Override
            public void success(GetComponentItemsResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()) {
                    componentItems = responseModel.getComponentItems();
//                    itemAdapter.setComponentItems(componentItems);
                    componentAdapter.setComponentItems(componentItems);
                } else {
                    Toast.makeText(getActivity(), responseModel.getErrorsString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                DataUtils.getINSTANCE(getActivity()).ConnectionError();
            }
        });
    }

    private void initView(View v) {
        restService = new RestService();
        viewHolder = new ViewHolder();
        componentItems = new ArrayList<>();
        viewHolder.listView = (RecyclerView) v.findViewById(R.id.lv_component_list);
//        itemAdapter = new EventComponentItemAdapter(getActivity(), R.layout.item_component_item, componentItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        viewHolder.listView.setLayoutManager(mLayoutManager);
        componentAdapter = new ComponentAdapter(getActivity(),componentItems);
        viewHolder.listView.setItemAnimator(new DefaultItemAnimator());
        viewHolder.listView.setAdapter(componentAdapter);

    }

    private class ViewHolder {
        RecyclerView listView;
    }
}
