package com.linhv.eventhub.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.linhv.eventhub.fragment.ContentEventFragment;
import com.linhv.eventhub.fragment.EventDetailComponentFragment;
import com.linhv.eventhub.fragment.EventOrganizerFragment;
import com.linhv.eventhub.fragment.TopEventFragment;
import com.linhv.eventhub.model.EventComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class EventDetailTabAdapter extends FragmentPagerAdapter {
    private int eventId;
    private List<EventComponent> components;

    public EventDetailTabAdapter(FragmentManager fragmentManager, int eventId) {
        super(fragmentManager);
        components = new ArrayList<>();
        this.eventId = eventId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ContentEventFragment eventFragment = new ContentEventFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", eventId);
                eventFragment.setArguments(bundle);
                return eventFragment;
//            case 1:
//                EventOrganizerFragment organizerFragment= new EventOrganizerFragment();
//                bundle = new Bundle();
//                bundle.putInt("eventId",eventId);
//                organizerFragment.setArguments(bundle);
//                return organizerFragment;
            default:
                EventDetailComponentFragment fragment = new EventDetailComponentFragment();
                bundle = new Bundle();
                bundle.putInt("componentId", components.get(position - 1).getId());
                fragment.setArguments(bundle);
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return 1 + components.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:

                return "Nội dung";
//            case 1:
//                return "Nhà tổ chức";
            default:
                return components.get(position - 1).getName();
        }

    }

    public void setComponents(List<EventComponent> components) {
        this.components = components;
        notifyDataSetChanged();
    }
}
