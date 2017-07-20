package app.android.pmdlocker.com.pmd_locker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.apdapters.LockerAdapterRecycleView;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.objects.HLocker;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserResponse;
import app.android.pmdlocker.com.pmd_locker.networks.BaseEndpoint;
import app.android.pmdlocker.com.pmd_locker.networks.BaseGenerator;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCustomProfileLocker extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentCustomProfileLocker.class.getName();


    View viewTopBar;
    ScaleImageView sivBackTopBar;
    TextView textVTitleTopBar;
    ScaleImageView sivHomeActionBar;

    TextView textVNextAction;
    TextView textVTitleTop;
    RecyclerView recycleViewTransport;
    public String cityName;
    public String location;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_custom_profile_locker, null);
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
        textVTitleTopBar.setText(Utility.getTextHtml(R.string.text_profile_customization,getActivity()));
        sivHomeActionBar = (ScaleImageView)v.findViewById(R.id.sivHomeActionBar);
        sivHomeActionBar.setOnClickListener(this);
    }
    private void findId(View v)
    {
        initTopBar(v);
        recycleViewTransport = (RecyclerView)v.findViewById(R.id.recycleViewTransport);
        textVTitleTop = (TextView)v.findViewById(R.id.textVTitleTop);
        textVTitleTop.setText(Utility.getTextHtml(R.string.text_need_usually_locker,getActivity()));
        textVNextAction = (TextView)v.findViewById(R.id.textVNextAction);
        textVNextAction.setOnClickListener(this);
    }
    private void initDefault()
    {
        textVNextAction.setText(Utility.getTextHtml(R.string.text_next,getActivity()));
    }
    private void firstLoad()
    {
        getListKiosk();

    }
    private void getListKioskSuccess(List<HLocker> lst)
    {
        Utility.closeDialogLoading();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleViewTransport.setLayoutManager(layoutManager);
        LockerAdapterRecycleView lockerAdapterRecycleView = new LockerAdapterRecycleView(getActivity(),lst,GlobalVariable.WidthScreen,GlobalVariable.HeightScreen,cityName,location);
        recycleViewTransport.setAdapter(lockerAdapterRecycleView);
        lockerAdapterRecycleView.notifyDataSetChanged();
    }
    private List<HLocker> initListLocker()
    {
        List<HLocker> lst = new ArrayList<>();
        HLocker hLocker ;
        hLocker = new HLocker(R.mipmap.profile_bicycle,Utility.getText(R.string.text_locker_bycicle,getActivity()));
        lst.add(hLocker);
        hLocker = new HLocker(R.mipmap.clip_group_1,Utility.getText(R.string.text_locker_clip1,getActivity()));
        lst.add(hLocker);
        hLocker = new HLocker(R.mipmap.clip_group_2,Utility.getText(R.string.text_locker_clip2,getActivity()));
        lst.add(hLocker);
        return lst;
    }
    private void getListKioskFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(getActivity(),Utility.getTextHtml(R.string.text_error_connection,getActivity()),Toast.LENGTH_LONG).show();
    }
    private void getListKiosk()
    {
        Utility.showDialogLoading(getActivity());

        getListKioskSuccess(initListLocker());
//        Map<String,String> params = new HashMap<>();
//        BaseEndpoint bService = BaseGenerator.createService(BaseEndpoint.class, GlobalVariable.ACCESSTOKEN, GlobalVariable.Language, params);
//        bService.getListKiosk().enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//
//                UserResponse userResponse = response.body();
//                getListKioskSuccess();
//
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Log.e(TAG, String.format("onFailure: %s", t.getMessage()));
//                getListKioskFailed();
//            }
//        });

    }

    public void onClick(View v)
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (v.getId())
        {
            case R.id.textVNextAction:
                if(((LockerAdapterRecycleView)recycleViewTransport.getAdapter())==null|| ((LockerAdapterRecycleView)recycleViewTransport.getAdapter()).lastestSelected==null) {
                    Toast.makeText(getActivity(),Utility.getText(R.string.text_select_locker,getActivity()),Toast.LENGTH_SHORT).show();
                    return;
                }
                FragmentCustomProfileTime fragment = (FragmentCustomProfileTime) ManagerChangeFragment.createFragment(fm, FragmentCustomProfileTime.class.getName());
                fragment.cityName = cityName;
                fragment.location = location;
                fragment.locker = ((LockerAdapterRecycleView)recycleViewTransport.getAdapter()).lastestSelected;

                ManagerChangeFragment.addFragment(fm, fragment, R.id.frameProfile, FragmentCustomProfileLocker.class.getName());

                break;
            case R.id.sivBackActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                break;
            case R.id.sivHomeActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileLocation.class.getName());
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileSetting.class.getName());
                ManagerChangeFragment.removeFragment(fm,FragmentAccount.class.getName());
                FrameLayout fl =  (FrameLayout) getActivity().findViewById(R.id.frameContent);
                if(fl!=null)
                    fl.setVisibility(View.VISIBLE);
                break;
        }
    }


}	

