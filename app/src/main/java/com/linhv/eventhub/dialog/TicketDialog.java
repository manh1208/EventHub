package com.linhv.eventhub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.model.UserParticipation;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by ManhNV on 7/27/16.
 */
public class TicketDialog extends Dialog {
    private UserParticipation userParticipation;
    private ViewHolder viewHolder;
    private Context mContext;

    public TicketDialog(Context context, UserParticipation userParticipation) {
        super(context);
        this.userParticipation = userParticipation;
        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ticket);

        viewHolder = new ViewHolder();
        viewHolder.ivQRCode = (ImageView) findViewById(R.id.iv_qrCode);
        viewHolder.txtParticipationCode = (TextView) findViewById(R.id.txt_participation_code);
        viewHolder.txtParticipationCode.setText(userParticipation.getParticipateCode()+"");
        String url = DataUtils.URL+userParticipation.getQrCodeUrl();
        Picasso.with(mContext).load(Uri.parse(url))
                .placeholder(R.drawable.ic_placeholder_qr)
                .error(R.drawable.ic_placeholder_qr)
                .into(viewHolder.ivQRCode);
    }

    private class ViewHolder{
        ImageView ivQRCode;
        TextView txtParticipationCode;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }
}
