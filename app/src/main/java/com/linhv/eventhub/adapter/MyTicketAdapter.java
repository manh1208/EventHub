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
import com.linhv.eventhub.model.Tickets;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ManhNV on 8/11/16.
 */
public class MyTicketAdapter extends ArrayAdapter<Tickets> {
    private Context mContext;
    private List<Tickets> tickets;

    public MyTicketAdapter(Context context, int resource, List<Tickets> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.tickets = objects;
    }

    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public Tickets getItem(int position) {
        return tickets.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_ticket, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(getItem(position).getName());
        String url = getItem(position).getImageUrl();
        if (url!=null){
            if (!url.contains("http")) {
                url = DataUtils.URL + url;
            }
        }else{
            url = "";
        }

        Picasso.with(mContext).load(Uri.parse(url))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(viewHolder.imageView);
        return convertView;
    }

    private class ViewHolder {
        TextView textView;
        ImageView imageView;
        ViewHolder(View v) {
            textView = (TextView) v.findViewById(R.id.txt_myticket_event_name);
            imageView = (ImageView) v.findViewById(R.id.iv_myticket_image);
        }
    }

    public void setTickets(List<Tickets> tickets) {
        this.tickets = tickets;
        notifyDataSetChanged();
    }
}
