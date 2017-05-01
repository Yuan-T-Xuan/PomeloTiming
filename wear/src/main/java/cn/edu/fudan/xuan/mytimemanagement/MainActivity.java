package cn.edu.fudan.xuan.mytimemanagement;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Random;

public class MainActivity extends WearableActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private CircledImageView mProgress;
    private Button mButtonUpper, mButtonLower;
    private GoogleApiClient mGoogleApiClient;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                mButtonUpper.setText("Working");
                mButtonLower.setText("Resting");
            } else if(msg.what == 2) {
                mButtonUpper.setText("Go Resting");
                mButtonLower.setText("Reset");
            } else if(msg.what == 3) {
                mButtonUpper.setText("Go Working");
                mButtonLower.setText("Reset");
            } else if(msg.what <= 1000) {
                mProgress.setProgress((float)msg.what / 1000);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        setContentView(R.layout.activity_main);
        setAmbientEnabled();
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener((_stub) -> {
            mProgress = (CircledImageView) findViewById(R.id.progressBar_center);
            mButtonLower = (Button) findViewById(R.id.button_lower);
            mButtonUpper = (Button) findViewById(R.id.button_upper);
            mProgress.setProgress(1.0f);
            //
            mButtonUpper.setText("Working");
            mButtonLower.setText("Resting");
            mButtonUpper.setOnClickListener((view) -> {
                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/watch-upper-button");
                putDataMapRequest.getDataMap().putInt("watch-upper-button", new Random().nextInt());
                PutDataRequest request = putDataMapRequest.asPutDataRequest();
                Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                        .setResultCallback(dataItemResult -> Log.d("TIME-MNG", "'watch-upper-button' sent"));
            });
            mButtonLower.setOnClickListener((view) -> {
                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/watch-lower-button");
                putDataMapRequest.getDataMap().putInt("watch-lower-button", new Random().nextInt());
                PutDataRequest request = putDataMapRequest.asPutDataRequest();
                Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                        .setResultCallback(dataItemResult -> Log.d("TIME-MNG", "'watch-lower-button' sent"));
            });
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        sendWatchConnectedMessage();
        Wearable.DataApi.addListener(mGoogleApiClient, dataEvents -> {
            for (DataEvent event : dataEvents) {
                if (event.getType() == DataEvent.TYPE_CHANGED) {
                    DataItem item = event.getDataItem();
                    if (item.getUri().getPath().equals("/watch-button-state")) {
                        DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                        int stateNum = dataMap.getInt("watch-button-state");
                        if (stateNum == 1) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = 1;
                            msg.sendToTarget();
                        } else if (stateNum == 2) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = 2;
                            msg.sendToTarget();
                        } else if (stateNum == 3) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = 3;
                            msg.sendToTarget();
                        }
                    }
                    else if (item.getUri().getPath().equals("/set-progress")) {
                        DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                        float progress = dataMap.getFloat("the-num");
                        //mProgress.setProgress(progress);
                        Message msg = mHandler.obtainMessage();
                        msg.what = (int)(progress * 1000);
                        if(msg.what <= 3)
                            msg.what = 4;
                        msg.sendToTarget();
                    }
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        // nothing ...
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // nothing ...
    }

    public void sendWatchConnectedMessage() {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/watch-connected");
        putDataMapRequest.getDataMap().putInt("watch-connected", new Random().nextInt());
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                .setResultCallback(dataItemResult -> Log.d("TIME-MNG", "'watch-connected' sent"));
    }

}

