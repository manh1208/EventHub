package com.linhv.eventhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.custom.CustomImage;
import com.linhv.eventhub.custom.CustomTextViewLight;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.utils.DataUtils;

import java.util.List;

/**
 * Created by ManhNV on 7/5/2016.
 */
public class EventAdapter extends ArrayAdapter<Event> implements View.OnClickListener {
    private Context mContext;
    private List<Event> mEvents;

    public EventAdapter(Context context, int resource, List<Event> events) {
        super(context, resource, events);
        mContext = context;
        mEvents = events;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Event getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_event, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivHot = (ImageView) convertView.findViewById(R.id.iv_event_hot);
            viewHolder.ivCoverEvent = (CustomImage) convertView.findViewById(R.id.iv_event_cover);
            viewHolder.tvEventName = (CustomTextViewLight) convertView.findViewById(R.id.tv_top_event_name);
            viewHolder.tvEventDate = (TextView) convertView.findViewById(R.id.tv_top_event_date_time);
            viewHolder.tvEventType = (TextView) convertView.findViewById(R.id.tv_top_event_type);
            viewHolder.tvEventLocation = (TextView) convertView.findViewById(R.id.tv_top_event_location);
            viewHolder.btnSave = (ImageButton) convertView.findViewById(R.id.btn_top_event_save);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Event event = getItem(position);


        viewHolder.btnSave.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        DataUtils.getAlphaAmination(v);
        int id = v.getId();
        switch (id) {
            case R.id.btn_top_event_save:
                Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class ViewHolder {
        ImageView ivHot;
        CustomImage ivCoverEvent;
        CustomTextViewLight tvEventName;
        TextView tvEventDate;
        TextView tvEventType;
        TextView tvEventLocation;
        ImageButton btnSave;
    }

    public void setEvents(List<Event> events) {
        this.mEvents = events;
        notifyDataSetChanged();
    }
}
