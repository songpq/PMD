package app.android.pmdlocker.com.pmd_locker.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.apdapters.LockerAdapterRecycleView;
import app.android.pmdlocker.com.pmd_locker.apdapters.NotificationAdapterRecycleView;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.objects.HNotification;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;


public class FragmentNotifications extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentNotifications.class.getName();


    View viewTopBar;
    ScaleImageView sivBackTopBar;
    TextView textVTitleTopBar;
    ScaleImageView sivHomeActionBar;

    TextView textVTitleTop;

    private void initTopBar(View v)
    {
        /*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the view
        final View view = inflater.inflate(R.layout.top_bar, null);
        toolbarFavorite.addView(view);
        */
        viewTopBar = (View)v.findViewById(R.id.topBar);
        sivBackTopBar = (ScaleImageView)v.findViewById(R.id.sivBackActionBar);
        sivBackTopBar.setOnClickListener(this);
        textVTitleTopBar = (TextView)v.findViewById(R.id.textVTitleActionBar);
        textVTitleTopBar.setText(Utility.getTextHtml(R.string.text_notifications_title,getActivity()));
        sivHomeActionBar = (ScaleImageView)v.findViewById(R.id.sivHomeActionBar);
        sivHomeActionBar.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_notifications, null);
            findId(view);
            initDefault();
            firstLoad();

            return view;
        } catch (Exception ex) {
            Log.i(TAG, Log.getStackTraceString(ex));
            return null;
        }
    }

    boolean isLoading = false;
    RecyclerView recyclerView;
    private void findId(View v)
    {
        initTopBar(v);
        textVTitleTop = (TextView)v.findViewById(R.id.textVTitleTop);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycleViewNoti);
    }
    private void initDefault()
    {
        textVTitleTop.setText(Utility.getText(R.string.text_notification_hi,getActivity()).replace("{s}", GlobalVariable.userLogin.getUserName()));

    }
    private List<HNotification> initListNotifications()
    {
        List<HNotification> lst = new ArrayList<>();
        HNotification HNotification ;
        HNotification = new HNotification(R.mipmap.profile_bicycle,Utility.getText(R.string.text_locker_bycicle,getActivity()));
        lst.add(HNotification);
        HNotification = new HNotification(R.mipmap.clip_group_1,Utility.getText(R.string.text_locker_clip1,getActivity()));
        lst.add(HNotification);
        HNotification = new HNotification(R.mipmap.clip_group_2,Utility.getText(R.string.text_locker_clip2,getActivity()));
        lst.add(HNotification);
        return lst;
    }
    private void firstLoad()
    {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NotificationAdapterRecycleView notificationAdapterRecycleView = new NotificationAdapterRecycleView(getActivity(),initListNotifications(),GlobalVariable.WidthScreen,GlobalVariable.HeightScreen);
        recyclerView.setAdapter(notificationAdapterRecycleView);
        notificationAdapterRecycleView.notifyDataSetChanged();

    }
    public void onClick(View v)
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (v.getId())
        {
            case R.id.sivBackActionBar:
            case R.id.sivHomeActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                FrameLayout fl =  (FrameLayout) getActivity().findViewById(R.id.frameContent);
                if(fl!=null)
                    fl.setVisibility(View.VISIBLE);
                break;

        }
    }


}	

