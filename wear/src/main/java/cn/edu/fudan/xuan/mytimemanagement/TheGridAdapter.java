package cn.edu.fudan.xuan.mytimemanagement;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import com.google.android.gms.common.api.GoogleApiClient;

public class TheGridAdapter extends FragmentGridPagerAdapter {
    FragmentOne fragmentOne = new FragmentOne();

    public TheGridAdapter(FragmentManager fm) {
        super(fm);
    }

    public TheGridAdapter(FragmentManager fm, GoogleApiClient googleApiClient) {
        super(fm);
        fragmentOne.setGoogleApiClient(googleApiClient);
    }

    public void callWhenConnected() {
        fragmentOne.callWhenConnected();
    }

    @Override
    public Fragment getFragment(int row, int col) {
        if(col == 0)
            return fragmentOne;
        return CardFragment.create("A page", "Page " + col);
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
