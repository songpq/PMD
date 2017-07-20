package app.android.pmdlocker.com.pmd_locker.fragments;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Ca. Phan Thanh on 3/27/2017.
 */

public class ManagerChangeFragment {
    public static Fragment createFragment(FragmentManager fm, String fragmentName)
    {

        if(fragmentName == FragmentBookingLocation.class.getName())
        {
            FragmentBookingLocation fragmentBookingLocation = new FragmentBookingLocation();
            fragmentBookingLocation.setRetainInstance(false);
            return fragmentBookingLocation;
        }
        if(fragmentName == FragmentBookingLocker.class.getName())
        {
            FragmentBookingLocker fragmentBookingLocker = new FragmentBookingLocker();
            fragmentBookingLocker.setRetainInstance(false);
            return fragmentBookingLocker;
        }
        if(fragmentName == FragmentBookingWhen.class.getName())
        {
            FragmentBookingWhen fragmentBookingWhen = new FragmentBookingWhen();
            fragmentBookingWhen.setRetainInstance(false);
            return fragmentBookingWhen;
        }


        if(fragmentName == FragmentUpdateProfile.class.getName())
        {
            FragmentUpdateProfile fragmentUpdateProfile = new FragmentUpdateProfile();
            fragmentUpdateProfile.setRetainInstance(false);
            return fragmentUpdateProfile;
        }
        if(fragmentName == FragmentBooking.class.getName())
        {
            FragmentBooking fragmentBooking = new FragmentBooking();
            fragmentBooking.setRetainInstance(false);
            return fragmentBooking;
        }
        if(fragmentName == FragmentDeal.class.getName())
        {
            FragmentDeal fragmentDeal = new FragmentDeal();
            fragmentDeal.setRetainInstance(false);
            return fragmentDeal;
        }
        if(fragmentName == FragmentNotifications.class.getName())
        {
            FragmentNotifications fragmentNotifications = new FragmentNotifications();
            fragmentNotifications.setRetainInstance(false);
            return fragmentNotifications;
        }
        if(fragmentName == FragmentLocation.class.getName())
        {
            FragmentLocation fragmentNotifications = new FragmentLocation();
            fragmentNotifications.setRetainInstance(false);
            return fragmentNotifications;
        }

        if(fragmentName == FragmentMainMenu.class.getName())
        {
            FragmentMainMenu fragmentHome = new FragmentMainMenu();
            fragmentHome.setRetainInstance(false);
            return fragmentHome;
        }
        if(fragmentName == FragmentAccount.class.getName())
        {
            FragmentAccount fragmentAccount = new FragmentAccount();
            fragmentAccount.setRetainInstance(false);
            return fragmentAccount;
        }

        if(fragmentName == FragmentCustomProfileTopup.class.getName())
        {
            FragmentCustomProfileTopup fragmentCustomProfileTopup = new FragmentCustomProfileTopup();
            fragmentCustomProfileTopup.setRetainInstance(false);
            return fragmentCustomProfileTopup;
        }
        if(fragmentName == FragmentCustomProfileTime.class.getName())
        {
            FragmentCustomProfileTime fragmentCustomProfileTime = new FragmentCustomProfileTime();
            fragmentCustomProfileTime.setRetainInstance(false);
            return fragmentCustomProfileTime;
        }
        if(fragmentName == FragmentCustomProfileTopupInputBank.class.getName())
        {
            FragmentCustomProfileTopupInputBank fragmentCustomProfileTopupInputBank = new FragmentCustomProfileTopupInputBank();
            fragmentCustomProfileTopupInputBank.setRetainInstance(false);
            return fragmentCustomProfileTopupInputBank;
        }
        if(fragmentName == FragmentCustomProfileCreditVia.class.getName())
        {
            FragmentCustomProfileCreditVia fragmentCustomProfileCreditVia = new FragmentCustomProfileCreditVia();
            fragmentCustomProfileCreditVia.setRetainInstance(false);
            return fragmentCustomProfileCreditVia;
        }
        if(fragmentName == FragmentCustomProfileSetting.class.getName())
        {
            FragmentCustomProfileSetting fragmentCustomProfileSetting = new FragmentCustomProfileSetting();
            fragmentCustomProfileSetting.setRetainInstance(false);
            return fragmentCustomProfileSetting;
        }
        if(fragmentName == FragmentCustomProfileLocation.class.getName())
        {
            FragmentCustomProfileLocation fragmentCustomProfileLocation = new FragmentCustomProfileLocation();
            fragmentCustomProfileLocation.setRetainInstance(false);
            return fragmentCustomProfileLocation;
        }
        if(fragmentName == FragmentCustomProfileLocker.class.getName())
        {
            FragmentCustomProfileLocker fragmentCustomProfileLocker = new FragmentCustomProfileLocker();
            fragmentCustomProfileLocker.setRetainInstance(false);
            return fragmentCustomProfileLocker;
        }

        if(fragmentName == FragmentTutorial.class.getName())
        {
            FragmentTutorial fragmentTutorial = new FragmentTutorial();
            fragmentTutorial.setRetainInstance(false);
            return fragmentTutorial;
        }
        if(fragmentName == FragmentHomeMainMenu.class.getName())
        {
            FragmentHomeMainMenu fragmentHomeMainMenu = new FragmentHomeMainMenu();
            fragmentHomeMainMenu.setRetainInstance(false);
            return fragmentHomeMainMenu;
        }

        return null;
    }
    public static void showFragment(FragmentManager fm, Fragment fragment, int idContainer, String backToStackName)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(idContainer,fragment,fragment.getClass().getName());
        if(backToStackName!=null)
            ft.addToBackStack(backToStackName);
        ft.commitAllowingStateLoss();
    }
    public static void addFragment(FragmentManager fm, Fragment fragment, String backToStackName)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(fragment,fragment.getClass().getName());
        if(backToStackName!=null)
            ft.addToBackStack(backToStackName);
        ft.commitAllowingStateLoss();
    }


    public static void addFragment(FragmentManager fm, Fragment fragment, int idContainer, String backToStackName)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(idContainer,fragment,fragment.getClass().getName());
        if(backToStackName!=null)
            ft.addToBackStack(backToStackName);
        ft.commitAllowingStateLoss();
    }

    public static boolean removeFragment(FragmentManager fm, Fragment fragment)
    {
        boolean isTrue = fm.findFragmentByTag(fragment.getClass().getName())!=null;
        fm.popBackStack();
        fm.beginTransaction().remove(fragment);
        fm.beginTransaction().commitAllowingStateLoss();
        return isTrue;
    }
    public static Fragment findFragment(FragmentManager fm, String fragmentName)
    {
        Fragment fragment = fm.findFragmentByTag(fragmentName);
        return fragment;
    }
    public static boolean removeFragment(FragmentManager fm, String fragmentName)
    {
        Fragment fragment = fm.findFragmentByTag(fragmentName);
        if(fragment!=null) {
            fm.popBackStack();
            fm.beginTransaction().remove(fragment);
            fm.beginTransaction().commitAllowingStateLoss();
            return true;
        }
        return false;
    }
}
