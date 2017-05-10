package cn.edu.fudan.xuan.mytimemanagement;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OtherFragment2 extends Fragment {

    private double val = -1;

    public void setVal(double newVal) {
        this.val = newVal;
        ((TextView)(getActivity().findViewById(R.id.uglytext))).setText(String.format(" %.3f ", this.val));
    }

    public OtherFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).ttx2 = (TextView)getView().findViewById(R.id.uglytext);
    }

    @Override
    public void onStart() {
        super.onStart();
        // ...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other2, container, false);
    }

}

