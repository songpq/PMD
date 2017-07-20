package app.android.pmdlocker.com.pmd_locker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.MapsInitializer;
import com.xdu.xhin.library.view.ChangeColorTab;

import java.util.ArrayList;
import java.util.List;

import app.android.pmdlocker.com.pmd_locker.fragments.FragmentHomeMainMenu;
import app.android.pmdlocker.com.pmd_locker.fragments.FragmentMainMenu;
import app.android.pmdlocker.com.pmd_locker.fragments.FragmentTutorial;
import app.android.pmdlocker.com.pmd_locker.fragments.ManagerChangeFragment;
import app.android.pmdlocker.com.pmd_locker.fragments.PagerViewMenuFragment;
import app.android.pmdlocker.com.pmd_locker.libraries.ChangeColorItemExtra;
import app.android.pmdlocker.com.pmd_locker.models.objects.HUserRegister;
import app.android.pmdlocker.com.pmd_locker.utils.ConstantGlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_account:

                    return true;

            }
            return false;
        }

    };
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Log.d("HashKey",Utility.getHashKey(this));
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            Log.e("Address Map", "Could not initialize google play", e);
        }
        initView();
        firstLoad();

    }

    private void firstLoad()
    {
        GlobalVariable.userLogin = Utility.getUserInfo(this);
//        GlobalVariable.userLogin = null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int isShowTutorial = Utility.getKeyInt(ConstantGlobalVariable.SharedPreferencesKey.KEY_SHOW_TUTORIAL,1,sharedPreferences);
        if(isShowTutorial!=1) {
            if(Utility.isLogin(GlobalVariable.userLogin))
            {
                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentHomeMainMenu.class.getName());
                ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
                showPagerViewBottomBar();
            }
            else {
                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentMainMenu.class.getName());
                ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
                changeColorTab.setVisibility(View.GONE);
                mViewPager.setVisibility(View.GONE);
            }
        }
        else
        {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentTutorial.class.getName());
            ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
            changeColorTab.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
        }

    }
    ViewPager mViewPager;
    ChangeColorTab changeColorTab;
    private int backPage = 0;
    List<ChangeColorItemExtra> lstItems = new ArrayList<>();
    FrameLayout frameLogo;

    private void initView()
    {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.getMenu().clear();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GlobalVariable.WidthScreen = displayMetrics.widthPixels;
        GlobalVariable.HeightScreen = displayMetrics.heightPixels;

        frameLogo = (FrameLayout)findViewById(R.id.frameLogo);
        frameLogo.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                frameLogo.setVisibility(View.GONE);
            }
        }, ConstantGlobalVariable.TIME_SHOW_LOGO);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        changeColorTab = (ChangeColorTab) findViewById(R.id.change_color_tab);

        changeColorTab.setVisibility(View.VISIBLE);
        changeColorTab.setViewpager(mViewPager);

        ChangeColorItemExtra itemExtra = (ChangeColorItemExtra)findViewById(R.id.itemAccount);
        lstItems.add(itemExtra);
        itemExtra = (ChangeColorItemExtra)findViewById(R.id.itemLocation);
        lstItems.add(itemExtra);
        itemExtra = (ChangeColorItemExtra)findViewById(R.id.itemWallet);
        lstItems.add(itemExtra);
        itemExtra = (ChangeColorItemExtra)findViewById(R.id.itemBooking);
        lstItems.add(itemExtra);
        itemExtra = (ChangeColorItemExtra)findViewById(R.id.itemNotifications);
        lstItems.add(itemExtra);
        itemExtra = (ChangeColorItemExtra)findViewById(R.id.itemDeals);
        lstItems.add(itemExtra);
        for(int i=0;i<lstItems.size();i++)
            lstItems.get(i).setResBGNormal(R.drawable.bg_menu_normal);
        lstItems.get(0).setResBGSelected(R.drawable.bg_menu_selected);
        changeColorTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));



    }
    public void updateStatusBar(int curSelected)
    {
        for(int i=0;i<lstItems.size();i++)
        {
            if(i==curSelected)
                lstItems.get(i).setResBGSelected(R.drawable.bg_menu_selected);
            else
                lstItems.get(i).setResBGSelected(0);
            lstItems.get(i).invalidate();
        }

    }
    public void clearLogout()
    {
        LoginManager.getInstance().logOut();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentMainMenu.class.getName());
        ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
        FrameLayout fl =  (FrameLayout) findViewById(R.id.frameContent);
        if(fl!=null)
            fl.setVisibility(View.VISIBLE);
        changeColorTab.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        mViewPager.setAdapter(null);
        Utility.clearUser(this);
    }
    public void showPagerViewBottomBar()
    {
        changeColorTab.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        PagerViewMenuFragment pagerAdapter = new PagerViewMenuFragment(fm, getApplicationContext(), lstItems);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                lstItems.get(backPage).setResBGSelected(0);
                lstItems.get(position).setResBGSelected(R.drawable.bg_menu_selected);
                backPage = position;
                updateStatusBar(position);
                FrameLayout fl =  (FrameLayout) MainActivity.this.findViewById(R.id.frameContent);
                if(fl!=null)
                    fl.setVisibility(View.GONE);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagerAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == ConstantGlobalVariable.REQUEST_ACTIVITY_LOGIN ))
        {
            this.setResult(RESULT_OK, data);
            GlobalVariable.userLogin  = (HUserRegister) data.getSerializableExtra(HUserRegister.class.getName());
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentHomeMainMenu.class.getName());
            ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
            showPagerViewBottomBar();
        }
        if (resultCode == RESULT_OK &&
                (requestCode == ConstantGlobalVariable.REQUEST_ACTIVITY_SIGN_UP ))
        {
            this.setResult(RESULT_OK, data);
            GlobalVariable.userLogin  = (HUserRegister) data.getSerializableExtra(HUserRegister.class.getName());
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentHomeMainMenu.class.getName());
            ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
            showPagerViewBottomBar();
        }

    }
}
