package com.linhv.eventhub.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.EventDetailTabAdapter;
import com.linhv.eventhub.adapter.HomeTabAdapter;
import com.linhv.eventhub.custom.CustomImage;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.EventComponent;
import com.linhv.eventhub.model.response_model.GetEventComponentResponseModel;
import com.linhv.eventhub.model.response_model.GetEventDetailResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EventDetailActivity extends AppCompatActivity {
    ViewHolder viewHolder;
    private int eventId;
    private String eventName;
    private Event event;
    private RestService restService;
    private List<EventComponent> components;
    private EventDetailTabAdapter detailTabAdapter;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        init();

        getEventDetail();
    }

    private void getEventDetail() {
        restService.getEventService().getEvent(eventId, userId, new Callback<GetEventDetailResponseModel>() {
            @Override
            public void success(GetEventDetailResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()) {
                    event = responseModel.getEvent();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewHolder.toolbar.setTitle(event.getName());
                        }
                    });

                    Picasso.with(EventDetailActivity.this).load(Uri.parse(DataUtils.URL + event.getImageUrl()))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(viewHolder.image);
//                    Toast.makeText(EventDetailActivity.this, event.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EventDetailActivity.this, responseModel.getErrorsString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                DataUtils.getINSTANCE(getApplicationContext()).ConnectionError();
            }
        });

    }

    private void init() {


        restService = new RestService();
        viewHolder = new ViewHolder();
        viewHolder.toolbar = (Toolbar) findViewById(R.id.toolbar);
        eventId = getIntent().getIntExtra("eventId", -1);
        eventName = getIntent().getStringExtra("eventName");
        viewHolder.toolbar.setTitle(eventName);
        userId = DataUtils.getINSTANCE(this).getmPreferences().getString(QuickSharePreferences.SHARE_USERID, "");
        setSupportActionBar(viewHolder.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewHolder.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewHolder.tabLayout = (TabLayout) findViewById(R.id.tabs_event_detail);
        viewHolder.viewPager = (ViewPager) findViewById(R.id.viewpager_event_detail);
        detailTabAdapter = new EventDetailTabAdapter(getSupportFragmentManager(), eventId);
        viewHolder.viewPager.setAdapter(detailTabAdapter);
//        viewHolder.tabLayout.setupWithViewPager(viewHolder.viewPager);

        viewHolder.tabLayout.post(new Runnable() {
            @Override
            public void run() {
                viewHolder.tabLayout.setupWithViewPager(viewHolder.viewPager);
            }
        });
        viewHolder.image = (CustomImage) findViewById(R.id.iv_detail_event_cover);

        getComponent();
    }

    private void getComponent() {
        restService.getEventService().getComponent(eventId, new Callback<GetEventComponentResponseModel>() {
            @Override
            public void success(GetEventComponentResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()) {
                    components = responseModel.getComponents();
                    detailTabAdapter.setComponents(components);

                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private final class ViewHolder {
        ViewPager viewPager;
        TabLayout tabLayout;
        CustomImage image;
        Toolbar toolbar;
    }

    @Override
    public void onBackPressed() {
        ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);
        if (taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            Intent intent = new Intent(EventDetailActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }
}
