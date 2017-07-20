package app.android.pmdlocker.com.pmd_locker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import app.android.pmdlocker.com.pmd_locker.LoginActivity;
import app.android.pmdlocker.com.pmd_locker.MainActivity;
import app.android.pmdlocker.com.pmd_locker.OTPActivity;
import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.SignupActivity;
import app.android.pmdlocker.com.pmd_locker.models.objects.HUserRegister;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserResponse;
import app.android.pmdlocker.com.pmd_locker.networks.BaseEndpoint;
import app.android.pmdlocker.com.pmd_locker.networks.BaseGenerator;
import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class FragmentMainMenu extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentMainMenu.class.getName();
    TextView textVLogin,textVSignUp,textVLoginFacebook;
    CallbackManager callbackManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_main_menu, null);
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
    private void findId(View v)
    {
        textVLogin=(TextView)v.findViewById(R.id.textVLogin);
        textVSignUp = (TextView)v.findViewById(R.id.textVSignUp);
        textVLoginFacebook = (TextView)v.findViewById(R.id.textVLoginFacebook);

        textVLogin.setText(Utility.getText(R.string.text_login,getActivity()));
        textVSignUp.setText(Utility.getText(R.string.text_sign_up,getActivity()));
        textVLoginFacebook.setText(Utility.getTextHtml(R.string.text_login_facebook,getActivity()));
        textVLogin.setOnClickListener(this);
        textVSignUp.setOnClickListener(this);
        textVLoginFacebook.setOnClickListener(this);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textVLogin.getLayoutParams();
        lp.width = GlobalVariable.WidthScreen/2;
        textVLogin.setLayoutParams(lp);
        lp = (RelativeLayout.LayoutParams) textVSignUp.getLayoutParams();
        lp.width = GlobalVariable.WidthScreen/2;
        textVSignUp.setLayoutParams(lp);

        lp = (RelativeLayout.LayoutParams) textVLoginFacebook.getLayoutParams();
        lp.width = GlobalVariable.WidthScreen/2;
        textVLoginFacebook.setLayoutParams(lp);
    }
    //apiKEY: ="laidrub_ZpN6HjrO6oWl4FRMV1le93UJKZmZ2P4Tp0zmayDlgJIQxQ"
    private void loginViaFacebookSuccess(UserResponse userResponse,final Profile profile,final String email)
    {
        Utility.closeDialogLoading();
        if(userResponse==null)
        {
            Toast.makeText(getActivity(),Utility.getText(R.string.text_error_unknown,getActivity()),Toast.LENGTH_LONG).show();
            return;
        }
        if(userResponse.getSuccess()==false)
        {
            if(userResponse.getErrorCode()==ConstantGlobalVariable.ERROR_CODE_NOT_USER || userResponse.getData()==null)
            {
                String userName = profile.getName();
                userName = Utility.AccentRemover.removeAccent(userName);
                HUserRegister hUserRegister = new HUserRegister(profile.getFirstName(),profile.getLastName(),
                        userName,email,"","",profile.getId(),AccessToken.getCurrentAccessToken().getToken(),"","",0,"","",0);
                Intent intentSignup = new Intent(getActivity(),SignupActivity.class);
                intentSignup.putExtra(HUserRegister.class.getName(),hUserRegister);
                startActivityForResult(intentSignup, ConstantGlobalVariable.REQUEST_ACTIVITY_SIGN_UP);
            }
            else
                Toast.makeText(getActivity(),userResponse.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }
        if(userResponse.getSuccess()==true && userResponse.getData()!=null)
        {
            HUserRegister userRegister = userResponse.getData();
            if(userRegister.getStatus().equalsIgnoreCase(ConstantGlobalVariable.USER_STATUS_ACTIVE))
            {
                GlobalVariable.userLogin = userRegister;
                Utility.saveUserInfo(userRegister,getContext());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentHomeMainMenu.class.getName());
                ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
                return;
            }
            else if(userRegister.getStatus().equalsIgnoreCase(ConstantGlobalVariable.USER_STATUS_PENDING))
            {
                Intent intentOTP = new Intent(getActivity(),OTPActivity.class);
                intentOTP.putExtra(HUserRegister.class.getName(),userRegister);
                startActivityForResult(intentOTP, ConstantGlobalVariable.REQUEST_ACTIVITY_OTP);
            }
        }
    }
    private void loginViaFacebookFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(getActivity(),Utility.getTextHtml(R.string.text_error_connection,getActivity()),Toast.LENGTH_LONG).show();
    }
    public void loginViaFacebook(final Profile profile, final String email)
    {
        Utility.showDialogLoading(getActivity());
        Map<String,String> params = new HashMap<>();
        BaseEndpoint bService = BaseGenerator.createService(BaseEndpoint.class, GlobalVariable.ACCESSTOKEN, GlobalVariable.Language, params);
        bService.loginViaFacebook(profile.getId(),email).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                UserResponse userResponse = response.body();
                loginViaFacebookSuccess(userResponse,profile,email);

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.getMessage()));
                loginViaFacebookFailed();
            }
        });

    }


    private void getInfoUserFacebook(final Profile profile)
    {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {

                            String email_id=object.getString("email");
//                            gender=object.getString("gender");
//                            String profile_name=object.getString("name");
//                            long fb_id=object.getLong("id"); //use this for logout
                            loginViaFacebook(profile,email_id);

                        } catch (JSONException e) {
                            loginViaFacebook(profile,"");
                            // TODO Auto-generated catch block
                            //  e.printStackTrace();
                        }

                    }

                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
    private void initDefault()
    {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               final Profile profile =  Profile.getCurrentProfile();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getInfoUserFacebook(profile);
                    }
                }).start();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(),error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void firstLoad()
    {


    }
    @Override
    public void onActivityResult(final int requestCode,final int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK &&
                (requestCode == ConstantGlobalVariable.REQUEST_ACTIVITY_LOGIN ))
        {
            GlobalVariable.userLogin  = (HUserRegister) data.getSerializableExtra(HUserRegister.class.getName());
            Utility.saveUserInfo(GlobalVariable.userLogin,getContext());
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentHomeMainMenu.class.getName());
            ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
            ((MainActivity)getActivity()).showPagerViewBottomBar();
        }
        if (resultCode == RESULT_OK &&
                (requestCode == ConstantGlobalVariable.REQUEST_ACTIVITY_SIGN_UP || requestCode == ConstantGlobalVariable.REQUEST_ACTIVITY_OTP))
        {

            GlobalVariable.userLogin  = (HUserRegister) data.getSerializableExtra(HUserRegister.class.getName());
            Utility.saveUserInfo(GlobalVariable.userLogin,getContext());
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentHomeMainMenu.class.getName());
            ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
            ((MainActivity)getActivity()).showPagerViewBottomBar();
        }
        if(callbackManager!=null)
            callbackManager.onActivityResult(requestCode,resultCode,data);
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.textVLogin:
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, ConstantGlobalVariable.REQUEST_ACTIVITY_LOGIN);
                break;
            case R.id.textVSignUp:
                Intent intentSignup = new Intent(getActivity(),SignupActivity.class);
                //intentSignup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intentSignup, ConstantGlobalVariable.REQUEST_ACTIVITY_SIGN_UP);

                break;
            case R.id.textVLoginFacebook:
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
                break;

        }
    }


}	

