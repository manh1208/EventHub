package com.linhv.eventhub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.EventAdapter;
import com.linhv.eventhub.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 7/5/2016.
 */
public class AllEventFragment extends Fragment {
    private ViewHolder viewHolder;
    private List<Event> mEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_event, container, false);
        init(v);
        return v;
    }


    private void init(View v) {
        viewHolder = new ViewHolder();
        viewHolder.lvEvents = (ListView) v.findViewById(R.id.lv_all_event_list);
        mEvents = loadEvent();
        EventAdapter eventAdapter = new EventAdapter(getActivity(), R.layout.item_list_event, mEvents);
        viewHolder.lvEvents.setAdapter(eventAdapter);
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

    private final class ViewHolder {
        ListView lvEvents;
    }
}
