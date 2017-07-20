package app.android.pmdlocker.com.pmd_locker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.NumberPicker;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserResponse;
import app.android.pmdlocker.com.pmd_locker.networks.BaseEndpoint;
import app.android.pmdlocker.com.pmd_locker.networks.BaseGenerator;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCustomProfileTime extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentCustomProfileTime.class.getName();


    View viewTopBar;
    ScaleImageView sivBackTopBar;
    TextView textVTitleTopBar;
    ScaleImageView sivHomeActionBar;

    TextView textVNextAction;
    TextView textVTitleTop;
//    TextView textVHours;
    NumberPicker numberPicker;
    public String locker,location,cityName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_custom_profile_time, null);
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
    }
    private void findId(View v)
    {
        initTopBar(v);

//        textVHours = (TextView)v.findViewById(R.id.tvHour);
        textVTitleTop = (TextView)v.findViewById(R.id.textVTitleTop);
        textVNextAction = (TextView)v.findViewById(R.id.textVNextAction);
        textVNextAction.setOnClickListener(this);
        sivHomeActionBar = (ScaleImageView)v.findViewById(R.id.sivHomeActionBar);
        sivHomeActionBar.setOnClickListener(this);

        numberPicker = (NumberPicker) v.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(100); // max value 100
        numberPicker.setMinValue(0);   // min value 0
        numberPicker.setValue(3);
//        textVHours.setText(String.valueOf(numberPicker.getValue()));
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new android.widget.NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
//                Log.i("value is",""+newVal);
//                textVHours.setText(String.valueOf(picker.getValue()));
            }
        });


    }



    private void initDefault()
    {
        textVNextAction.setText(Utility.getTextHtml(R.string.text_save_settings,getActivity()));
    }
    private void firstLoad()
    {


    }
    private void updateCustomProfileSuccess(UserResponse userResponse)
    {
        Utility.closeDialogLoading();
        FragmentManager fm = getFragmentManager();
        if(userResponse!=null) {
            if(userResponse.getSuccess())
            {
                GlobalVariable.userLogin = userResponse.getData();
                Utility.saveUserInfo(GlobalVariable.userLogin,getActivity());
                ManagerChangeFragment.removeFragment(fm,this);
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileLocker.class.getName());
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileLocation.class.getName());
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileSetting.class.getName());
                FragmentAccount fa = (FragmentAccount)ManagerChangeFragment.findFragment(fm,FragmentAccount.class.getName());
                if(fa!=null)
                    fa.initDefault();
//                Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentCustomProfileLocker.class.getName());
//                ManagerChangeFragment.addFragment(fm, fragment, R.id.frameProfile, FragmentCustomProfileLocation.class.getName());
            }
            else {
                Toast.makeText(getActivity(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(getActivity(),Utility.getText(R.string.text_error_unknown,getActivity()),Toast.LENGTH_SHORT).show();
    }
    private void updateCustomProfileFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(getActivity(),Utility.getTextHtml(R.string.text_error_connection,getActivity()),Toast.LENGTH_LONG).show();
    }
    private void updateCustomProfile()
    {
        Utility.showDialogLoading(getActivity());
        Map<String,String> params = new HashMap<>();
        Integer duration =  numberPicker.getValue();
        BaseEndpoint bService = BaseGenerator.createService(BaseEndpoint.class, GlobalVariable.ACCESSTOKEN, GlobalVariable.Language, params);
        bService.updateCustomUpdateProfile(GlobalVariable.ACCESSTOKEN,location,cityName,duration,locker).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                UserResponse userResponse = response.body();
                updateCustomProfileSuccess(userResponse);

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.getMessage()));
                updateCustomProfileFailed();
            }
        });

    }
    public void onClick(View v)
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (v.getId())
        {
            case R.id.textVNextAction:
                updateCustomProfile();

                break;
            case R.id.sivBackActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                break;
            case R.id.sivHomeActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileLocker.class.getName());
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

