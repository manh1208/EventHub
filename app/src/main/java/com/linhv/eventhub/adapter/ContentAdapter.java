package com.linhv.eventhub.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.linhv.eventhub.R;
import com.linhv.eventhub.custom.URLImageParser;
import com.linhv.eventhub.dialog.OrganizerDialog;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.request_model.RateEventRequestMode;
import com.linhv.eventhub.model.response_model.RateEventResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;

import org.w3c.dom.Text;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private Context mContext;
    private Event event;
    private RestService restService;
    private String userId;

    public ContentAdapter(Context mContext, Event event) {
        this.mContext = mContext;
        this.event = event;
        restService = new RestService();
        userId = DataUtils.getINSTANCE(mContext).getmPreferences().getString(QuickSharePreferences.SHARE_USERID, "");
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_event_content, parent, false);
        return new ContentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ContentViewHolder viewHolder, int position) {
        if (event.getName() != null) {
            viewHolder.txtTime.setText(DataUtils.parseDatetimeAPI(event.getStartDate(), "dd.MM.yyyy"));
//            Html.ImageGetter imageGetter = new Html.ImageGetter() {
//                public Drawable getDrawable(String source) {
//                    String path = DataUtils.URL+source;
//                    Drawable d = Drawable.createFromPath(path);
//                    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//                    return d;
//                }
//            };
            viewHolder.txtLocation.setText(event.getAddress());
//            viewHolder.txtDesc.setText(Html.fromHtml(event.getDescription(),new URLImageParser(viewHolder.txtDesc,mContext),null));
            viewHolder.txtDesc.loadUrl(DataUtils.URL + "/mobile/event/" + event.getSeoName());
            viewHolder.txtDesc.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                }
            });
            viewHolder.rbRate.setRating(event.getRate().getUserRate());
            viewHolder.txtRating.setText(event.getRate().getPoint() + "");
            viewHolder.txtNumOfRate.setText(event.getRate().getNumOfRate() + "");
            viewHolder.rbRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    restService.getEventService().rateEvent(new RateEventRequestMode(userId, event.getId(), rating), new Callback<RateEventResponseModel>() {
                        @Override
                        public void success(RateEventResponseModel responseModel, Response response) {
                            if (responseModel.isSucceed()) {
                                event.getRate().setNumOfRate(responseModel.getRate().getNumOfRate());
                                event.getRate().setPoint(responseModel.getRate().getPoint());
                                event.getRate().setUserRate(responseModel.getRate().getUserRate());
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            });
            ShareLinkContent shareLinkContent= new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(DataUtils.URL+"/event/"+event.getSeoName()))
                    .build();
            viewHolder.btnShare.setShareContent(shareLinkContent);
            viewHolder.btnOrganizer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrganizerDialog organizerDialog = new OrganizerDialog(mContext,event.getId());
                    organizerDialog.show();
                    organizerDialog.setCanceledOnTouchOutside(true);
                }
            });
//            viewHolder.btnLike.setObjectIdAndType(DataUtils.URL+"/event/"+event.getSeoName(), LikeView.ObjectType.DEFAULT);
//            viewHolder.btnLike.setLikeViewStyle(LikeView.Style.STANDARD);
//            viewHolder.btnLike.setHorizontalAlignment(LikeView.HorizontalAlignment.CENTER);
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
        private WebView txtDesc;
        private TextView txtLocation;
        private RatingBar rbRate;
        private TextView txtRating;
        private TextView txtNumOfRate;
        private ShareButton btnShare;
        private Button btnOrganizer;
//        private LikeView btnLike;

        public ContentViewHolder(View convertView) {
            super(convertView);
            txtTime = (TextView) convertView.findViewById(R.id.txt_event_detail_start_date);
            txtDesc = (WebView) convertView.findViewById(R.id.wv_detail);
            txtLocation = (TextView) convertView.findViewById(R.id.txt_event_detail_location);
            rbRate = (RatingBar) convertView.findViewById(R.id.content_rating);
            txtRating = (TextView) convertView.findViewById(R.id.txt_content_rating);
            txtNumOfRate = (TextView) convertView.findViewById(R.id.txt_num_of_rate);
            btnShare = (ShareButton) convertView.findViewById(R.id.btn_share_facebook);
            btnOrganizer = (Button) convertView.findViewById(R.id.btn_event_organizer);
//            btnLike = (LikeView) convertView.findViewById(R.id.btn_like_facebook);
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
