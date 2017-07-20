package app.android.pmdlocker.com.pmd_locker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.apdapters.CreditCardAdapterRecycleView;
import app.android.pmdlocker.com.pmd_locker.apdapters.LockerAdapterRecycleView;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.objects.HCreditCard;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;


public class FragmentCustomProfileCreditVia extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentCustomProfileCreditVia.class.getName();


    View viewTopBar;
    ScaleImageView sivBackTopBar;
    TextView textVTitleTopBar;
    ScaleImageView sivHomeActionBar;

    TextView textVTitleTop;
    TextView textVWalletAmount;
    TextView textVTopupAction;
    RecyclerView recycleViewBank;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_custom_profile_credit_via, null);
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
        textVWalletAmount = (TextView)v.findViewById(R.id.textVWalletAmount);
        textVTopupAction = (TextView)v.findViewById(R.id.textVTopupAction);

        recycleViewBank = (RecyclerView)v.findViewById(R.id.recycleViewBank);
        textVTopupAction.setOnClickListener(this);
    }
    private void initDefault()
    {
        textVTopupAction.setText(Utility.getTextHtml(R.string.text_next,getActivity()));
        textVTitleTop.setText(Utility.getText(R.string.text_may_add_credit,getActivity()));
    }
    private void firstLoad()
    {

        loadViaBank();
    }
    private void loadViaBank()
    {
        ArrayList<HCreditCard> lst = new ArrayList<>();
        HCreditCard hCreditCard;
        hCreditCard = new HCreditCard(R.mipmap.ezlink,"");
        lst.add(hCreditCard);
        hCreditCard = new HCreditCard(R.mipmap.nets,"");
        lst.add(hCreditCard);
        hCreditCard = new HCreditCard(R.mipmap.mastercard,"");
        lst.add(hCreditCard);
        hCreditCard = new HCreditCard(R.mipmap.visa,"");
        lst.add(hCreditCard);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recycleViewBank.setLayoutManager(layoutManager);
        CreditCardAdapterRecycleView creditCardAdapterRecycleView = new CreditCardAdapterRecycleView(getActivity(),lst, GlobalVariable.WidthScreen,GlobalVariable.HeightScreen,null,null);
        recycleViewBank.setAdapter(creditCardAdapterRecycleView);
        creditCardAdapterRecycleView.notifyDataSetChanged();
    }
    public void onClick(View v)
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (v.getId())
        {
            case R.id.textVTopupAction:
                Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentCustomProfileTopupInputBank.class.getName());
                ManagerChangeFragment.addFragment(fm, fragment, R.id.frameProfile, FragmentCustomProfileCreditVia.class.getName());
                break;
            case R.id.sivBackActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                break;
            case R.id.sivHomeActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileCreditVia.class.getName());
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileTopup.class.getName());
                FrameLayout fl =  (FrameLayout) getActivity().findViewById(R.id.frameContent);
                if(fl!=null)
                    fl.setVisibility(View.VISIBLE);
                break;
        }
    }


}	

