package com.linhv.eventhub.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.activity.EventDetailActivity;
import com.linhv.eventhub.adapter.EventAdapter;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.response_model.GetEventsResponseModel;
import com.linhv.eventhub.services.RestService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/5/2016.
 */
public class AllEventFragment extends Fragment {
    private static final String TAG = "AllEventFragment";
    private Context mContext;
    private ViewHolder viewHolder;
    private List<Event> mEvents;
    private static final int MAX_TAKE = 5;
    private int skip;
    private int take;
    private boolean isFull;
    View footerView;
    private boolean flag_loading;
    private RestService restService;
    private EventAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_event, container, false);
        initView(v);
        event();
        return v;
    }

    private void event() {

        viewHolder.lvEvents.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount-1 && totalItemCount >1) {
                    if (!flag_loading && !isFull) {
                        Log.i(TAG,"scroll");
                        loadData();
                    } else {
                        removeFooter();
                    }
                }
            }
        });

        viewHolder.layoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Toast.makeText(mContext, "Refresh", Toast.LENGTH_SHORT).show();
                reloadAllEvent();
            }
        });

        viewHolder.lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = eventAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra("eventId",event.getId());
                intent.putExtra("eventName",event.getName());
                intent.putExtra("eventImage",event.getImageUrl());
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }
        });
    }

    private void initView(View v) {
        mContext = getActivity();
        viewHolder = new ViewHolder();
        restService = new RestService();
        mEvents = new ArrayList<>();
        viewHolder.lvEvents = (ListView) v.findViewById(R.id.lv_all_event_list);
        viewHolder.layoutRefresh = (SwipeRefreshLayout) v.findViewById(R.id.layout_refresh);
        eventAdapter = new EventAdapter(getActivity(), R.layout.item_list_event, mEvents);
        footerView = ((LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.item_load_more, null, false);
        if (viewHolder.lvEvents.getFooterViewsCount() <= 0)
            viewHolder.lvEvents.addFooterView(footerView);
        viewHolder.lvEvents.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged();
    }

    private void init() {
        mEvents.clear();
        skip = 0;
        take = MAX_TAKE;
        flag_loading = false;
        isFull = false;
    }

    private void reloadAllEvent() {
        init();
        if (viewHolder.lvEvents.getFooterViewsCount() <= 0)
            viewHolder.lvEvents.addFooterView(footerView);
//        feedsAdapter.notifyDataSetChanged();
        if (!flag_loading) {
            Log.i(TAG,"Resume");
            loadData();
        }
    }

    private void loadData() {
        flag_loading = true;
        restService.getEventService().getEvents(take, skip, new Callback<GetEventsResponseModel>() {
            @Override
            public void success(GetEventsResponseModel responseModel, Response response) {
                if (viewHolder.layoutRefresh.isRefreshing()) {
                    viewHolder.layoutRefresh.setRefreshing(false);
                }
                flag_loading = false;
                if (responseModel.isSucceed()) {
                    if (responseModel.getEvents() != null && responseModel.getEvents().size() > 0) {
                        for (Event item : responseModel.getEvents()) {
                            mEvents.add(item);
                        }
                        eventAdapter.setEventList(mEvents);
                        if (mEvents.size() < (skip + take)) {
                            isFull = true;
                            removeFooter();
                        }

                        skip = skip + take;
                    } else {
                        isFull = true;
                        removeFooter();
                    }
                } else {
                    removeFooter();
                    Log.i(TAG, responseModel.getErrorsString());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private void removeFooter() {
        viewHolder.lvEvents.removeFooterView(footerView);
    }


    private final class ViewHolder {
        ListView lvEvents;
        SwipeRefreshLayout layoutRefresh;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadAllEvent();
    }
}
