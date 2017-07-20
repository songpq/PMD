package app.android.pmdlocker.com.pmd_locker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.android.pmdlocker.com.pmd_locker.R;


public class FragmentBooking extends FragmentBase implements View.OnClickListener{

    private final static String TAG = FragmentBooking.class.getName();





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_booking, null);
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


    }
    private void initDefault()
    {

    }
    private void firstLoad()
    {


    }
    public void onClick(View v)
    {

    }


}	

