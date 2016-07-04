package com.linhv.eventhub.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.linhv.eventhub.fragment.AllEventFragment;
import com.linhv.eventhub.fragment.TopEventFragment;

/**
 * Created by ManhNV on 7/5/2016.
 */
public class HomeTabAdapter extends FragmentPagerAdapter {
    public HomeTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new TopEventFragment();
            case 1 : return new AllEventFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Sự kiện nổi bật";
            case 1:
                return "Tất cả sự kiện";
        }
        return null;
    }

}
