package com.linhv.eventhub.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.activity.EventDetailActivity;
import com.linhv.eventhub.activity.SearchActivity;
import com.linhv.eventhub.adapter.EventAdapter;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.response_model.GetEventsResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/5/2016.
 */
public class EventStoragedFragment extends Fragment implements MenuItemCompat.OnActionExpandListener {
    private static final String TAG = "Event Storaged";
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
    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView;
    private String userId;
    private boolean isOrganizer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.framgment_event_storaged, container, false);

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
                if (firstVisibleItem + visibleItemCount >= totalItemCount - 1 && totalItemCount > 1) {
                    if (!flag_loading && !isFull) {
                        Log.i(TAG, "scroll");
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
                intent.putExtra("eventId", event.getId());
                intent.putExtra("eventName", event.getName());
                intent.putExtra("eventImage", event.getImageUrl());
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    private void initView(View v) {
        mContext = getActivity();
        userId = DataUtils.getINSTANCE(mContext).getmPreferences().getString(QuickSharePreferences.SHARE_USERID, "");
        isOrganizer = DataUtils.getINSTANCE(mContext).getmPreferences().getBoolean(QuickSharePreferences.SHARE_IS_ORGANIZER,false);
        viewHolder = new ViewHolder();
        restService = new RestService();
        mEvents = new ArrayList<>();
        viewHolder.lvEvents = (ListView) v.findViewById(R.id.lv_all_event_list);
        viewHolder.layoutRefresh = (SwipeRefreshLayout) v.findViewById(R.id.layout_refresh);
        eventAdapter = new EventAdapter(getActivity(), R.layout.item_list_event, mEvents,true,false);
        footerView = ((LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.item_load_more, null, false);
        if (viewHolder.lvEvents.getFooterViewsCount() <= 0)
            viewHolder.lvEvents.addFooterView(footerView);
        viewHolder.lvEvents.setEmptyView(v.findViewById(R.id.layout_empty));
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
            Log.i(TAG, "Resume");
            loadData();
        }
    }

    private void loadData() {
        flag_loading = true;
            restService.getUserService().getFollowEvent(userId, take, skip, new Callback<GetEventsResponseModel>() {
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

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(mContext, "Open menu search", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Toast.makeText(mContext, "Close Menu search", Toast.LENGTH_SHORT).show();
        return true;
    }


    private final class ViewHolder {
        ListView lvEvents;
        SwipeRefreshLayout layoutRefresh;
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//
//        super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.main, menu);
//        MenuItem searchItem = menu.findItem(R.id.menu_search);
//
//        SearchManager searchManager = (SearchManager) mContext.getSystemService(Context.SEARCH_SERVICE);
//        searchView = null;
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//
//            queryTextListener = new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    Log.i("onQueryTextChange", newText);
//                    if (newText.length() <= 0) {
//                        eventAdapter.setEventList(mEvents);
//                        flag_loading = false;
//                    }
////                    doSearch(newText);
//                    return true;
//                }
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    Log.i("onQueryTextSubmit", query);
//                    doSearchAPI(query);
//                    return true;
//                }
//            };
//            searchView.setOnQueryTextListener(queryTextListener);
//            searchView.onActionViewCollapsed();
//        }
//
//    }

    private void doSearchAPI(String query) {
        flag_loading = true;
        restService.getEventService().searchByName(query, 0, 10, new Callback<GetEventsResponseModel>() {
            @Override
            public void success(GetEventsResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()) {
                    eventAdapter.setEventList(responseModel.getEvents());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        Toast.makeText(mContext, "Close", Toast.LENGTH_SHORT).show();
        super.onOptionsMenuClosed(menu);
    }

    private void doSearch(String newText) {
        if (newText != null && newText.length() > 0) {
            List<Event> tmp = new ArrayList<>();
            for (Event item : mEvents) {
                if (item.getName().toUpperCase().contains(newText.toUpperCase()) || item.getAddress().toUpperCase().contains(newText.toUpperCase())) {
                    tmp.add(item);
                }
            }

            eventAdapter.setEventList(tmp);
        } else {
            eventAdapter.setEventList(mEvents);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_filter) {
//            viewHolder.toolbar.setTitle("Tìm kiếm sự kiện");
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.frame_main, new SearchFragment())
//                    .commit();
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            return true;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(mContext, "Close", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadAllEvent();
    }
}