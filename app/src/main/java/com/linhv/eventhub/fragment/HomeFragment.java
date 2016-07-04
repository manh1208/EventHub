package com.linhv.eventhub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.HomeTabAdapter;

/**
 * Created by ManhNV on 7/5/2016.
 */
public class HomeFragment extends Fragment {
    private ViewHolder viewHolder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        init(v);

        return v;
    }

    private void init(View v) {
        viewHolder = new ViewHolder();
        viewHolder.tabLayout = (TabLayout) v.findViewById(R.id.tabs_home);
        viewHolder.viewPager = (ViewPager) v.findViewById(R.id.viewpager_home);
        viewHolder.viewPager.setAdapter(new HomeTabAdapter(getChildFragmentManager()));
        viewHolder.tabLayout.post(new Runnable() {
            @Override
            public void run() {
                viewHolder.tabLayout.setupWithViewPager(viewHolder.viewPager);
            }
        });
    }


    private final class ViewHolder{
        ViewPager viewPager;
        TabLayout tabLayout;
    }
}
