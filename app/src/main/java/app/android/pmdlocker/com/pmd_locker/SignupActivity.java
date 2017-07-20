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
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.objects.HUserRegister;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserRegisterResponse;
import app.android.pmdlocker.com.pmd_locker.networks.BaseEndpoint;
import app.android.pmdlocker.com.pmd_locker.networks.BaseGenerator;
import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements  View.OnClickListener{

    private static final String TAG = SignupActivity.class.getName();
    ScaleImageView sivHomeActionBar;
    ScaleImageView sivBackActionBar;
    TextView textVTitleActionBar;
    Toolbar toolbarSignup;

    EditText edVUserName,edVPhone,edVEmail,edVPass,edVFirstName,edVLastName;
    HUserRegister hUserRegister=null;
    TextView tvSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        if(getIntent()!=null&&getIntent().hasExtra(HUserRegister.class.getName()))
        {
            hUserRegister = (HUserRegister) getIntent().getSerializableExtra(HUserRegister.class.getName());
        }
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
        toolbarSignup.addView(view);
        sivHomeActionBar = (ScaleImageView)view.findViewById(R.id.sivHomeActionBar);
        textVTitleActionBar = (TextView)view.findViewById(R.id.textVTitleActionBar);
        sivBackActionBar = (ScaleImageView)view.findViewById(R.id.sivBackActionBar);
        sivBackActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textVTitleActionBar.setText((Utility.getTextHtml(R.string.text_sign_up,this)));
        sivHomeActionBar.setVisibility(View.INVISIBLE);
        Toolbar parent =(Toolbar) view.getParent();
        parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0,0);
    }
    private void findId()
    {
        toolbarSignup = (Toolbar) findViewById(R.id.toolbarSignup);
        edVUserName = (EditText) findViewById(R.id.edVUserName);
        edVPass = (EditText) findViewById(R.id.edVPass);
        edVEmail = (EditText)findViewById(R.id.edVEmail);
        edVPhone = (EditText)findViewById(R.id.edVPhone);

        edVFirstName = (EditText)findViewById(R.id.edVFirstName);
        edVLastName = (EditText)findViewById(R.id.edVLastName);

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
                    signUpAccount(edVUserName.getText().toString()
                            ,edVPass.getText().toString()
                            ,edVPhone.getText().toString()
                            ,edVEmail.getText().toString()
                            ,edVFirstName.getText().toString()
                            ,edVLastName.getText().toString()
                            );
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.textVSignUpAction:
                signUpAccount(edVUserName.getText().toString()
                        ,edVPass.getText().toString()
                        ,edVPhone.getText().toString()
                        ,edVEmail.getText().toString()
                        ,edVFirstName.getText().toString()
                        ,edVLastName.getText().toString()
                );
                break;
        }
    }
    private void initDefault()
    {
        setSupportActionBar(toolbarSignup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customActionBar();
        edVUserName.setHint((Utility.getTextHtml(R.string.text_username,this)));
        edVPass.setHint((Utility.getTextHtml(R.string.text_password,this)));
        edVPhone.setHint(Utility.getTextHtml(R.string.text_mobile_sample,this));
        edVEmail.setHint(Utility.getTextHtml(R.string.text_email,this));
        edVFirstName.setHint(Utility.getTextHtml(R.string.text_first_name,this));
        edVLastName.setHint(Utility.getTextHtml(R.string.text_last_name,this));

        edVPass.setImeOptions(EditorInfo.IME_ACTION_GO);
        edVPass.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edVPass.setSelection(edVPass.getText().length());

        edVPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        TextView label;
        label = (TextView)findViewById(R.id.textVLabelUserName);
        label.setText(Utility.getTextHtml(R.string.text_username,this));

        label = (TextView)findViewById(R.id.textVLabelPhone);
        label.setText(Utility.getTextHtml(R.string.text_mobile,this));

        label = (TextView)findViewById(R.id.textVLabelEmail);
        label.setText(Utility.getTextHtml(R.string.text_email,this));


        label = (TextView)findViewById(R.id.textVLabelFirstName);
        label.setText(Utility.getTextHtml(R.string.text_first_name,this));

        label = (TextView)findViewById(R.id.textVLabelLastName);
        label.setText(Utility.getTextHtml(R.string.text_last_name,this));

        label = (TextView)findViewById(R.id.textVLabelPass);
        label.setText(Utility.getTextHtml(R.string.text_password,this));

        label = (TextView)findViewById(R.id.textVHintOTP);
        label.setText(Utility.getTextHtml(R.string.text_message_sms,this));
        label.setOnClickListener(this);

        label = (TextView)findViewById(R.id.textVSignUpAction);
        label.setText(Utility.getTextHtml(R.string.text_ok,this));
        label.setOnClickListener(this);

        if(hUserRegister!=null)
            showInfoUser(hUserRegister);


    }
    private void showInfoUser(HUserRegister userRegister)
    {
        if(!TextUtils.isEmpty(userRegister.getEmail()))
            edVEmail.setText(userRegister.getEmail());
        if(!TextUtils.isEmpty(userRegister.getFirstName()))
            edVFirstName.setText(userRegister.getFirstName());
        if(!TextUtils.isEmpty(userRegister.getLastName()))
            edVLastName.setText(userRegister.getLastName());
        if(!TextUtils.isEmpty(userRegister.getUserName()))
            edVUserName.setText(userRegister.getUserName());
        if(!TextUtils.isEmpty(userRegister.getPhone()))
            edVFirstName.setText(userRegister.getFirstName());

    }
    private void signUpSuccess(UserRegisterResponse userRegisterResponse)
    {
        Utility.closeDialogLoading();
        if(userRegisterResponse.getSuccess()==true) {
            Intent intent = new Intent(this, OTPActivity.class);
            HUserRegister hUserRegister = userRegisterResponse.getData();
            hUserRegister.setPassword(edVPass.getText().toString());
            intent.putExtra(HUserRegister.class.getName(),hUserRegister);
            startActivityForResult(intent, ConstantGlobalVariable.REQUEST_ACTIVITY_OTP);
        }
        else
        {
            Toast.makeText(this,userRegisterResponse.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private void signUpFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(this,Utility.getTextHtml(R.string.text_error_connection,this),Toast.LENGTH_LONG).show();
    }
    private void signUpAccount(String userName,String password,String phone,String email,String firstName,String lastName)
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
        bService.registerUser(userName,email,password,phone,firstName,lastName,deviceToken).enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {

                UserRegisterResponse userRegisterResponse = response.body();
                signUpSuccess(userRegisterResponse);

            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.getMessage()));
                signUpFailed();
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ConstantGlobalVariable.REQUEST_ACTIVITY_OTP) {
            this.setResult(RESULT_OK,data);

            finishActivity(ConstantGlobalVariable.REQUEST_ACTIVITY_SIGN_UP);
            finish();
        }

    }
    private void firstLoad()
    {

    }







}
