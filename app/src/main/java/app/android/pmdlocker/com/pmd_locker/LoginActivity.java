package app.android.pmdlocker.com.pmd_locker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.objects.HUserRegister;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserOTPResponse;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserRegisterResponse;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserResponse;
import app.android.pmdlocker.com.pmd_locker.networks.BaseEndpoint;
import app.android.pmdlocker.com.pmd_locker.networks.BaseGenerator;
import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{

    private static final String TAG = LoginActivity.class.getName();
    ScaleImageView sivHomeActionBar;
    ScaleImageView sivBackActionBar;
    TextView textVTitleActionBar;
    Toolbar toolbarLogin;

    EditText edVUserName,edVPass;
    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        findId();
        initDefault();
        firstLoad();
    }
    private void customActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate the view
        final View view = inflater.inflate(R.layout.top_bar, null);
        toolbarLogin.addView(view);
        sivHomeActionBar = (ScaleImageView)view.findViewById(R.id.sivHomeActionBar);
        textVTitleActionBar = (TextView)view.findViewById(R.id.textVTitleActionBar);
        sivBackActionBar = (ScaleImageView)view.findViewById(R.id.sivBackActionBar);
        sivBackActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textVTitleActionBar.setText((Utility.getTextHtml(R.string.text_login,this)));
        sivHomeActionBar.setVisibility(View.INVISIBLE);
        Toolbar parent =(Toolbar) view.getParent();
        parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0,0);
    }
    private void findId()
    {
        toolbarLogin = (Toolbar) findViewById(R.id.toolbarLogin);
        edVUserName = (EditText) findViewById(R.id.edVUserName);
        edVPass = (EditText) findViewById(R.id.edVPass);

        edVPass.setImeOptions(EditorInfo.IME_ACTION_GO);
        edVPass.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edVPass.setSelection(edVPass.getText().length());

        edVPass.setTransformationMethod(PasswordTransformationMethod.getInstance());



        edVPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO)
                {
                    Utility.forceHideKeyboard(v.getContext(),edVPass);
                    loginWithAccount(edVUserName.getText().toString(),edVPass.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.textVLoginAction:
                loginWithAccount(edVUserName.getText().toString(),edVPass.getText().toString());
                break;
            case R.id.textVNotSignUp:
                Intent intent = new Intent(this,SignupActivity.class);
                startActivityForResult(intent,ConstantGlobalVariable.REQUEST_ACTIVITY_SIGN_UP);
                break;
            case R.id.textVForgetPass:
                Intent intentForget = new Intent(this,ForgotPasswordActivity.class);
                startActivityForResult(intentForget,ConstantGlobalVariable.REQUEST_ACTIVITY_FORGOT);
                break;
        }
    }
    private void initDefault()
    {
        setSupportActionBar(toolbarLogin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customActionBar();
        edVUserName.setHint((Utility.getTextHtml(R.string.text_username,this)));
        edVPass.setHint((Utility.getTextHtml(R.string.text_password,this)));

        TextView label;
        label = (TextView)findViewById(R.id.textVLabelUserName);
        label.setText(Utility.getTextHtml(R.string.text_username,this));

        label = (TextView)findViewById(R.id.textVLabelPass);
        label.setText(Utility.getTextHtml(R.string.text_password,this));

        label = (TextView)findViewById(R.id.textVNotSignUp);
        label.setText(Utility.getTextHtml(R.string.text_not_yet_signup,this));
        label.setOnClickListener(this);

        label = (TextView)findViewById(R.id.textVForgetPass);
        label.setText(Utility.getTextHtml(R.string.text_forget_pass,this));
        label.setOnClickListener(this);


        tvLogin = (TextView)findViewById(R.id.textVLoginAction);
        tvLogin.setText(Utility.getTextHtml(R.string.text_ok,this));
        tvLogin.setOnClickListener(this);




    }
    private void loginSuccess(UserResponse userResponse)
    {
        Utility.closeDialogLoading();
        if(userResponse.getSuccess()==true) {
//            GlobalVariable.hUser = userResponse.getData();
            Intent intent = getIntent();
            HUserRegister hUserRegister = userResponse.getData();
            hUserRegister.setPassword(edVPass.getText().toString());
            intent.putExtra(HUserRegister.class.getName(), hUserRegister);
            this.setResult(RESULT_OK, intent);
            finishActivity(ConstantGlobalVariable.REQUEST_ACTIVITY_LOGIN);

            finish();
        }
        else
        {
//            Utility.showDialogMessage(this,userResponse.getStatus().getErrorMessage(),Utility.getText(R.string.text_button_close,this));
            Toast.makeText(this,userResponse.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private void loginFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(this,Utility.getTextHtml(R.string.text_error_connection,this),Toast.LENGTH_LONG).show();
    }
    private void loginWithAccount(String userName,String password)
    {
        Utility.showDialogLoading(this);
        Map<String,String> params = new HashMap<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String deviceToken = Utility.getKeyString(ConstantGlobalVariable.SharedPreferencesKey.KEY_FCM,"",prefs);
        if(TextUtils.isEmpty(deviceToken)) {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
            Utility.saveKeyString(ConstantGlobalVariable.SharedPreferencesKey.KEY_FCM,deviceToken,prefs);
        }
        try {
            deviceToken = GoogleCloudMessaging.getInstance(this).register(ConstantGlobalVariable.GOOGLE_CLOUD_MESSING_SENDER_ID); // SENDER_ID
        }catch (Exception e)
        {

        }
        Log.d("device_token",deviceToken);
        BaseEndpoint bService = BaseGenerator.createService(BaseEndpoint.class, GlobalVariable.ACCESSTOKEN, GlobalVariable.Language, params);
        bService.loginUserName(userName,password,deviceToken).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                UserResponse userResponse = response.body();
                loginSuccess(userResponse);

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.getMessage()));
                loginFailed();
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ConstantGlobalVariable.REQUEST_ACTIVITY_SIGN_UP) {
            this.setResult(RESULT_OK,data);
            finishActivity(ConstantGlobalVariable.REQUEST_ACTIVITY_LOGIN);
            finish();
        }
        if (resultCode == RESULT_OK && requestCode == ConstantGlobalVariable.REQUEST_ACTIVITY_FORGOT) {
            this.setResult(RESULT_OK,data);
            UserResponse userResponse = (UserResponse) data.getSerializableExtra(UserResponse.class.getName());
            Utility.showDialogMessage(this,userResponse.getMessage(),Utility.getText(R.string.text_ok,this));
        }

    }
    private void firstLoad()
    {

    }







}
