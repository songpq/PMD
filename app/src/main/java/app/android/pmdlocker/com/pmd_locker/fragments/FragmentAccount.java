package app.android.pmdlocker.com.pmd_locker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.android.pmdlocker.com.pmd_locker.MainActivity;
import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserResponse;
import app.android.pmdlocker.com.pmd_locker.networks.BaseEndpoint;
import app.android.pmdlocker.com.pmd_locker.networks.BaseGenerator;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentAccount extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentAccount.class.getName();

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
    TextView textVTopupAction;
    TextView textVCustomUserSettingAction;
    TextView textVLogoutAction;
    TextView textVUpdateProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_account, null);
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
        sivHomeActionBar = (ScaleImageView)v.findViewById(R.id.sivHomeActionBar);
        sivHomeActionBar.setOnClickListener(this);
        textVTitleTopBar = (TextView)v.findViewById(R.id.textVTitleActionBar);
        textVTitleTopBar.setText(Utility.getTextHtml(R.string.text_profile,getActivity()));
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
        textVTopupAction = (TextView)v.findViewById(R.id.textVTopupAction);
        textVCustomUserSettingAction = (TextView)v.findViewById(R.id.textVCustomUserSettingAction);
        textVTopupAction.setOnClickListener(this);
        textVCustomUserSettingAction.setOnClickListener(this);
        textVLogoutAction = (TextView)v.findViewById(R.id.textVLogoutAction);
        textVLogoutAction.setOnClickListener(this);
        textVUpdateProfile = (TextView)v.findViewById(R.id.textVUpdateProfile);
        textVUpdateProfile.setOnClickListener(this);
    }
    public void initDefault()
    {
        textVTopupAction.setText(Utility.getTextHtml(R.string.text_top_up,getActivity()));
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
        textVLogoutAction.setText(Utility.getTextHtml(R.string.text_logout,getActivity()));
    }
    private void firstLoad()
    {


    }

    public void onClick(View v)
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (v.getId())
        {
            case R.id.textVTopupAction:
                Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentCustomProfileTopup.class.getName());
                ManagerChangeFragment.addFragment(fm, fragment, R.id.frameAccount, FragmentAccount.class.getName());
                break;

            case R.id.textVCustomUserSettingAction:
                Fragment fragmentProfile = ManagerChangeFragment.createFragment(fm, FragmentCustomProfileSetting.class.getName());
                ManagerChangeFragment.addFragment(fm, fragmentProfile, R.id.frameAccount, FragmentAccount.class.getName());
                break;
            case R.id.sivBackActionBar:
            case R.id.sivHomeActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                FrameLayout fl =  (FrameLayout) getActivity().findViewById(R.id.frameContent);
                if(fl!=null)
                    fl.setVisibility(View.VISIBLE);
                break;
            case R.id.textVLogoutAction:
                logout();
                break;
            case R.id.textVUpdateProfile:
                Fragment fragmentUpdateProfile = ManagerChangeFragment.createFragment(fm, FragmentUpdateProfile.class.getName());
                ManagerChangeFragment.addFragment(fm, fragmentUpdateProfile, R.id.frameAccount, FragmentAccount.class.getName());
                break;

        }
    }
    private void logoutSuccess()
    {
        Utility.closeDialogLoading();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ManagerChangeFragment.removeFragment(fm,this);
        ((MainActivity)getActivity()).clearLogout();

    }
    private void logoutFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(getActivity(),Utility.getTextHtml(R.string.text_error_connection,getActivity()),Toast.LENGTH_LONG).show();
    }
    public void logout()
    {
        Utility.showDialogLoading(getActivity());
        Map<String,String> params = new HashMap<>();
        BaseEndpoint bService = BaseGenerator.createService(BaseEndpoint.class, GlobalVariable.ACCESSTOKEN, GlobalVariable.Language, params);
        bService.logoutUser(GlobalVariable.userLogin.getUserName()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {


                logoutSuccess();

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.getMessage()));
                logoutFailed();
            }
        });

    }


}	

