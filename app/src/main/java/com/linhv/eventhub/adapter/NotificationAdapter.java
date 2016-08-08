package com.linhv.eventhub.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.model.Notification;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

/**
 * Created by ManhNV on 6/18/2016.
 */
public class NotificationAdapter extends ArrayAdapter<Notification> {
    private Context mContext;
    private List<Notification> mNotifications;

    public NotificationAdapter(Context context, int resource, List<Notification> objects) {
        super(context, resource, objects);
        mContext = context;
        mNotifications = objects;
    }

    @Override
    public int getCount() {
        return mNotifications.size();
    }

    @Override
    public Notification getItem(int position) {
        return mNotifications.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_notification,parent,false);
            viewHolder.txtContent = (TextView) convertView.findViewById(R.id.txt_notification_content);
            viewHolder.layoutNotification = (LinearLayout) convertView.findViewById(R.id.layout_notification);
            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txt_notification_time);
            viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.iv_notification_avatar);
            viewHolder.ivType = (ImageView) convertView.findViewById(R.id.iv_notification_type);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Notification notification = getItem(position);

        viewHolder.txtContent.setText(notification.getTitle());
        try {
            viewHolder.txtTime.setText(DataUtils.getTime(DataUtils.parseDatetimeAPI(notification.getCreatedTime(),"yyyy/MM/dd HH:mm:ss"))+" ago");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String photo = notification.getUserImage()+"";
        Picasso.with(mContext).load(Uri.parse(photo))
                .placeholder(R.drawable.image_default_avatar)
                .error(R.drawable.image_default_avatar)
                .into(viewHolder.ivAvatar);
        if (!getItem(position).isActive()){
            viewHolder.layoutNotification.setBackgroundColor(Color.WHITE);

        }else{
            viewHolder.layoutNotification.setBackgroundColor(mContext.getResources().getColor(R.color.notification));
        }

//        if (notification.getType().toUpperCase().trim().equals("Answer".trim().toUpperCase())){
//            viewHolder.ivType.setImageResource(R.drawable.ic_answer);
//        }else{
//            viewHolder.ivType.setImageResource(R.drawable.ic_upvote);
//        }

        return convertView;
    }

    public void setmNotifications(List<Notification> mNotifications) {
        this.mNotifications = mNotifications;
        notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView txtContent;
        LinearLayout layoutNotification;
        TextView txtTime;
        ImageView ivAvatar;
        ImageView ivType;
    }
}
