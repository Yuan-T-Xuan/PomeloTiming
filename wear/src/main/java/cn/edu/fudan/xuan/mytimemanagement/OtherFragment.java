package cn.edu.fudan.xuan.mytimemanagement;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OtherFragment extends Fragment {

    public FragmentOne ff;

    private int val = -1;

    public void setVal(int newVal) {
        this.val = newVal;
        ((TextView)getActivity().findViewById(R.id.thetext1)).setText(" " + val + " ");
    }

    public OtherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).ttx1 = (TextView)getView().findViewById(R.id.thetext1);
    }

    @Override
    public void onStart() {
        super.onStart();
        ff.sendWatchConnectedMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false);
    }

}

