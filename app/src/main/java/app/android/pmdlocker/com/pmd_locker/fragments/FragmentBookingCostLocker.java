package app.android.pmdlocker.com.pmd_locker.fragments;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.AspectImageView;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;


public class FragmentBookingCostLocker extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentBookingCostLocker.class.getName();



    AspectImageView asIVVoucher;
    TextView textVTitleTop;
    TextView textVHintBottom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_booking_cost_locker, null);
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
        asIVVoucher = (AspectImageView)v.findViewById(R.id.asIVVoucher);
        textVTitleTop = (TextView)v.findViewById(R.id.textVTitleTop);
        textVHintBottom = (TextView)v.findViewById(R.id.textVHintBottom);

    }
    private void initDefault()
    {
        textVTitleTop.setText(Utility.getTextHtml(R.string.text_hint_top_daily_deal,getActivity()));
        String hint = Utility.getText(R.string.text_hint_bottom_daily_deal,getActivity()).replace("{s}","2");
        textVHintBottom.setText(Html.fromHtml(hint));
    }
    private void firstLoad()
    {


    }
    public void onClick(View v)
    {

    }


}	

