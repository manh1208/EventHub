package com.linhv.eventhub.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.activity.EventDetailActivity;
import com.linhv.eventhub.adapter.NotificationAdapter;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.Notification;
import com.linhv.eventhub.model.response_model.GetNotificationResponseModel;
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
public class NotificationFragment extends Fragment {
    ViewHolder viewHolder;
    private Context mContext;
    private String userId;
    private List<Notification> notifications;
    private NotificationAdapter notificationAdapter;
    private RestService restService;


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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Notification notification = notificationAdapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(notification.getTitle())
                        .setMessage(notification.getMessage())
                        .setPositiveButton("Đi đến sự kiện", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                                intent.putExtra("eventId",notification.getId());
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
}
