package com.linhv.eventhub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.custom.CustomImage;
import com.linhv.eventhub.model.Organizer;
import com.linhv.eventhub.model.response_model.GetOrganizerResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/17/2016.
 */
public class OrganizerDialog extends Dialog {
    private Context mContext;
    private OrganizerHolder organizerHolder;
    private RestService restService;
    private int eventId;

    public OrganizerDialog(Context context, int eventId) {
        super(context);
        this.eventId = eventId;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_event_organizer);
        organizerHolder = new OrganizerHolder();
        organizerHolder.txtName = (TextView) findViewById(R.id.txt_event_detail_organizer_name);
        organizerHolder.txtAddress = (TextView) findViewById(R.id.txt_event_detail_organizer_address);
        organizerHolder.txtHotline = (TextView) findViewById(R.id.txt_event_detail_organizer_hotline);
        organizerHolder.txtEmail = (TextView) findViewById(R.id.txt_event_detail_organizer_email);
        organizerHolder.txtDescription = (TextView) findViewById(R.id.txt_event_detail_organizer_description);
        organizerHolder.ivImage = (CustomImage) findViewById(R.id.iv_event_organizer_image);
        organizerHolder.btnClose = (Button) findViewById(R.id.btn_event_organizer_close);
        organizerHolder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        restService = new RestService();
        restService.getEventService().getOrganizer(eventId, new Callback<GetOrganizerResponseModel>() {
            @Override
            public void success(GetOrganizerResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()) {
                    Organizer organizer = responseModel.getOrganizer();
                    organizerHolder.txtName.setText(organizer.getName());
                    organizerHolder.txtAddress.setText(organizer.getAddress());
                    organizerHolder.txtHotline.setText(organizer.getPhone());
                    organizerHolder.txtEmail.setText(organizer.getEmail());
                    Picasso.with(mContext).load(Uri.parse(DataUtils.URL + organizer.getImageUrl()))
                            .placeholder(R.drawable.image_default_avatar)
                            .error(R.drawable.image_default_avatar)
                            .into(organizerHolder.ivImage);
                    if (organizer.getDescription() != null && organizer.getDescription().length() > 0) {
                        organizerHolder.txtDescription.setVisibility(View.VISIBLE);
                        organizerHolder.txtDescription.setText(organizer.getDescription());
                    } else {
                        organizerHolder.txtDescription.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }

    private class OrganizerHolder {
        TextView txtName;
        TextView txtAddress;
        TextView txtHotline;
        TextView txtEmail;
        TextView txtDescription;
        CustomImage ivImage;
        Button btnClose;


    }
}
