package com.linhv.eventhub.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.ParticipantTabAdapter;
import com.linhv.eventhub.adapter.ParticipatedUserAdapter;
import com.linhv.eventhub.dialog.UserInfoDialog;
import com.linhv.eventhub.model.ParticipatedUser;
import com.linhv.eventhub.model.User;
import com.linhv.eventhub.model.request_model.CheckInRequestModel;
import com.linhv.eventhub.model.response_model.CheckInResponseModel;
import com.linhv.eventhub.model.response_model.GetParticipatedUsersResponseModel;
import com.linhv.eventhub.model.response_model.GetParticipatedUsesResponseModel;
import com.linhv.eventhub.otto.BusStation;
import com.linhv.eventhub.otto.Message;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ParticipantsActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private ViewHolder viewHolder;
    private ParticipatedUserAdapter participatedUserAdapter;
    private List<ParticipatedUser> users;
    private RestService restService;
    private int eventId;
    private String userId;
    private boolean isStartCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Người tham gia");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isStartCamera = false;
        checkPermissionCamera();
        init();

    }

    private void init() {
        viewHolder = new ViewHolder();
        restService = new RestService();
        eventId = getIntent().getIntExtra("eventId", -1);
        userId = DataUtils.getINSTANCE(this).getmPreferences().getString(QuickSharePreferences.SHARE_USERID, "");
        viewHolder.fab = (FloatingActionButton) findViewById(R.id.fab);
        viewHolder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView = new ZXingScannerView(ParticipantsActivity.this);   // Programmatically initialize the scanner view
                setContentView(mScannerView);
                mScannerView.setResultHandler(ParticipantsActivity.this); // Register ourselves as a handler for scan results.
                mScannerView.startCamera();
                isStartCamera = true;
            }
        });
//        viewHolder.test = (Button) findViewById(R.id.btn_test);
        viewHolder.lvUser = (ListView) findViewById(R.id.lv_participated_users);
        users = new ArrayList<>();
        participatedUserAdapter = new ParticipatedUserAdapter(this, R.layout.item_participated_user, users);
        viewHolder.lvUser.setAdapter(participatedUserAdapter);
//        viewHolder.tabLayout = (TabLayout) findViewById(R.id.tabs_participant);
//        viewHolder.viewPager = (ViewPager) findViewById(R.id.viewpager_participant);
//        viewHolder.viewPager.setAdapter(new ParticipantTabAdapter(getSupportFragmentManager()));
//        viewHolder.tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                viewHolder.tabLayout.setupWithViewPager(viewHolder.viewPager);
////                tabLayout.getTabAt(1).setIcon(R.drawable.ic_phone);
//
//            }
//        });

        loadData();
    }

    private void loadData() {
        restService.getEventService().getParticipatedUsers(eventId, new Callback<GetParticipatedUsersResponseModel>() {
            @Override
            public void success(GetParticipatedUsersResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()) {
                    if (responseModel.getParticipatedUser().size() > 0) {
                        participatedUserAdapter.setUsers(responseModel.getParticipatedUser());
                    } else {
                        Toast.makeText(ParticipantsActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                DataUtils.getINSTANCE(ParticipantsActivity.this).ConnectionError();
            }
        });
    }

    private void checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, 1999);
            }
        }
    }

    @Override
    public void handleResult(Result result) {
        Log.e("handler", result.getText()); // Prints scan results
        Log.e("handler", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(result.getText());
//        AlertDialog alert1 = builder.create();
//        alert1.show();
        try {
            int code = Integer.parseInt(result.getText());

            restService.getEventService().checkInEvent(new CheckInRequestModel(userId, eventId, code), new Callback<CheckInResponseModel>() {
                @Override
                public void success(CheckInResponseModel responseModel, Response response) {
                    if (responseModel.isSucceed()) {
                        UserInfoDialog dialog = new UserInfoDialog(ParticipantsActivity.this, responseModel.getUser());
                        dialog.setTitle("Thông tin người tham gia");
                        dialog.show();
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                onBackPressed();
                                loadData();
                            }
                        });

//                    if (responseModel.isSuccessfull()){
//                        Toast.makeText(ParticipantsActivity.this, "Checked in", Toast.LENGTH_SHORT).show();
//
//                    }else{
//                        Toast.makeText(ParticipantsActivity.this, "Checked in Fail"+responseModel.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
                    } else {
                        Toast.makeText(ParticipantsActivity.this, responseModel.getErrorsString(), Toast.LENGTH_SHORT).show();
                        recreate();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    DataUtils.getINSTANCE(ParticipantsActivity.this).ConnectionError();
                }
            });
        } catch (Exception e) {
            Toast.makeText(ParticipantsActivity.this, "QR Code không hợp lệ", Toast.LENGTH_SHORT).show();
            recreate();
        }

    }


    private class ViewHolder {
        ListView lvUser;
        //        Button test;
//        TabLayout tabLayout;
//        ViewPager viewPager;
        FloatingActionButton fab;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (isStartCamera)
            recreate();
        else
            super.onBackPressed();
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Bus", "regist bus");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
