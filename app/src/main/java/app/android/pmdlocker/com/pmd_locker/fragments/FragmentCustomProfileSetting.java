package app.android.pmdlocker.com.pmd_locker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;


public class FragmentCustomProfileSetting extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentCustomProfileSetting.class.getName();


    View viewTopBar;
    ScaleImageView sivBackTopBar;
    TextView textVTitleTopBar;
    ScaleImageView sivHomeActionBar;

    TextView textVName;
    TextView textVMainUsageOfLocker;
    TextView textVPreferrerLockerLocation;
    TextView textVAverageUsageDuration;
    TextView textVTitleAmountWallet;
    TextView textVAmount;
    TextView textVCustomUserSettingAction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_custom_profile_setting, null);
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
        textVTitleTopBar.setText(Utility.getTextHtml(R.string.text_profile,getActivity()));
        sivHomeActionBar = (ScaleImageView)v.findViewById(R.id.sivHomeActionBar);
        sivHomeActionBar.setOnClickListener(this);
    }
    private void findId(View v)
    {
        initTopBar(v);
        textVName = (TextView)v.findViewById(R.id.textVname);

        textVMainUsageOfLocker = (TextView)v.findViewById(R.id.textVMainUsageOfLocker);
        textVPreferrerLockerLocation = (TextView)v.findViewById(R.id.textVPreferredLockerLocation);
        textVAverageUsageDuration = (TextView)v.findViewById(R.id.textVAverageUsageDuration);

        textVTitleAmountWallet = (TextView)v.findViewById(R.id.textVTitleAmountWallet);
        textVAmount = (TextView)v.findViewById(R.id.textVAmount);
        textVCustomUserSettingAction = (TextView)v.findViewById(R.id.textVCustomUserSettingAction);

        textVCustomUserSettingAction.setOnClickListener(this);
    }
    private void initDefault()
    {
        textVCustomUserSettingAction.setText(Utility.getTextHtml(R.string.text_custom_setting_user,getActivity()));
        textVTitleAmountWallet.setText(Utility.getTextHtml(R.string.text_amount_in_wallet,getActivity()));


        if(!TextUtils.isEmpty(GlobalVariable.userLogin.getUserName()))
            textVName.setText(Html.fromHtml(GlobalVariable.userLogin.getUserName()));
        if(!TextUtils.isEmpty(GlobalVariable.userLogin.getUsageLocker()))
            textVMainUsageOfLocker.setText(Html.fromHtml(Utility.getText(R.string.text_main_usage_of_locker,getActivity())+" "+ GlobalVariable.userLogin.getUsageLocker()));
        else
            textVMainUsageOfLocker.setText(Html.fromHtml(Utility.getText(R.string.text_main_usage_of_locker,getActivity())+" "+ Utility.getText(R.string.text_need_to_update,getActivity())));

        if(!TextUtils.isEmpty(GlobalVariable.userLogin.getPreferredLockerLocationName()))
            textVPreferrerLockerLocation.setText(Html.fromHtml(Utility.getText(R.string.text_preferrer_locker_location,getActivity())+" "+ GlobalVariable.userLogin.getPreferredLockerLocationName()));
        else
            textVPreferrerLockerLocation.setText(Html.fromHtml(Utility.getText(R.string.text_preferrer_locker_location,getActivity())+" "+ Utility.getText(R.string.text_need_to_update,getActivity())));

        if(!TextUtils.isEmpty(GlobalVariable.userLogin.getPreferredLockerLocationName()))
            textVAverageUsageDuration.setText(Html.fromHtml(Utility.getText(R.string.text_average_usage_duration,getActivity())+" "+ GlobalVariable.userLogin.getUsageDuration()));
        else
            textVAverageUsageDuration.setText(Html.fromHtml(Utility.getText(R.string.text_average_usage_duration,getActivity())+" "+ Utility.getText(R.string.text_need_to_update,getActivity())));
        textVAmount.setText(Utility.getText(R.string.text_dollar,getActivity())+GlobalVariable.userLogin.getWalletAmount());
    }
    private void firstLoad()
    {


    }
    @Override
    public void onResume()
    {
        super.onResume();
        initDefault();
    }
    public void onClick(View v)
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (v.getId())
        {
            case R.id.textVCustomUserSettingAction:
                Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentCustomProfileLocation.class.getName());
                ManagerChangeFragment.addFragment(fm, fragment, R.id.frameProfile, FragmentCustomProfileSetting.class.getName());
                break;
            case R.id.sivBackActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                break;
            case R.id.sivHomeActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                ManagerChangeFragment.removeFragment(fm,FragmentAccount.class.getName());
                FrameLayout fl =  (FrameLayout) getActivity().findViewById(R.id.frameContent);
                if(fl!=null)
                    fl.setVisibility(View.VISIBLE);
                break;
        }
    }


}	

