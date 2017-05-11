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
    private Handler handler;

    private FragmentOne fragmentOne = new FragmentOne();
    private OtherFragment fragmentTwo = new OtherFragment();

    public TheGridAdapter(FragmentManager fm) {
        // ...?
        super(fm);
    }

    TheGridAdapter(FragmentManager fm, GoogleApiClient googleApiClient, Handler handler) {
        super(fm);
        fragmentOne.setGoogleApiClient(googleApiClient);
        fragmentOne.setParentAdapter(this);
        this.handler = handler;
        fragmentTwo.ff = fragmentOne;
    }

    void callWhenConnected() {
        // ...?
        fragmentOne.callWhenConnected();
    }

    void setmToday(int newval) {
        System.out.println("here is 'setmToday'");
        Message msg = handler.obtainMessage();
        msg.what = -1 * newval;
        //msg.what = -666;
        msg.sendToTarget();
    }

    @Override
    public Fragment getFragment(int row, int col) {
        if(col == 0)
            return fragmentOne;
        if(col == 1)
            return fragmentTwo;
        // never happens
        return null;
    }

    @Override
    public int getRowCount() {
        // ...?
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        // ...?
        return 2;
    }
}

