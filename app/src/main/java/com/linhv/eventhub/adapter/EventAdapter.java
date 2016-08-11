package com.linhv.eventhub.adapter;

import android.content.Context;
import android.net.Uri;
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
import com.linhv.eventhub.model.response_model.FollowEventResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/5/2016.
 */
public class EventAdapter extends ArrayAdapter<Event> implements View.OnClickListener {
    private Context mContext;
    private List<Event> mEvents;
    private String userId;
    private RestService restService;
    private boolean isSaved;
    private boolean isOwnerEvent;

    public EventAdapter(Context context, int resource, List<Event> events, boolean isSaved,boolean isOwnerEvent) {
        super(context, resource, events);
        mContext = context;
        mEvents = events;
        userId = DataUtils.getINSTANCE(mContext).getmPreferences().getString(QuickSharePreferences.SHARE_USERID, "");
        restService = new RestService();
        this.isSaved = isSaved;
        this.isOwnerEvent  = isOwnerEvent;
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
//            viewHolder.tvEventLocation = (TextView) convertView.findViewById(R.id.tv_top_event_location);
//            viewHolder.btnSave = (ImageButton) convertView.findViewById(R.id.btn_top_event_save);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        if (isOwnerEvent){
//            viewHolder.btnSave.setVisibility(View.GONE);
//        }else{
//            viewHolder.btnSave.setVisibility(View.VISIBLE);
//        }

        Event event = getItem(position);
        String url = event.getImageUrl().contains("http") ? event.getImageUrl() : DataUtils.URL + event.getImageUrl();
        Picasso.with(mContext).load(Uri.parse(url))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(viewHolder.ivCoverEvent);
        if (event.isFree()) {
            viewHolder.ivHot.setVisibility(View.VISIBLE);
            viewHolder.ivHot.setImageResource(R.drawable.ic_free);
        } else {
            viewHolder.ivHot.setVisibility(View.GONE);
        }
        viewHolder.tvEventName.setText(event.getName());
        viewHolder.tvEventDate.setText(DataUtils.parseDatetimeAPI(event.getStartDate(), "dd - MMM - yyyy"));
        viewHolder.tvEventType.setText(event.getCategory());
//        viewHolder.btnSave.setTag(position);
//        viewHolder.btnSave.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        DataUtils.getAlphaAmination(v);
        int id = v.getId();
        switch (id) {
//            case R.id.btn_top_event_save:
//                final int position = (int) v.getTag();
//
//                restService.getUserService().followEvent(getItem(position).getId(), userId, new Callback<FollowEventResponseModel>() {
//                    @Override
//                    public void success(FollowEventResponseModel responseModel, Response response) {
//                        if (responseModel.isSucceed()) {
//                            if (isSaved) {
//                                mEvents.remove(position);
//                                notifyDataSetChanged();
//                            } else {
//                                if (responseModel.getFollow().isActive()) {
//                                    Toast.makeText(mContext, "Event was saved", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(mContext, "Event was unsaved", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Toast.makeText(mContext, error.getResponse().getReason(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                break;
        }
    }

    public void setEventList(List<Event> eventList) {
        this.mEvents = eventList;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        ImageView ivHot;
        CustomImage ivCoverEvent;
        CustomTextViewLight tvEventName;
        TextView tvEventDate;
        TextView tvEventType;
//        TextView tvEventLocation;
//        ImageButton btnSave;
    }

}
