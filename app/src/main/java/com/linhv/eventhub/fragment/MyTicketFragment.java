package com.linhv.eventhub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.MyTicketAdapter;
import com.linhv.eventhub.dialog.TicketDialog;
import com.linhv.eventhub.model.Tickets;
import com.linhv.eventhub.model.UserParticipation;
import com.linhv.eventhub.model.response_model.GetMyTicketsResponseModel;
import com.linhv.eventhub.model.response_model.JoinEventFreeResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 8/11/16.
 */
public class MyTicketFragment extends Fragment {
    private ViewHolder viewHolder;
    private MyTicketAdapter adapter;
    private List<Tickets> tickets;
    private RestService restService;
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_ticket,container,false);
        userId = DataUtils.getINSTANCE(getActivity()).getmPreferences().getString(QuickSharePreferences.SHARE_USERID,"");
        viewHolder = new ViewHolder(v);
        restService = new RestService();
        tickets = new ArrayList<Tickets>();
        adapter = new MyTicketAdapter(getActivity(),R.layout.item_my_ticket,tickets);
        viewHolder.lvMyTicket.setAdapter(adapter);
        viewHolder.lvMyTicket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                restService.getEventService().getUserParticipation(userId, adapter.getItem(position).getEventId(), new Callback<JoinEventFreeResponseModel>() {
                    @Override
                    public void success(JoinEventFreeResponseModel responseModel, Response response) {
                        if (responseModel.isSucceed()){
                            UserParticipation userParticipation = responseModel.getUserParticipation();
                            TicketDialog ticketDialog = new TicketDialog(getActivity(),userParticipation);
                            ticketDialog.setTitle("Vé của bạn");
                            ticketDialog.show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        DataUtils.getINSTANCE(getActivity()).ConnectionError();
                    }
                });
            }
        });
        getData();
        return v;
    }

    private void getData() {
        restService.getUserService().getTickets(userId, new Callback<GetMyTicketsResponseModel>() {
            @Override
            public void success(GetMyTicketsResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()){
                    adapter.setTickets(responseModel.getTickets());
                }else{
                    Toast.makeText(getActivity(), "Dữ liệu lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                DataUtils.getINSTANCE(getActivity()).ConnectionError();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class ViewHolder{
        ListView lvMyTicket;

        ViewHolder(View v){
            lvMyTicket = (ListView) v.findViewById(R.id.lv_my_ticket);
        }
    }
}
