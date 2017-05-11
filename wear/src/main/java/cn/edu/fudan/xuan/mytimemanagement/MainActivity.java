package cn.edu.fudan.xuan.mytimemanagement;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

    private GridViewPager pager;
    private TheGridAdapter adapter;
    private GoogleApiClient mGoogleApiClient;

    public TextView ttx1;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what <= 0)
                ttx1.setText(" " + (-1 * msg.what) + " ");
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


        setContentView(R.layout.activity_main);
        setAmbientEnabled();
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener((_stub) -> {
            pager = (GridViewPager)findViewById(R.id.thegrid);
            adapter = new TheGridAdapter(getFragmentManager(), mGoogleApiClient, this.mHandler);
            pager.setAdapter(adapter);
        });

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        adapter.callWhenConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // nothing ...
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // nothing ...
    }
}

