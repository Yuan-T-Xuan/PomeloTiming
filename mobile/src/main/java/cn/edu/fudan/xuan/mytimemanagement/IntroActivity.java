package cn.edu.fudan.xuan.mytimemanagement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

public class IntroActivity extends FragmentActivity {
    static final int NUM_ITEMS = 3;
    MyAdapter mAdapter;
    ViewPager mPager;
    Context mContext;
    //int nowPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.introViewPager);
        mPager.setAdapter(mAdapter);
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return ArrayListFragment.newInstance(position);
        }

        @Override//初始化每个item
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            return super.instantiateItem(arg0, arg1);
        }

        @Override//与instantiateitem配套使用
        public void destroyItem(ViewGroup container, int position, Object object) {
            //System.out.println( "position Destory" + position);
            super.destroyItem(container, position, object);
        }

    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;

        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();

            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v=null;

            if(mNum==0){
                v=inflater.inflate(R.layout.myfragment,container,false);
            }else if(mNum==1){
                v=inflater.inflate(R.layout.myfragment2,container,false);
            }else if(mNum==2){
                v=inflater.inflate(R.layout.myfragment3,container,false);
            }else{
                v=inflater.inflate(R.layout.myfragment,container,false);
            }
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onDestroyView(){
            //System.out.println(mNum + "mNumDestory");
            super.onDestroyView();
        }

        @Override
        public void onDestroy(){
            super.onDestroy();
        }
    }
    public void button_Enter(View v){
        finish();
    }
    public void checkbox(View v){
        MainActivity.instance.neverseeagain=false;
    }
}
