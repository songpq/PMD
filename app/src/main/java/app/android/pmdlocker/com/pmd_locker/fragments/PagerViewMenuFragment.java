package app.android.pmdlocker.com.pmd_locker.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.ChangeColorItemExtra;
import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;

public class PagerViewMenuFragment extends FragmentPagerAdapter{
	private Context _context;


	FragmentManager _fm;

	List<ChangeColorItemExtra> lstItemExtra;

	public PagerViewMenuFragment(FragmentManager fm, Context c,List<ChangeColorItemExtra> items) {
		super(fm);
		_context = c;		
		_fm = fm;
		lstItemExtra = items;
	}
	
	 // function created tab tittle for view pager

	private FragmentBase getFragment(int position)
	{
		FragmentBase fragment;
		if(position == ConstantGlobalVariable.POS_FRAGMENT_USERPROFILE)
			return new FragmentAccount();
		if(position == ConstantGlobalVariable.POS_FRAGMENT_LOCATION)
			return new FragmentLocation();
		if(position == ConstantGlobalVariable.POS_FRAGMENT_WALLET)
			return new FragmentWallet();
		if(position == ConstantGlobalVariable.POS_FRAGMENT_BOOKING)
			return new FragmentBooking();
		if(position == ConstantGlobalVariable.POS_FRAGMENT_NOTIFICATIONS)
			return new FragmentNotifications();
		if(position == ConstantGlobalVariable.POS_FRAGMENT_DAILY_DEALS)
			return new FragmentDeal();
		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {		
			return "";
	}

	@Override
	public int getCount() {
		return ConstantGlobalVariable.ARRAY_BOTTOM_MENU.length;
	}

	@Override
	public Fragment getItem(int position) {

        Fragment fragment = null;
		if(position<ConstantGlobalVariable.ARRAY_BOTTOM_MENU.length)
		    fragment = getFragment(position);
       return fragment;
        
	}
}	

