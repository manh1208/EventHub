package com.linhv.eventhub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.ActivitiesAdapter;
import com.linhv.eventhub.enumeration.ActivityEnum;
import com.linhv.eventhub.model.Activity;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.response_model.GetActivityPublishedResponseModel;
import com.linhv.eventhub.otto.BusStation;
import com.linhv.eventhub.otto.Message;
import com.linhv.eventhub.otto.NewActivity;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EventActivitiesActivity extends AppCompatActivity {
    private ViewHolder viewHolder;
    private ActivitiesAdapter activitiesAdapter;
    private List<Activity> activities;
    private RestService restService;
    private int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hoạt động");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        loadData();
    }

    private void init() {
        eventId = getIntent().getIntExtra("eventId",-1);
        viewHolder = new ViewHolder();
        activities = new ArrayList<>();
        restService = new RestService();
        viewHolder.lvActivities = (ListView) findViewById(R.id.lv_event_activities);
        activitiesAdapter = new ActivitiesAdapter(this,R.layout.item_event_activity,activities);
        viewHolder.lvActivities.setAdapter(activitiesAdapter);
        viewHolder.lvActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = activitiesAdapter.getItem(position);
                if (activity.getType()== ActivityEnum.Survey.getValue()){
                    Intent intent = new Intent(EventActivitiesActivity.this, SurveyActivity.class);
                    intent.putExtra("activityName",activity.getName());
                    intent.putExtra("activityId",activity.getId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in,R.anim.left_out);
                }
                else if (activity.getType()==ActivityEnum.RemoteMic.getValue()){
                    Intent intent = new Intent(EventActivitiesActivity.this,StreamActivity.class);
                    intent.putExtra("eventId",eventId);
                    intent.putExtra("activityId",activity.getId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in,R.anim.left_out);
                }
            }
        });
    }

    private void loadData(){
//        activities.add(new Activity());
//        activities.add(new Activity());
//        activitiesAdapter.setActivities(activities);
        restService.getEventService().getActivity(eventId, new Callback<GetActivityPublishedResponseModel>() {
            @Override
            public void success(GetActivityPublishedResponseModel responseModel, Response response) {
                    if (responseModel.isSucceed()){
                        if (responseModel.getActivities()!=null && responseModel.getActivities().size()>0){
                            activitiesAdapter.setActivities(responseModel.getActivities());
                        }else{
                            Toast.makeText(EventActivitiesActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(EventActivitiesActivity.this, responseModel.getErrorsString(), Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void failure(RetrofitError error) {
                DataUtils.getINSTANCE(getApplicationContext()).ConnectionError();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private class ViewHolder{
        ListView lvActivities;
        ViewHolder(){

        }
        ViewHolder(View view){
            lvActivities = (ListView) findViewById(R.id.lv_event_activities);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Otto", "Đăng ký");
        BusStation.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Otto", "Hủy đăng ký");
        BusStation.getBus().unregister(this);
    }

    @Subscribe
    public void change(NewActivity newActivity) {
       loadData();
    }

}
