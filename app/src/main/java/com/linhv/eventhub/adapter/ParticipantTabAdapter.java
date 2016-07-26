package com.linhv.eventhub.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.linhv.eventhub.fragment.AllEventFragment;
import com.linhv.eventhub.fragment.CheckinFragment;
import com.linhv.eventhub.fragment.ParticipantFragment;
import com.linhv.eventhub.fragment.TopEventFragment;

/**
 * Created by ManhNV on 7/25/16.
 */
public class ParticipantTabAdapter extends FragmentPagerAdapter {
    public ParticipantTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ParticipantFragment();
            case 1:
                return new CheckinFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Người chơi đã đăng ";
            case 1:
                return "Người chơi đã checkin";
        }
        return null;
    }
}