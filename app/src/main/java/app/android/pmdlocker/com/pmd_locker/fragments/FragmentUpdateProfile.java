package app.android.pmdlocker.com.pmd_locker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

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


public class FragmentUpdateProfile extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentUpdateProfile.class.getName();


    View viewTopBar;
    ScaleImageView sivBackTopBar;
    TextView textVTitleTopBar;
    ScaleImageView sivHomeActionBar;


    EditText edVPass,edVConfirmPass,edVFirstName,edVLastName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_update_profile, null);
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
        TextView label;
        label = (TextView)v.findViewById(R.id.textVLabelFirstName);
        label.setText(Utility.getText(R.string.text_first_name,getActivity()));

        label = (TextView)v.findViewById(R.id.textVLabelLastName);
        label.setText(Utility.getText(R.string.text_last_name,getActivity()));

        label = (TextView)v.findViewById(R.id.textVLabelPass);
        label.setText(Utility.getText(R.string.text_password,getActivity()));

        label = (TextView)v.findViewById(R.id.textVLabelConfirmPass);
        label.setText(Utility.getText(R.string.text_re_password,getActivity()));

        edVFirstName = (EditText)v.findViewById(R.id.edVFirstName);
        edVFirstName.setText(GlobalVariable.userLogin.getFirstName());

        edVLastName = (EditText)v.findViewById(R.id.edVLastName);
        edVLastName.setText(GlobalVariable.userLogin.getLastName());

        edVPass = (EditText)v.findViewById(R.id.edVPass);
        edVPass.setHint(Utility.getText(R.string.text_password,getActivity()));

        edVPass.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edVPass.setSelection(edVPass.getText().length());

        edVConfirmPass = (EditText)v.findViewById(R.id.edVConfirmPass);

        edVConfirmPass.setHint(Utility.getText(R.string.text_re_password,getActivity()));
        edVConfirmPass.setImeOptions(EditorInfo.IME_ACTION_GO);
        edVConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edVConfirmPass.setSelection(edVConfirmPass.getText().length());

        edVConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());



        edVConfirmPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO)
                {
                    Utility.forceHideKeyboard(v.getContext(),edVConfirmPass);
                    updateCustomProfile();
                    return true;
                }
                return false;
            }
        });
        TextView tvAction = (TextView)v.findViewById(R.id.textVUpdateAction);
        tvAction.setOnClickListener(this);
    }
    private void updateProfileSuccess(UserResponse userResponse)
    {
        Utility.closeDialogLoading();
        if(userResponse!=null) {

            {
                Toast.makeText(getActivity(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        else
            Toast.makeText(getActivity(),Utility.getText(R.string.text_error_unknown,getActivity()),Toast.LENGTH_SHORT).show();

    }
    private void updateProfileFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(getActivity(),Utility.getTextHtml(R.string.text_error_connection,getActivity()),Toast.LENGTH_LONG).show();
    }
    private void updateCustomProfile()
    {

        Map<String,String> params = new HashMap<>();

        BaseEndpoint bService = BaseGenerator.createService(BaseEndpoint.class, GlobalVariable.ACCESSTOKEN, GlobalVariable.Language, params);
        Callback<UserResponse> callback =  new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                UserResponse userResponse = response.body();
                GlobalVariable.userLogin.setFirstName(edVFirstName.getText().toString());
                GlobalVariable.userLogin.setLastName(edVLastName.getText().toString());
                Utility.saveUserInfo(GlobalVariable.userLogin,getActivity());
                updateProfileSuccess(userResponse);

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.getMessage()));
                updateProfileFailed();
            }
        };
        Callback<UserResponse> callbackPass =  new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                UserResponse userResponse = response.body();
                GlobalVariable.userLogin.setPassword(edVPass.getText().toString());
                Utility.saveUserInfo(GlobalVariable.userLogin,getActivity());
                updateProfileSuccess(userResponse);

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.getMessage()));
                updateProfileFailed();
            }
        };
        String firstName = GlobalVariable.userLogin.getFirstName();
        String lastName = GlobalVariable.userLogin.getLastName();
        if(firstName==null)
            firstName = "";
        if(lastName==null)
            lastName = "";
        boolean isLoading = false;
        if(!firstName.equals(edVFirstName.getText().toString()) || !lastName.equals(edVLastName.getText().toString())) {
            bService.updateCustomProfile(GlobalVariable.ACCESSTOKEN, edVFirstName.getText().toString(), edVLastName.getText().toString()).enqueue(callback);
            isLoading = true;
        }
        if(!(TextUtils.isEmpty(edVPass.getText().toString())&&TextUtils.isEmpty(edVConfirmPass.getText().toString()))) {
            bService.updateCustomPassword(GlobalVariable.ACCESSTOKEN, GlobalVariable.userLogin.getPassword(), edVPass.getText().toString(), edVConfirmPass.getText().toString()).enqueue(callbackPass);
            isLoading = true;
        }
        if(isLoading)
            Utility.showDialogLoading(getActivity());

    }

    private void initDefault()
    {

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
            case R.id.textVUpdateAction:
                updateCustomProfile();
                break;
        }
    }


}	

