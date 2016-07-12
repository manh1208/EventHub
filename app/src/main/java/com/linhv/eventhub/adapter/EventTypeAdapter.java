package com.linhv.eventhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.custom.SquareImageView;
import com.linhv.eventhub.model.EventType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ManhNV on 7/12/2016.
 */
public class EventTypeAdapter extends ArrayAdapter<EventType> {
    private Context mContext;
    private List<EventType> eventTypes;
    private HashMap<EventType, Boolean> checked;

    public EventTypeAdapter(Context context, int resource, List<EventType> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.eventTypes = objects;
        putChecked();
    }

    private void putChecked() {
        checked = new HashMap<EventType, Boolean>();
        for (EventType item : eventTypes
                ) {
            if (!checked.containsKey(item)) {
                checked.put(item, false);
            }
        }
    }

    @Override
    public int getCount() {
        return eventTypes.size();
    }

    @Override
    public EventType getItem(int position) {
        return eventTypes.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_event_type, parent, false);
            viewHolder.ivImage = (SquareImageView) convertView.findViewById(R.id.iv_event_type_image);
            viewHolder.checked = (ImageView) convertView.findViewById(R.id.iv_event_type_check);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_event_type_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        EventType eventType = getItem(position);
        if (checked.containsKey(eventType)) {
            if (checked.get(eventType)) {
                viewHolder.checked.setVisibility(View.VISIBLE);
                viewHolder.ivImage.setAlpha(0.6f);
            } else {
                viewHolder.checked.setVisibility(View.GONE);
                viewHolder.ivImage.setAlpha(1f);
            }
        }
        viewHolder.ivImage.setImageResource(eventType.getImage());

        viewHolder.txtName.setText(eventType.getName());
        return convertView;
    }

    private class ViewHolder {
        SquareImageView ivImage;
        TextView txtName;
        ImageView checked;
    }

    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
        putChecked();
        notifyDataSetChanged();
    }

    public void setChecked(int position) {
        EventType eventType = getItem(position);
        if (checked.containsKey(eventType) && !checked.get(eventType)) {
            checked.put(eventType, true);
        } else if (checked.containsKey(eventType) && checked.get(eventType)) {
            checked.put(eventType, false);
        }
        notifyDataSetChanged();
    }

    public void setCheckAll(){
        for (EventType item : eventTypes
                ) {
            if (checked.containsKey(item)) {
                checked.put(item, true);
            }
        }
        notifyDataSetChanged();
    }
}
