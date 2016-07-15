package com.linhv.eventhub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.utils.DataUtils;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private Context mContext;
    private Event event;

    public ContentAdapter(Context mContext, Event event) {
        this.mContext = mContext;
        this.event = event;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_event_content, parent, false);
        return new ContentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder viewHolder, int position) {
        if (event.getName() != null) {
            viewHolder.txtTime.setText(DataUtils.parseDatetimeAPI(event.getStartDate(),"dd.MM.yyyy"));
            viewHolder.txtLocation.setText(event.getAddress());
            viewHolder.txtDesc.setText(Html.fromHtml(event.getDescription()));
        }
//        if (componentItem.getUrl() != null && componentItem.getUrl().length() > 0) {
//            String url = DataUtils.URL+componentItem.getUrl();
//            Picasso.with(mContext).load(Uri.parse(url))
//                    .placeholder(R.drawable.image_default_avatar)
//                    .error(R.drawable.image_default_avatar)
//                    .into(viewHolder.ivImage);
//            viewHolder.ivImage.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.ivImage.setVisibility(View.GONE);
//        }
//
//        viewHolder.txtName.setText(componentItem.getName());
//        viewHolder.txtDesc.setText(componentItem.getDescription());
//        viewHolder.txtDetail.setText(componentItem.getDetail());

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTime;
        private TextView txtDesc;
        private TextView txtLocation;

        public ContentViewHolder(View convertView) {
            super(convertView);
            txtTime = (TextView) convertView.findViewById(R.id.txt_event_detail_start_date);
            txtDesc = (TextView) convertView.findViewById(R.id.txt_event_detail_description);
            txtLocation = (TextView) convertView.findViewById(R.id.txt_event_detail_location);
//            ivImage = (ImageView) convertView.findViewById(R.id.iv_item_image);
//            txtName = (TextView) convertView.findViewById(R.id.txt_item_name);
//            txtDesc = (TextView) convertView.findViewById(R.id.txt_item_description);
//            txtDetail = (TextView) convertView.findViewById(R.id.txt_item_detail);
        }

    }

    public void setEvent(Event event) {
        this.event = event;
        notifyDataSetChanged();
    }
}
