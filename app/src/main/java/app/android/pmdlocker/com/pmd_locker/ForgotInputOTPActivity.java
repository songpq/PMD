package app.android.pmdlocker.com.pmd_locker;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.objects.HUserRegister;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserOTPResponse;
import app.android.pmdlocker.com.pmd_locker.networks.BaseEndpoint;
import app.android.pmdlocker.com.pmd_locker.networks.BaseGenerator;
import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotInputOTPActivity extends AppCompatActivity implements  View.OnClickListener{

    private static final String TAG = ForgotInputOTPActivity.class.getName();
    ScaleImageView sivHomeActionBar;
    ScaleImageView sivBackActionBar;
    TextView textVTitleActionBar;
    Toolbar toolbarOTP;

    TextView textVLabelMobile;
    EditText edVMobile;

    TextView textVlabelCode;
    EditText edVCode;

    TextView tvHintForgot;

    TextView textVOTPAction;
    //HUserRegister hUserRegister;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_input_otp);
        if(getIntent()!=null)
            phone = getIntent().getStringExtra(ConstantGlobalVariable.ShareKeyIntent.KEY_PHONE);
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
        toolbarOTP.addView(view);
        sivHomeActionBar = (ScaleImageView)view.findViewById(R.id.sivHomeActionBar);
        textVTitleActionBar = (TextView)view.findViewById(R.id.textVTitleActionBar);
        sivBackActionBar = (ScaleImageView)view.findViewById(R.id.sivBackActionBar);
        sivBackActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textVTitleActionBar.setText((Utility.getTextHtml(R.string.text_forgot_pass,this)));
        sivHomeActionBar.setVisibility(View.INVISIBLE);
        Toolbar parent =(Toolbar) view.getParent();
        parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0,0);
    }
    private void findId()
    {
        toolbarOTP = (Toolbar) findViewById(R.id.toolbarOTP);
        edVMobile = (EditText) findViewById(R.id.edVMobile);
        tvHintForgot = (TextView)findViewById(R.id.textVHintOTP);
        textVLabelMobile = (TextView)findViewById(R.id.textVLabelPhone);

        textVlabelCode = (TextView)findViewById(R.id.textVLabelOTP);
        edVCode = (EditText)findViewById(R.id.edVOTP);

        textVOTPAction = (TextView)findViewById(R.id.textVOTPAction);
        edVCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO)
                {
                    Utility.forceHideKeyboard(v.getContext(),edVMobile);
                    sentVerifyOTPLostPass(edVMobile.getText().toString(),edVCode.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.textVOTPAction:
                sentVerifyOTPLostPass(edVMobile.getText().toString(),edVCode.getText().toString());
                break;

        }
    }
    private void initDefault()
    {
        setSupportActionBar(toolbarOTP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customActionBar();


        textVLabelMobile.setText(Utility.getTextHtml(R.string.text_mobile,this));
        edVMobile.setHint(Utility.getTextHtml(R.string.text_mobile_sample,this));
        if(!phone.startsWith("+"))
            edVMobile.setText(phone);
        else
            edVMobile.setText("+"+phone);
        textVlabelCode.setText(Utility.getTextHtml(R.string.text_otp_sample,this));
        edVCode.setHint(Utility.getTextHtml(R.string.text_otp,this));

        tvHintForgot.setText(Utility.getTextHtml(R.string.text_message_forgot_pass,this));

        textVOTPAction.setText(Utility.getTextHtml(R.string.text_ok,this));
        textVOTPAction.setOnClickListener(this);




    }
    private void verifyOTPForgotSuccess(UserOTPResponse userOTPResponse,final String phone)
    {
        Utility.closeDialogLoading();
        if(userOTPResponse.getSuccess()==true) {
            Toast.makeText(this,userOTPResponse.getMessage(),Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,ForgotRenewPassActivity.class);
            i.putExtra(ConstantGlobalVariable.ShareKeyIntent.KEY_PHONE,phone);
            startActivityForResult(i, ConstantGlobalVariable.REQUEST_ACTIVITY_FORGOT_RENEW_PASS);
        }
        else
        {
            Toast.makeText(this,userOTPResponse.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private void verifyOTPForgotFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(this,Utility.getTextHtml(R.string.text_error_connection,this),Toast.LENGTH_LONG).show();
    }
    private void sentVerifyOTPLostPass(final String phone,String codeOTP)
    {
        Utility.showDialogLoading(this);
        Map<String,String> params = new HashMap<>();
        BaseEndpoint bService = BaseGenerator.createService(BaseEndpoint.class, GlobalVariable.ACCESSTOKEN, GlobalVariable.Language, params);
        bService.verifyOTPLostPass(phone,codeOTP).enqueue(new Callback<UserOTPResponse>() {
            @Override
            public void onResponse(Call<UserOTPResponse> call, Response<UserOTPResponse> response) {

                UserOTPResponse userOTPResponse = response.body();
                verifyOTPForgotSuccess(userOTPResponse,phone);

            }

            @Override
            public void onFailure(Call<UserOTPResponse> call, Throwable t) {
                Log.e(TAG, String.format("onFailure: %s", t.getMessage()));
                verifyOTPForgotFailed();
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ConstantGlobalVariable.REQUEST_ACTIVITY_FORGOT_RENEW_PASS) {
            this.setResult(RESULT_OK,data);
            finishActivity(ConstantGlobalVariable.REQUEST_ACTIVITY_FORGOT_OTP);
            finish();
        }

    }
    private void firstLoad()
    {

    }







}
