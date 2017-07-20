package app.android.pmdlocker.com.pmd_locker.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.AspectImageView;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;

// --
	public class TutorialSlideFragment extends Fragment {
		
		//public static PlaceSlideFragment newInstance(String i,AppBanner appBanner) {
			  	          
//	          Bundle bundle = new Bundle();
//	          bundle.putString("title", title);
//	          instance.setArguments(bundle);
//	          return pageFragment;
			
			//new PlaceSlideFragment();
	      //}
	        
		
//		public static PlaceSlideFragment newInstance(int i,AppBanner appBanner,RelativeLayout rl_head) {
//			PlaceSlideFragment fr = new PlaceSlideFragment();
//			fr.imageResourceId = i;
//			fr.appDTO = appBanner;
//			System.out.println("1111111init inti glkjsklgskl::"+appBanner);
//			System.out.println("1111111init ppppppp::"+fr.imageResourceId);
//			fr.rlHead = rl_head;
//			fr.setRetainInstance(true);
//			
//			instanceFragment = fr;
//			return fr;
//		}
		private int mIDImageTutorial;
		public TutorialSlideFragment()
		{
			mIDImageTutorial = R.drawable.tutorial1;
		}
		
		@SuppressLint("ValidFragment")
		public TutorialSlideFragment(int id) {
			super();
			mIDImageTutorial = id;			
		}
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			View view = View.inflate(getActivity(),R.layout.item_tutorial, null);
			AspectImageView siv = (AspectImageView) view.findViewById(R.id.ivTutorial);
			Picasso.with(getContext()).load(mIDImageTutorial).into(siv);

			return view;
			//return parent;			
		}
		@Override
		public void onStop()
		{
			super.onStop();

		}
		@Override
		public void onStart()
		{
			super.onStart();

		}
	}