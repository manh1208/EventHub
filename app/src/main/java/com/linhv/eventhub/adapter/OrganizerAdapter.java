package com.linhv.eventhub.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.custom.CustomImage;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.Organizer;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;
import com.squareup.picasso.Picasso;

/**
 * Created by ManhNV on 7/17/2016.
 */
public class OrganizerAdapter extends RecyclerView.Adapter<OrganizerAdapter.OrganizerHolder> {

    private Context mContext;
    private Organizer organizer;
    private RestService restService;
    private String userId;

    public OrganizerAdapter(Context mContext, Organizer organizer) {
        this.mContext = mContext;
        this.organizer = organizer;
        restService = new RestService();
        userId = DataUtils.getINSTANCE(mContext).getmPreferences().getString(QuickSharePreferences.SHARE_USERID, "");
    }


    @Override
    public OrganizerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_event_organizer, parent, false);
        return new OrganizerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrganizerHolder viewHolder, int position) {
        if (organizer.getName() != null) {
            viewHolder.txtName.setText(organizer.getName());
            viewHolder.txtAddress.setText(organizer.getAddress());
            viewHolder.txtHotline.setText(organizer.getPhone());
            viewHolder.txtEmail.setText(organizer.getEmail());
            Picasso.with(mContext).load(Uri.parse(DataUtils.URL+organizer.getImageUrl()))
                    .placeholder(R.drawable.image_default_avatar)
                    .error(R.drawable.image_default_avatar)
                    .into(viewHolder.ivImage);
            if (organizer.getDescription() != null && organizer.getDescription().length() > 0) {
                viewHolder.txtDescription.setVisibility(View.VISIBLE);
                viewHolder.txtDescription.setText(organizer.getDescription());
            } else {
                viewHolder.txtDescription.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class OrganizerHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtAddress;
        private TextView txtHotline;
        private TextView txtEmail;
        private TextView txtDescription;
        private CustomImage ivImage;

        public OrganizerHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txt_event_detail_organizer_name);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_event_detail_organizer_address);
            txtHotline = (TextView) itemView.findViewById(R.id.txt_event_detail_organizer_hotline);
            txtEmail = (TextView) itemView.findViewById(R.id.txt_event_detail_organizer_email);
            txtDescription = (TextView) itemView.findViewById(R.id.txt_event_detail_organizer_description);
            ivImage = (CustomImage) itemView.findViewById(R.id.iv_event_organizer_image);
        }
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
        notifyDataSetChanged();
    }
}

