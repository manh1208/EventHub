package com.linhv.eventhub.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.linhv.eventhub.R;
import com.linhv.eventhub.model.response_model.RequestRemoteMicResponseModel;
import com.linhv.eventhub.otto.BusStation;
import com.linhv.eventhub.otto.Message;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StreamActivity extends AppCompatActivity {
    private RestService restService;
    private int activityId;
    private int eventId;
    private String userId;
    private String participationCode;
    private Button button;
    private boolean isStart = false;
    private Animation first;
    private Animation second;
    private Animation third;
    public static DatagramSocket socket;
    private int port = 11000;
    AudioRecord recorder;
    private int sampleRate = 44100; // 44100 for music
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    //    private int audioFormat = MediaRecorder.OutputFormat.MPEG_4; //AudioFormat.ENCODING_PCM_16BIT;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
    private boolean status = true;
    private String ip;
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Đặt câu hỏi");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkPermissionCamera();
        userId = DataUtils.getINSTANCE(this).getmPreferences().getString(QuickSharePreferences.SHARE_USERID, "");
        eventId = getIntent().getIntExtra("eventId", -1);
        activityId = getIntent().getIntExtra("activityId", -1);
        isStart = getIntent().getBooleanExtra("start", false);
        restService = new RestService();
        imageView = (ImageView) findViewById(R.id.iv_animation_one);
        imageView1 = (ImageView) findViewById(R.id.iv_animation_two);
        imageView2 = (ImageView) findViewById(R.id.iv_animation_three);
        button = (Button) findViewById(R.id.btn_stream);
        button.setText("Tham gia");
        first = getAnimation();
        second = getAnimation();
        third = getAnimation();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button button1 = (Button) v;
                button1.setEnabled(false);
                if (isStart) {
                    AlertDialog.Builder buider = new AlertDialog.Builder(StreamActivity.this);
                    buider.setTitle("Cảnh báo")
                            .setMessage("Bạn có muốn dừng kết nối không?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    stop();
                                }
                            })
                            .setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    button1.setEnabled(true);
                                }
                            }).create().show();
//                    stop();
                } else {
                    if (button1.getText().toString().trim().toUpperCase().equals("Tham gia".toUpperCase())) {
                        setVisibleImage(View.VISIBLE);
                        imageView.startAnimation(first);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageView1.startAnimation(second);
                            }
                        }, 500);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imageView2.startAnimation(third);
                            }
                        }, 1000);
                        restService.getActivityService().requestRemoteMic(eventId, userId, activityId, new Callback<RequestRemoteMicResponseModel>() {
                            @Override
                            public void success(RequestRemoteMicResponseModel responseModel, Response response) {
                                if (responseModel.isSucceed()) {
                                    button1.setText("Chờ...");

                                } else {
                                    Toast.makeText(StreamActivity.this, "Lỗi rồi", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                DataUtils.getINSTANCE(getApplicationContext()).ConnectionError();
                            }
                        });
                    } else {

                    }
                }
            }
        });
        if (isStart) {
            button.setEnabled(false);
            start();
        } else {
            button.setEnabled(true);
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    imageView.
//                }
//            }
//        }).start();
    }

    private void setVisibleImage(int visible) {
        imageView.setVisibility(visible);
        imageView1.setVisibility(visible);
        imageView2.setVisibility(visible);
    }

//    private void setColorImage(int color) {
//
//        imageView.setback(visible);
//        imageView1.setVisibility(visible);
//        imageView2.setVisibility(visible);
//    }


    private void stopStream() {

        Log.i("Stream", "Stop Stream");
        status = false;
        recorder.release();
        Log.d("VS", "Recorder released");
    }

    private void stop() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setVisibleImage(View.GONE);
                button.setText("Tham gia");
                button.setEnabled(true);
                isStart = false;
                first.cancel();
                second.cancel();
                third.cancel();
                restService.getActivityService().cancelRemoteMic(eventId, userId, activityId, new Callback<RequestRemoteMicResponseModel>() {
                    @Override
                    public void success(RequestRemoteMicResponseModel responseModel, Response response) {
                        if (responseModel.isSucceed()) {
                            Toast.makeText(StreamActivity.this, "Kết thúc kết nối", Toast.LENGTH_SHORT).show();
                        } else {

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        DataUtils.getINSTANCE(getApplicationContext()).ConnectionError();
                    }
                });
                stopStream();
            }
        });

    }

    private void checkPermissionCamera() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, 1999);
            }
        }
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            } else {
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1999);
//            }
//        }
    }

    private AnimationSet getAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setRepeatMode(Animation.RESTART);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        return set;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Otto", "Đăng ký");
        BusStation.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Otto", "Hủy đăng ký");
        BusStation.getBus().unregister(this);
    }

    private void start() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setText("Đã kết nối");
                button.setEnabled(true);
                isStart = true;
                startStream();
            }
        });
    }

    public void startStream() {
        Log.i("Stream", "Start Stream");
        minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
        status = true;
        Thread streamThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    DatagramSocket socket = new DatagramSocket();
                    Log.i("VS", "Socket Created");
                    Log.i("MinBuffSize", minBufSize + "");
                    byte[] buffer = new byte[minBufSize];

                    Log.i("VS", "Buffer created of size " + minBufSize);
                    DatagramPacket packet;

                    final InetAddress destination = InetAddress.getByName(ip);
                    Log.i("VS", "Address retrieved");
//                    port = 11000;

                    recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, minBufSize * 10);
                    Log.d("VS", "Recorder initialized");
                    Log.i("VS", "Recorder initialized");
                    recorder.startRecording();
//                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
//                            +"/reverseme.pcm");
//                    Wave
//                     os = new FileOutputStream(outputFile);
//                     bos = new BufferedOutputStream(os);
//                     dos = new DataOutputStream(bos);
                    while (status == true) {


                        //reading data from MIC into buffer
                        minBufSize = recorder.read(buffer, 0, buffer.length);

                        //putting buffer in the packet
                        packet = new DatagramPacket(buffer, buffer.length, destination, port);

                        socket.send(packet);
//                        for(int i = 0; i < minBufSize;i++) {
//                            dos.writeShort(buffer[i]);
//                        }
                        System.out.println("MinBufferSize: " + minBufSize + ": " + buffer.length + ": Data:" + buffer);
//                        ByteArrayInputStream baiss = new ByteArrayInputStream(
//                                buffer);


                    }


                } catch (UnknownHostException e) {
                    Log.e("VS", "UnknownHostException");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("VS", "IOException");
                }
            }

        });
        streamThread.start();
    }


    @Subscribe
    public void change(Message message) {
        ip = message.getMsg();
        Log.i("Stream", ip);
        Log.i("Otto", "Nhận message " + isStart);
        if (!isStart) {
            start();
        } else {
            stop();
        }
    }

    private void back() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }

    @Override
    public void onBackPressed() {

        if (isStart) {
            AlertDialog.Builder buider = new AlertDialog.Builder(StreamActivity.this);
            buider.setTitle("Cảnh báo")
                    .setMessage("Bạn có muốn dừng kết nối không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stop();
                            back();
                        }
                    })
                    .setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).create().show();
//                    stop();
        }else{
            back();
        }
    }
}
