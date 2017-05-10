package cn.edu.fudan.xuan.mytimemanagement;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;

class TheGridAdapter extends FragmentGridPagerAdapter {
    private int mToday = 0;
    private double mAverage = 0.0;

    private Handler handler;

    private FragmentOne fragmentOne = new FragmentOne();
    private OtherFragment fragmentTwo = new OtherFragment();
    private OtherFragment2 fragmentThree = new OtherFragment2();

    public TheGridAdapter(FragmentManager fm) {
        super(fm);
    }

    TheGridAdapter(FragmentManager fm, GoogleApiClient googleApiClient, Handler handler) {
        super(fm);
        fragmentOne.setGoogleApiClient(googleApiClient);
        fragmentOne.setParentAdapter(this);
        this.handler = handler;
    }

    void callWhenConnected() {
        fragmentOne.callWhenConnected();
    }

    void setmToday(int newval) {
        System.out.println("here is 'setmToday'");
        this.mToday = newval;
        //fragmentTwo.setVal(this.mToday);
        if((-1 * this.mToday) >= 10000)
            return;
        Message msg = handler.obtainMessage();
        //msg.what = -1 * this.mToday;
        msg.what = -666;
        msg.sendToTarget();
    }

    void setmAverage(double newval) {
        System.out.println("here is 'setmAverage'");
        this.mAverage = newval;
        //fragmentThree.setVal(this.mAverage);
        Message msg = handler.obtainMessage();
        msg.what = (int)(newval * 10000);
        Log.d("This is msg.what", " " + msg.what);
        msg.sendToTarget();
    }

    @Override
    public Fragment getFragment(int row, int col) {
        if(col == 0)
            return fragmentOne;
        if(col == 1)
            return fragmentTwo;
        else
            return fragmentThree;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 3;
    }
}

