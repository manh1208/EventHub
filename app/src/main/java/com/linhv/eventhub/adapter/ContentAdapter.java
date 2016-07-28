package com.linhv.eventhub.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.linhv.eventhub.R;
import com.linhv.eventhub.activity.BuyTicketActivity;
import com.linhv.eventhub.activity.EventDetailActivity;
import com.linhv.eventhub.activity.ParticipantsActivity;
import com.linhv.eventhub.dialog.OrganizerDialog;
import com.linhv.eventhub.dialog.TicketDialog;
import com.linhv.eventhub.model.Event;
import com.linhv.eventhub.model.Ticket;
import com.linhv.eventhub.model.UserParticipation;
import com.linhv.eventhub.model.request_model.JoinEventFreeRequestModel;
import com.linhv.eventhub.model.request_model.RateEventRequestMode;
import com.linhv.eventhub.model.response_model.CheckEventOfUserResponseModel;
import com.linhv.eventhub.model.response_model.GetTicketsResponseModel;
import com.linhv.eventhub.model.response_model.JoinEventFreeResponseModel;
import com.linhv.eventhub.model.response_model.RateEventResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder>  implements View.OnClickListener{
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
            if (DataUtils.getINSTANCE(mContext).getmPreferences().getBoolean(QuickSharePreferences.SHARE_IS_ORGANIZER,false)){
                restService.getEventService().checkEventOfUser(userId, event.getId(), new Callback<CheckEventOfUserResponseModel>() {
                    @Override
                    public void success(CheckEventOfUserResponseModel responseModel, Response response) {
                        if (responseModel.isSucceed()){
                            if (responseModel.isBelong()){
                                viewHolder.btnParticipant.setVisibility(View.VISIBLE);
                                viewHolder.btnJoin.setVisibility(View.GONE);
                            }else{
                                viewHolder.btnParticipant.setVisibility(View.GONE);
                                viewHolder.btnJoin.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }else{
                viewHolder.btnParticipant.setVisibility(View.GONE);
                viewHolder.btnJoin.setVisibility(View.VISIBLE);
            }
            viewHolder.btnJoin.setEnabled(false);
            viewHolder.btnJoin.setOnClickListener(ContentAdapter.this);
            restService.getEventService().getUserParticipation(userId, event.getId(), new Callback<JoinEventFreeResponseModel>() {
                @Override
                public void success(JoinEventFreeResponseModel responseModel, Response response) {
                    if (responseModel.isSucceed()){
                        viewHolder.btnJoin.setEnabled(true);
                        viewHolder.btnJoin.setTag(responseModel.getUserParticipation());
                        if (responseModel.getUserParticipation()!=null){
                            viewHolder.btnJoin.setText("Xem vé");
                        }else{
                            if (event.isFree()) {
                                viewHolder.btnJoin.setText("Đăng ký");
                            }else{
                                viewHolder.btnJoin.setText("Mua vé");
                            }
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    DataUtils.getINSTANCE(mContext).ConnectionError();
                }
            });


            viewHolder.btnParticipant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ParticipantsActivity.class);
                    intent.putExtra("eventId",event.getId());
                    mContext.startActivity(intent);
                    
                }
            });


        }


    }


    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_join_event_free:
                final Button btn = (Button) v;
                if (!btn.getText().toString().trim().toUpperCase().equals("Xem vé".trim().toUpperCase())){
                    if (event.isFree()) {
                        restService.getEventService().joinEventFree(new JoinEventFreeRequestModel(userId, event.getId()),
                                new Callback<JoinEventFreeResponseModel>() {


                                    @Override
                                    public void success(JoinEventFreeResponseModel responseModel, Response response) {
                                        if (responseModel.isSucceed()){
                                            Toast.makeText(mContext, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                            btn.setText("Xem vé");
                                            btn.setTag(responseModel.getUserParticipation());
                                        }else{
                                            Toast.makeText(mContext, "Đăng ký không thành công ", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        DataUtils.getINSTANCE(mContext).ConnectionError();
                                    }
                                });
                    }else{

                        createDialog();
                    }
                }else{
                    UserParticipation userParticipation = (UserParticipation) btn.getTag();
                    TicketDialog ticketDialog = new TicketDialog(mContext,userParticipation);
                    ticketDialog.setTitle("Vé của bạn");
                    ticketDialog.show();

                }
                break;
        }
    }

    private void createDialog() {
            restService.getEventService().getTickets(userId, event.getId(), new Callback<GetTicketsResponseModel>() {
                @Override
                public void success( GetTicketsResponseModel responseModel, Response response) {
                    if (responseModel.isSucceed()){
                        final List<Ticket> tickets = responseModel.getTickets();
                        ArrayList<String> ticketName = new ArrayList<String>();
                        for (Ticket item: responseModel.getTickets()
                             ) {
                            ticketName.add(item.getName() +" : "+ item.getPrice());
                        }
                        CharSequence[] array = new CharSequence[ticketName.size()];
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Chọn loại vé")
                                .setItems( ticketName.toArray(array), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(mContext, BuyTicketActivity.class);
                                        intent.putExtra("PaymentLink",tickets.get(which).getPaymentLink());
                                        mContext.startActivity(intent);

                                    }
                                });
                        builder.create().show();
                    }else{
                        Toast.makeText(mContext, responseModel.getErrorsString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    DataUtils.getINSTANCE(mContext).ConnectionError();
                }
            });

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
        private Button btnParticipant;
        private Button btnJoin;

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
            btnParticipant= (Button) convertView.findViewById(R.id.btn_event_participant);
            btnJoin = (Button) convertView.findViewById(R.id.btn_join_event_free);
        }

    }

    public void setEvent(Event event) {
        this.event = event;
        notifyDataSetChanged();
    }
}
