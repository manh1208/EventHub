package com.linhv.eventhub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.linhv.eventhub.R;
import com.linhv.eventhub.activity.WebViewActivity;
import com.linhv.eventhub.enumeration.ActivityEnum;
import com.linhv.eventhub.model.Activity;
import com.linhv.eventhub.utils.DataUtils;

import java.util.List;

/**
 * Created by ManhNV on 8/2/16.
 */
public class ActivitiesAdapter extends ArrayAdapter<Activity> {
    private Context mContext;
    private List<Activity> activities;

    public ActivitiesAdapter(Context context, int resource, List<Activity> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.activities = objects;
    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Activity getItem(int position) {
        return activities.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_event_activity,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Activity activity = getItem(position);
        viewHolder.txtName.setText(activity.getName());
        viewHolder.txtDesc.setText(activity.getDescription());
        String starttime = DataUtils.parseDatetimeAPI(activity.getStartTime(),"dd/MM/yyyy");
        String endtime= DataUtils.parseDatetimeAPI(activity.getEndTime(),"dd/MM/yyyy");
        viewHolder.txtTime.setText(starttime+" - "+endtime);

        return convertView;
    }

    private class ViewHolder{
        TextView txtName;
        TextView txtDesc;
        TextView txtTime;
        public ViewHolder(View view){
            txtName = (TextView) view.findViewById(R.id.txt_event_activity_name);
            txtDesc = (TextView) view.findViewById(R.id.txt_event_activity_desc);
            txtTime = (TextView) view.findViewById(R.id.txt_event_activity_time);
        }
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
        notifyDataSetChanged();
    }
}
