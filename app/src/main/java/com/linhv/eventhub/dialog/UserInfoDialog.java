package com.linhv.eventhub.dialog;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.activity.MainActivity;
import com.linhv.eventhub.custom.RoundedImageView;
import com.linhv.eventhub.model.User;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by ManhNV on 8/12/16.
 */
public class UserInfoDialog extends Dialog {
    private Context mContext;
    private Viewholder viewholder;
    private User user;

    public UserInfoDialog(Context context, User user) {
        super(context);
        mContext = context;
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_user_info);

        viewholder = new Viewholder();
        viewholder.fullName.setText(user.getFullName());
        viewholder.userName.setText(user.getUserName());
        String url = user.getImageUrl();
        if (url != null) {
            if (!url.contains("http")) {
                url = DataUtils.URL + url;
            }
        } else {
            url = "";
        }
        Picasso.with(mContext).load(Uri.parse(url))
                .placeholder(R.drawable.image_default_avatar)
                .error(R.drawable.image_default_avatar)
                .into(viewholder.imageView);
        viewholder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class Viewholder {
        RoundedImageView imageView;
        TextView fullName;
        TextView userName;
        Button button;

        Viewholder() {
            imageView = (RoundedImageView) findViewById(R.id.iv_user_info_avatar);
            fullName = (TextView) findViewById(R.id.txt_user_info_fullname);
            userName = (TextView) findViewById(R.id.txt_user_info_username);
            button = (Button) findViewById(R.id.btn_close);
        }
    }
}
