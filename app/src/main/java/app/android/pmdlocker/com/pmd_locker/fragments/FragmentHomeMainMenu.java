package app.android.pmdlocker.com.pmd_locker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xdu.xhin.library.view.ChangeColorItem;
import com.xdu.xhin.library.view.ChangeColorTab;

import org.w3c.dom.Text;

import app.android.pmdlocker.com.pmd_locker.LoginActivity;
import app.android.pmdlocker.com.pmd_locker.MainActivity;
import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.SignupActivity;
import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;


public class FragmentHomeMainMenu extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentHomeMainMenu.class.getName();

    TextView textVUserProfile;
    TextView textVLocations;
    TextView textVBooking;
    TextView textVSafeKeep;
    TextView textVNotification;
    TextView textVDailyDeals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_home_main_menu, null);
            findId(view);
            initDefault();
            firstLoad();
//            ChangeColorTab changeColorTab = (ChangeColorTab) getActivity().findViewById(R.id.change_color_tab);
//            changeColorTab.setVisibility(View.GONE);
            return view;
        } catch (Exception ex) {
            Log.i(TAG, Log.getStackTraceString(ex));
            return null;
        }
    }


    private void findId(View v)
    {
        textVUserProfile = (TextView)v.findViewById(R.id.textVUserProfile);
        textVLocations = (TextView)v.findViewById(R.id.textVLocations);
        textVBooking = (TextView)v.findViewById(R.id.textVBooking);
        textVSafeKeep = (TextView)v.findViewById(R.id.textVSafekeep);
        textVNotification = (TextView)v.findViewById(R.id.textVNotifications);
        textVDailyDeals = (TextView)v.findViewById(R.id.textVDailyDeals);

        RelativeLayout rlItem;
        rlItem = (RelativeLayout)v.findViewById(R.id.rlUserProfile);
        rlItem.setOnClickListener(this);
        rlItem = (RelativeLayout)v.findViewById(R.id.rlLocations);
        rlItem.setOnClickListener(this);
        rlItem = (RelativeLayout)v.findViewById(R.id.rlBooking);
        rlItem.setOnClickListener(this);
        rlItem = (RelativeLayout)v.findViewById(R.id.rlBooking);
        rlItem.setOnClickListener(this);
        rlItem = (RelativeLayout)v.findViewById(R.id.rlNotification);
        rlItem.setOnClickListener(this);
        rlItem = (RelativeLayout)v.findViewById(R.id.rlDailyDeal);
        rlItem.setOnClickListener(this);

        textVUserProfile.setOnClickListener(this);
        textVLocations.setOnClickListener(this);
        textVBooking.setOnClickListener(this);
        textVSafeKeep.setOnClickListener(this);
        textVDailyDeals.setOnClickListener(this);
        textVNotification.setOnClickListener(this);
    }
    private void initDefault()
    {
        textVUserProfile.setText(Utility.getTextHtml(R.string.text_user_profile,getActivity()));
        textVLocations.setText(Utility.getTextHtml(R.string.text_locations,getActivity()));
        textVBooking.setText(Utility.getTextHtml(R.string.text_book_a_locker,getActivity()));
        textVSafeKeep.setText(Utility.getTextHtml(R.string.text_SAFEKEEP,getActivity()));
        textVNotification.setText(Utility.getTextHtml(R.string.text_notifications_title,getActivity()));
        textVDailyDeals.setText(Utility.getTextHtml(R.string.text_daily_deals,getActivity()));
    }
    private void firstLoad()
    {


    }

    @Override
    public void onResume()
    {
        super.onResume();
//        ChangeColorTab changeColorTab = (ChangeColorTab) getActivity().findViewById(R.id.change_color_tab);
//        changeColorTab.setVisibility(View.GONE);

    }
    @Override
    public void onStart()
    {
        super.onStart();
//        ChangeColorTab changeColorTab = (ChangeColorTab) getActivity().findViewById(R.id.change_color_tab);
//        changeColorTab.setVisibility(View.GONE);

    }
    public void onClick(View v)
    {
        ChangeColorTab changeColorTab = (ChangeColorTab) getActivity().findViewById(R.id.change_color_tab);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        ViewPager mViewPager = (ViewPager) getActivity().findViewById(R.id.id_viewpager);
        FrameLayout fl =  (FrameLayout) getActivity().findViewById(R.id.frameContent);
        switch (v.getId())
        {
            case R.id.textVUserProfile:
            case R.id.rlUserProfile:
                mViewPager.setCurrentItem(ConstantGlobalVariable.POS_FRAGMENT_USERPROFILE);
                ((MainActivity)getActivity()).updateStatusBar(ConstantGlobalVariable.POS_FRAGMENT_USERPROFILE);
//                getFragmentManager().beginTransaction().remove(this).commit();
                Fragment fragmentAccount = fm.findFragmentByTag(FragmentAccount.class.getName());
                if(fragmentAccount==null) {
                 Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentAccount.class.getName());
                    ManagerChangeFragment.addFragment(fm, fragment, R.id.frameContent, FragmentHomeMainMenu.class.getName());
                }
                if(fl!=null)
                    fl.setVisibility(View.GONE);
                changeColorTab.setVisibility(View.VISIBLE);
                break;
            case R.id.textVLocations:
            case R.id.rlLocations:
                mViewPager.setCurrentItem(ConstantGlobalVariable.POS_FRAGMENT_LOCATION);
                ((MainActivity)getActivity()).updateStatusBar(ConstantGlobalVariable.POS_FRAGMENT_LOCATION);
                Fragment fragmentHistory = fm.findFragmentByTag(FragmentLocation.class.getName());
                if(fragmentHistory==null) {
                    Fragment fragmentLocation = ManagerChangeFragment.createFragment(fm, FragmentLocation.class.getName());
                    ManagerChangeFragment.addFragment(fm, fragmentLocation, R.id.frameContent, FragmentHomeMainMenu.class.getName());
                }
                else
                {

                }

                if(fl!=null)
                    fl.setVisibility(View.GONE);
                changeColorTab.setVisibility(View.VISIBLE);
                break;
            case R.id.textVBooking:
            case R.id.rlBooking:
                mViewPager.setCurrentItem(ConstantGlobalVariable.POS_FRAGMENT_BOOKING);
                ((MainActivity)getActivity()).updateStatusBar(ConstantGlobalVariable.POS_FRAGMENT_BOOKING);
                break;
            case R.id.textVSafekeep:
            case R.id.rlWallet:
                mViewPager.setCurrentItem(ConstantGlobalVariable.POS_FRAGMENT_WALLET);
                ((MainActivity)getActivity()).updateStatusBar(ConstantGlobalVariable.POS_FRAGMENT_WALLET);

                Fragment fragmentBooking = fm.findFragmentByTag(FragmentBooking.class.getName());
                if(fragmentBooking==null) {
                    fragmentBooking = ManagerChangeFragment.createFragment(fm, FragmentBooking.class.getName());
                    ManagerChangeFragment.addFragment(fm, fragmentBooking, R.id.frameContent, FragmentHomeMainMenu.class.getName());
                }
                if(fl!=null)
                    fl.setVisibility(View.GONE);
                changeColorTab.setVisibility(View.VISIBLE);

                break;
            case R.id.textVNotifications:
            case R.id.rlNotification:
                mViewPager.setCurrentItem(ConstantGlobalVariable.POS_FRAGMENT_NOTIFICATIONS);
                ((MainActivity)getActivity()).updateStatusBar(ConstantGlobalVariable.POS_FRAGMENT_NOTIFICATIONS);
                Fragment fragmentNotification = fm.findFragmentByTag(FragmentNotifications.class.getName());
                if(fragmentNotification==null) {
                    Fragment fragmentNofications = ManagerChangeFragment.createFragment(fm, FragmentNotifications.class.getName());
                    ManagerChangeFragment.addFragment(fm, fragmentNofications, R.id.frameContent, FragmentHomeMainMenu.class.getName());
                }
                if(fl!=null)
                    fl.setVisibility(View.GONE);
                changeColorTab.setVisibility(View.VISIBLE);
                break;
            case R.id.textVDailyDeals:
            case R.id.rlDailyDeal:
                mViewPager.setCurrentItem(ConstantGlobalVariable.POS_FRAGMENT_DAILY_DEALS);
                ((MainActivity)getActivity()).updateStatusBar(ConstantGlobalVariable.POS_FRAGMENT_DAILY_DEALS);

                Fragment fragmentDailyDeal = fm.findFragmentByTag(FragmentDeal.class.getName());
                if(fragmentDailyDeal==null) {
                    fragmentDailyDeal = ManagerChangeFragment.createFragment(fm, FragmentDeal.class.getName());
                    ManagerChangeFragment.addFragment(fm, fragmentDailyDeal, R.id.frameContent, FragmentHomeMainMenu.class.getName());
                }
                if(fl!=null)
                    fl.setVisibility(View.GONE);
                changeColorTab.setVisibility(View.VISIBLE);

                break;
        }
    }


}	

