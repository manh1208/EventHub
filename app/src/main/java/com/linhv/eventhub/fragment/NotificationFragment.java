package com.linhv.eventhub.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.activity.EventDetailActivity;
import com.linhv.eventhub.activity.SearchActivity;
import com.linhv.eventhub.adapter.NotificationAdapter;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.Notification;
import com.linhv.eventhub.model.request_model.ReadNotificationRequestModel;
import com.linhv.eventhub.model.response_model.GetEventsResponseModel;
import com.linhv.eventhub.model.response_model.GetNotificationResponseModel;
import com.linhv.eventhub.model.response_model.ReadNotificationResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 8/8/16.
 */
public class NotificationFragment extends Fragment implements MenuItemCompat.OnActionExpandListener{
    ViewHolder viewHolder;
    private Context mContext;
    private String userId;
    private List<Notification> notifications;
    private NotificationAdapter notificationAdapter;
    private RestService restService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notification,container,false);
        init(v);
        loadData();
        return v;
    }

    private void loadData() {
        restService.getUserService().getNotification(userId, new Callback<GetNotificationResponseModel>() {
            @Override
            public void success(GetNotificationResponseModel responseModel, Response response) {
                if (viewHolder.refreshLayout.isRefreshing()) {
                    viewHolder.refreshLayout.setRefreshing(false);
                }
                if (responseModel.isSucceed()){
                    notificationAdapter.setmNotifications(responseModel.getNotifications());
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private void init(View v) {
        viewHolder = new ViewHolder(v);
        restService = new RestService();
        mContext = getActivity();
        userId = DataUtils.getINSTANCE(mContext.getApplicationContext()).getmPreferences().getString(QuickSharePreferences.SHARE_USERID, "");
        notifications = new ArrayList<Notification>();
        notificationAdapter = new NotificationAdapter(getActivity(), R.layout.item_list_notification, notifications);
        viewHolder.lvNotifications.setAdapter(notificationAdapter);
        viewHolder.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        viewHolder.lvNotifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Notification notification = notificationAdapter.getItem(position);
                restService.getUserService().readNotification(new ReadNotificationRequestModel(notification.getId()), new Callback<ReadNotificationResponseModel>() {
                    @Override
                    public void success(ReadNotificationResponseModel responseModel, Response response) {
                        if (responseModel.isSucceed()){
                            notificationAdapter.getItem(position).setActive(false);
                            notificationAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(notification.getTitle())
                        .setMessage(notification.getMessage())
                        .setPositiveButton("Đi đến sự kiện", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                                intent.putExtra("eventId",notification.getEventId());
                                intent.putExtra("eventName",notification.getEventName());
//                                intent.putExtra("eventImage",event.getImageUrl());
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.right_in,R.anim.left_out);
                            }
                        })
                        .setNegativeButton("Tắt", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });
    }

    private class ViewHolder {
//        ImageButton btnMarkRead;
        ListView lvNotifications;
        SwipeRefreshLayout refreshLayout;
        ViewHolder(View v){
            lvNotifications = (ListView) v.findViewById(R.id.lv_list_notification);
            refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.layout_refresh);
        }
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


    private void doSearchAPI(String query) {
//        flag_loading = true;
//        restService.getEventService().searchByName(query, 0, 10, new Callback<GetEventsResponseModel>() {
//            @Override
//            public void success(GetEventsResponseModel responseModel, Response response) {
//                if (responseModel.isSucceed()) {
//                    eventAdapter.setEventList(responseModel.getEvents());
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        Toast.makeText(mContext, "Close", Toast.LENGTH_SHORT).show();
        super.onOptionsMenuClosed(menu);
    }

    private void doSearch(String newText) {
//        if (newText != null && newText.length() > 0) {
//            List<Event> tmp = new ArrayList<>();
//            for (Event item : mEvents) {
//                if (item.getName().toUpperCase().contains(newText.toUpperCase()) || item.getAddress().toUpperCase().contains(newText.toUpperCase())) {
//                    tmp.add(item);
//                }
//            }
//
//            eventAdapter.setEventList(tmp);
//        } else {
//            eventAdapter.setEventList(mEvents);
//        }
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
//        searchView.setOnQueryTextListener(queryTextListener);
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                Toast.makeText(mContext, "Close", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        return super.onOptionsItemSelected(item);
    }
}
