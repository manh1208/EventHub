package com.linhv.eventhub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.activity.EventDetailActivity;
import com.linhv.eventhub.activity.MainActivity;
import com.linhv.eventhub.model.UserParticipation;
import com.linhv.eventhub.model.response_model.GetEventDetailResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/27/16.
 */
public class TicketDialog extends Dialog {
    private UserParticipation userParticipation;
    private ViewHolder viewHolder;
    private Context mContext;
    private RestService restService;
    private String userId;

    public TicketDialog(Context context, UserParticipation userParticipation) {
        super(context);
        this.userParticipation = userParticipation;
        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ticket);
        restService = new RestService();
        viewHolder = new ViewHolder();
        userId = DataUtils.getINSTANCE(mContext).getmPreferences().getString(QuickSharePreferences.SHARE_USERID,"");
        viewHolder.ivQRCode = (ImageView) findViewById(R.id.iv_qrCode);
        viewHolder.txtParticipationCode = (TextView) findViewById(R.id.txt_participation_code);
        viewHolder.btnEvent = (Button) findViewById(R.id.btn_goto_event);
        viewHolder.txtParticipationCode.setText(userParticipation.getParticipateCode()+"");
        String url = DataUtils.URL+userParticipation.getQrCodeUrl();
        Picasso.with(mContext).load(Uri.parse(url))
                .placeholder(R.drawable.ic_placeholder_qr)
                .error(R.drawable.ic_placeholder_qr)
                .into(viewHolder.ivQRCode);
        viewHolder.btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restService.getEventService().getEvent(userParticipation.getEventId(), userId, new Callback<GetEventDetailResponseModel>() {
                            @Override
                            public void success(GetEventDetailResponseModel responseModel, Response response) {
                                if (responseModel.isSucceed()){
                                    Intent intent = new Intent(mContext, EventDetailActivity.class);
                                    intent.putExtra("eventId", responseModel.getEvent().getId());
                                    intent.putExtra("eventName",responseModel.getEvent().getName());
                                    mContext.startActivity(intent);
                                    ((MainActivity)mContext).overridePendingTransition(R.anim.right_in,R.anim.left_out);
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });

            }
        });
    }

    private class ViewHolder{
        ImageView ivQRCode;
        TextView txtParticipationCode;
        Button btnEvent;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }
}
