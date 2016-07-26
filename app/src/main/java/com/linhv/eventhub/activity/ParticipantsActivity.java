package com.linhv.eventhub.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;
import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.ParticipantTabAdapter;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ParticipantsActivity extends AppCompatActivity  implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    private ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkPermissionCamera();
        init();

    }

    private void init(){
        viewHolder = new ViewHolder();
         viewHolder.fab = (FloatingActionButton) findViewById(R.id.fab);
        viewHolder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView = new ZXingScannerView(ParticipantsActivity.this);   // Programmatically initialize the scanner view
                setContentView(mScannerView);
                mScannerView.setResultHandler(ParticipantsActivity.this); // Register ourselves as a handler for scan results.
                mScannerView.startCamera();
            }
        });

        viewHolder.tabLayout = (TabLayout) findViewById(R.id.tabs_participant);
        viewHolder.viewPager = (ViewPager) findViewById(R.id.viewpager_participant);
        viewHolder.viewPager.setAdapter(new ParticipantTabAdapter(getSupportFragmentManager()));
        viewHolder.tabLayout.post(new Runnable() {
            @Override
            public void run() {
                viewHolder.tabLayout.setupWithViewPager(viewHolder.viewPager);
//                tabLayout.getTabAt(1).setIcon(R.drawable.ic_phone);

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
                        new String[]{Manifest.permission.CAMERA},1999);
            }
        }
    }

    @Override
    public void handleResult(Result result) {
        Log.e("handler", result.getText()); // Prints scan results
        Log.e("handler", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }


    private class ViewHolder{
        TabLayout tabLayout;
        ViewPager viewPager;
        FloatingActionButton fab;
    }
}
