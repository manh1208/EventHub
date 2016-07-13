package com.linhv.eventhub.activity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.dialog.EventTimeDialog;
import com.linhv.eventhub.dialog.EventTypeDialog;
import com.linhv.eventhub.listener.IEventTimeListener;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity  implements View.OnClickListener, IEventTimeListener{

    private ViewHolder viewHolder;
    private List<Event> mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lọc");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getActionBar();
        init();



    }

    private void init() {
        viewHolder = new ViewHolder();
//        viewHolder.btnSearchName = (EditText) findViewById(R.id.btn_search_event_name);
        viewHolder.tvLocation = (TextView) findViewById(R.id.tv_search_location);
        viewHolder.tvType = (TextView) findViewById(R.id.tv_search_type);
        viewHolder.tvDateTime = (TextView) findViewById(R.id.tv_search_date_time);
        viewHolder.btnDone = (Button) findViewById(R.id.btn_search_done);
//        viewHolder.btnSearchName.setOnClickListener(this);
        viewHolder.tvLocation.setOnClickListener(this);
        viewHolder.tvType.setOnClickListener(this);
        viewHolder.tvDateTime.setOnClickListener(this);
        viewHolder.btnDone.setOnClickListener(this);
    }

    private List<Event> loadEvent() {
        List<Event> events = new ArrayList<Event>();
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        events.add(new Event());
        return events;
    }

    @Override
    public void onClick(View v) {
        DataUtils.getAlphaAmination(v);
        int id = v.getId();
        switch (id) {
            case R.id.btn_search_event_name:

                break;
            case R.id.tv_search_location:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final String[] array = getResources().getStringArray(R.array.locations);
                View view = LayoutInflater.from(this).inflate(R.layout.item_event_type,null,false);
                builder.setCustomTitle(LayoutInflater.from(this).inflate(R.layout.view_title_alert_dialog,null,false))
                        .setSingleChoiceItems(array, array.length - 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewHolder.tvLocation.setText(array[which]);
                            }
                        })
                        .setPositiveButton("Chọn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();

                break;
            case R.id.tv_search_type:
                EventTypeDialog eventTypeDialog = new EventTypeDialog(this);
                eventTypeDialog.show();
                break;
            case R.id.tv_search_date_time:
                EventTimeDialog eventTimeDialog = new EventTimeDialog(this);
                eventTimeDialog.show();
                break;
            case R.id.btn_search_done:

                break;
        }

    }

    @Override
    public void getTime(String time) {
        viewHolder.tvDateTime.setText(time);
    }

    private final class ViewHolder {
//        EditText btnSearchName;
        TextView tvLocation;
        TextView tvType;
        TextView tvDateTime;
        Button btnDone;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
