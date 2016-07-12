package com.linhv.eventhub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.EventTypeAdapter;
import com.linhv.eventhub.model.EventType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 7/12/2016.
 */
public class EventTypeDialog extends Dialog {
    private ViewHolder viewHolder;
    private EventTypeAdapter eventTypeAdapter;
    private List<EventType> eventTypes;
    private Context context;

    public EventTypeDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_type_of_event);
        viewHolder = new ViewHolder();
        eventTypes = new ArrayList<>();
        viewHolder.gvEventType = (GridView) findViewById(R.id.gv_type_of_event);
        eventTypeAdapter = new EventTypeAdapter(context,R.layout.item_event_type,eventTypes);
        viewHolder.gvEventType.setAdapter(eventTypeAdapter);
        viewHolder.gvEventType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventTypeAdapter.setChecked(position);
            }
        });
        getList();
        viewHolder.btnCheckAll = (Button) findViewById(R.id.btn_event_type_check_all);
        viewHolder.btnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventTypeAdapter.setCheckAll();
            }
        });
        viewHolder.btnSubmit = (Button) findViewById(R.id.btn_event_type_submit);
        viewHolder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void getList() {
        eventTypes.add(new EventType(R.drawable.meeting,"Hội thảo"));
        eventTypes.add(new EventType(R.drawable.meeting,"Giải Trí"));
        eventTypes.add(new EventType(R.drawable.meeting,"Triển Lãm"));
        eventTypes.add(new EventType(R.drawable.meeting,"Thể Thao"));
        eventTypes.add(new EventType(R.drawable.meeting,"Văn Hóa"));
        eventTypes.add(new EventType(R.drawable.meeting,"Học tập"));
        eventTypeAdapter.setEventTypes(eventTypes);

    }

    private class ViewHolder{
        GridView gvEventType;
        Button btnCheckAll;
        Button btnSubmit;
    }
}
