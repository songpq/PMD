package app.android.pmdlocker.com.pmd_locker.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.android.pmdlocker.com.pmd_locker.R;

public class PagerViewTutorialFragment extends FragmentPagerAdapter{
	private Context _context;
	
	public final static int []ARR_DRAW_TUTORIAL = new int []{R.drawable.tutorial1,R.drawable.tutorial2,R.drawable.tutorial3,R.drawable.tutorial4,R.drawable.tutorial5,R.drawable.tutorial6,R.drawable.tutorial7};
	FragmentManager _fm;
	
	public PagerViewTutorialFragment(FragmentManager fm,Context c) {
		super(fm);
		_context = c;		
		_fm = fm;		
	}
	
	 // function created tab tittle for view pager
	
	@Override
	public CharSequence getPageTitle(int position) {		
			return "";
	}

	@Override
	public int getCount() {
		return ARR_DRAW_TUTORIAL.length;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new TutorialSlideFragment(ARR_DRAW_TUTORIAL[position]);
		
       return fragment;
        
	}
}	

