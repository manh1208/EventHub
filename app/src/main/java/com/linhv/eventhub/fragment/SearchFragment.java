package com.linhv.eventhub.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.EventAdapter;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 7/5/2016.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    private ViewHolder viewHolder;
    private List<Event> mEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        init(v);
        return v;
    }


    private void init(View v) {
        viewHolder = new ViewHolder();
        viewHolder.btnSearchName = (Button) v.findViewById(R.id.btn_search_event_name);
        viewHolder.tvLocation = (TextView) v.findViewById(R.id.tv_search_location);
        viewHolder.tvType = (TextView) v.findViewById(R.id.tv_search_type);
        viewHolder.tvDateTime = (TextView) v.findViewById(R.id.tv_search_date_time);
        viewHolder.btnDone = (Button) v.findViewById(R.id.btn_search_done);
        viewHolder.btnSearchName.setOnClickListener(this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final String[] array = getResources().getStringArray(R.array.locations);
                builder.setCustomTitle(LayoutInflater.from(getActivity()).inflate(R.layout.view_title_alert_dialog,null,false))
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

                break;
            case R.id.tv_search_date_time:

                break;
            case R.id.btn_search_done:

                break;
        }

    }

    private final class ViewHolder {
        Button btnSearchName;
        TextView tvLocation;
        TextView tvType;
        TextView tvDateTime;
        Button btnDone;
    }
}
