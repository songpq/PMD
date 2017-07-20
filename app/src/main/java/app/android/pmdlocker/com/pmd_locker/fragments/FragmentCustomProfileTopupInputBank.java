package app.android.pmdlocker.com.pmd_locker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;


public class FragmentCustomProfileTopupInputBank extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentCustomProfileTopupInputBank.class.getName();


    View viewTopBar;
    ScaleImageView sivBackTopBar;
    TextView textVTitleTopBar;
    ScaleImageView sivHomeActionBar;

    TextView textVTitleTop;
    TextView textVLabelAcount,textVLabelAmount,textVLabelCreditCard,textVLabelCVC;
    TextView textVConfirm;
    EditText edVAccount,edVAmount,edVCreditCard,edVCVC;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_custom_profile_input_bank_account, null);
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
        textVTitleTopBar.setText(Utility.getTextHtml(R.string.text_top_up,getActivity()));
        sivHomeActionBar = (ScaleImageView)v.findViewById(R.id.sivHomeActionBar);
        sivHomeActionBar.setOnClickListener(this);
    }
    private void findId(View v)
    {
        initTopBar(v);
        textVTitleTop = (TextView)v.findViewById(R.id.textVTitleTop);
        textVLabelAcount = (TextView)v.findViewById(R.id.textVLabelBankAccount);
        textVLabelAmount = (TextView)v.findViewById(R.id.textVLabelAmount);
        textVLabelCreditCard = (TextView)v.findViewById(R.id.textVLabelCreditCard);
        textVLabelCVC = (TextView)v.findViewById(R.id.textVLabelCVC);

        edVAccount = (EditText)v.findViewById(R.id.edVBankAccount);
        edVAmount = (EditText)v.findViewById(R.id.edVAmount);
        edVCreditCard = (EditText)v.findViewById(R.id.edVCreditCard);
        edVCVC = (EditText)v.findViewById(R.id.edVCVC);


        textVConfirm = (TextView)v.findViewById(R.id.textVConfirmAction);

        textVConfirm.setOnClickListener(this);
    }
    private void initDefault()
    {
        textVTitleTop.setText(Utility.getText(R.string.text_add_wallet,getActivity()));
        textVConfirm.setText(Utility.getTextHtml(R.string.text_confirm,getActivity()));
        textVLabelCVC.setText(Utility.getText(R.string.text_ccv,getActivity()));
        textVLabelAmount.setText(Utility.getText(R.string.text_amount,getActivity()));
        textVLabelAcount.setText(Utility.getText(R.string.text_name_card,getActivity()));
        textVLabelCreditCard.setText(Utility.getText(R.string.text_credit_card,getActivity()));

        edVAccount.setHint(Utility.getText(R.string.text_name_card,getActivity()));
        edVCVC.setHint(Utility.getText(R.string.text_ccv,getActivity()));
        edVAmount.setHint(Utility.getText(R.string.text_amount,getActivity()));
        edVCreditCard.setHint(Utility.getText(R.string.text_credit_card,getActivity()));
    }
    private void firstLoad()
    {


    }
    public void onClick(View v)
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (v.getId())
        {
            case R.id.textVCustomUserSettingAction:
                Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentCustomProfileLocation.class.getName());
                ManagerChangeFragment.addFragment(fm, fragment, R.id.frameProfile, FragmentCustomProfileTopupInputBank.class.getName());
                break;
            case R.id.sivBackActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                break;
            case R.id.sivHomeActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileCreditVia.class.getName());
                ManagerChangeFragment.removeFragment(fm,FragmentAccount.class.getName());
                FrameLayout fl =  (FrameLayout) getActivity().findViewById(R.id.frameContent);
                if(fl!=null)
                    fl.setVisibility(View.VISIBLE);
                break;
        }
    }


}	

