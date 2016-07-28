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
import com.linhv.eventhub.activity.MainActivity;
import com.linhv.eventhub.custom.RoundedImageView;
import com.linhv.eventhub.model.User;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ManhNV on 7/28/16.
 */
public class ParticipatedUserAdapter extends ArrayAdapter<User> {
    private Context mContext;
    private List<User> users;

    public ParticipatedUserAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        mContext  = context;
        users = objects;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_participated_user,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        viewHolder.txtName.setText(user.getFullName());

        String url = user.getImageUrl();
        if (url!=null){
            if (!url.contains("http")) {
                url = DataUtils.URL + url;
            }
        }else{
            url = "";
        }
        Picasso.with(mContext).load(Uri.parse(url))
                .placeholder(R.drawable.image_default_avatar)
                .error(R.drawable.image_default_avatar)
                .into(viewHolder.ivAvatar);
        return convertView;
    }

    private class ViewHolder{
        RoundedImageView ivAvatar;
        TextView txtName;
        ImageView ivChecked;

        public ViewHolder(View v){
            ivAvatar = (RoundedImageView) v.findViewById(R.id.iv_participated_user_avatar);
            txtName = (TextView) v.findViewById(R.id.txt_participated_user_name);
            ivChecked = (ImageView) v.findViewById(R.id.iv_is_checked_in);
        }
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
}
