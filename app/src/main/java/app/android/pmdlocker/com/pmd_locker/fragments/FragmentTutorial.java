package app.android.pmdlocker.com.pmd_locker.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import app.android.pmdlocker.com.pmd_locker.LoginActivity;
import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.SignupActivity;
import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;


public class FragmentTutorial extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentTutorial.class.getName();
    ViewPager pagerTutorial;
    CirclePageIndicator circlePageIndicator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_tutorial, null);
            findId(view);
            initDefault();
            firstLoad();

            return view;
        } catch (Exception ex) {
            Log.i(TAG, Log.getStackTraceString(ex));
            return null;
        }
    }


    private void findId(View v)
    {
        pagerTutorial = (ViewPager)v.findViewById(R.id.pagerTutorial);
        circlePageIndicator = (CirclePageIndicator)v.findViewById(R.id.indicatorTutorial);


        PagerViewTutorialFragment mAdapter = new PagerViewTutorialFragment(getFragmentManager(),getActivity());
        pagerTutorial.setAdapter(mAdapter);
        circlePageIndicator.setViewPager(pagerTutorial);
        mAdapter.notifyDataSetChanged();
        circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == PagerViewTutorialFragment.ARR_DRAW_TUTORIAL.length-1)
                {
                    pagerTutorial.setOnTouchListener(new View.OnTouchListener()
                    {
                        @Override
                        public boolean onTouch(View v, MotionEvent event)
                        {
                            return true;
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentManager fm = getFragmentManager();
                            Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentMainMenu.class.getName());
                            ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
                        }
                    },1500);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    Utility.saveKeyInt(ConstantGlobalVariable.SharedPreferencesKey.KEY_SHOW_TUTORIAL,0,sharedPreferences);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initDefault()
    {

    }
    private void firstLoad()
    {


    }
    public void onClick(View v)
    {
        switch (v.getId())
        {

        }
    }


}	

