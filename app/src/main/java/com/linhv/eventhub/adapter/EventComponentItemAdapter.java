package com.linhv.eventhub.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.model.ComponentItem;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class EventComponentItemAdapter extends ArrayAdapter<ComponentItem> {
    private Context mContext;
    private List<ComponentItem> componentItems;

    public EventComponentItemAdapter(Context context, int resource, List<ComponentItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.componentItems = objects;
    }

    @Override
    public int getCount() {
        return componentItems.size();
    }

    @Override
    public ComponentItem getItem(int position) {
        return componentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return componentItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_component_item, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_item_image);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_item_name);
            viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.txt_item_description);
            viewHolder.txtDetail = (TextView) convertView.findViewById(R.id.txt_item_detail);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ComponentItem componentItem = getItem(position);
        if (componentItem.getUrl() != null && componentItem.getUrl().length() > 0) {
            String url = DataUtils.URL+componentItem.getUrl();
            Picasso.with(mContext).load(Uri.parse(url))
                    .placeholder(R.drawable.image_default_avatar)
                    .error(R.drawable.image_default_avatar)
                    .into(viewHolder.ivImage);
            viewHolder.ivImage.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivImage.setVisibility(View.GONE);
        }

        viewHolder.txtName.setText(componentItem.getName());
        viewHolder.txtDesc.setText(componentItem.getDescription());
        viewHolder.txtDetail.setText(componentItem.getDetail());
        return convertView;
    }


    public void setComponentItems(List<ComponentItem> componentItems) {
        this.componentItems = componentItems;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView ivImage;
        TextView txtName;
        TextView txtDesc;
        TextView txtDetail;
    }
}

