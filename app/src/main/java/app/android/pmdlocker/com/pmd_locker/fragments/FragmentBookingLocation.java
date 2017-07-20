package app.android.pmdlocker.com.pmd_locker.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.objects.HLockerLocation;
import app.android.pmdlocker.com.pmd_locker.models.responses.HLockerLocationResponse;
import app.android.pmdlocker.com.pmd_locker.networks.BaseEndpoint;
import app.android.pmdlocker.com.pmd_locker.networks.BaseGenerator;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBookingLocation extends FragmentBase implements View.OnClickListener,OnMapReadyCallback {

    private final static String TAG = FragmentBookingLocation.class.getName();


    View viewTopBar;
    ScaleImageView sivBackTopBar;
    TextView textVTitleTopBar;
    ScaleImageView sivHomeActionBar;

    TextView textVTitleTop;
    TextView textVSafekeepStation;
    SupportMapFragment supportMapFragment;
    GoogleMap googleMap;

    private static View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        if (view != null) {
//            ViewGroup parent = (ViewGroup) view.getParent();
//            if (parent != null)
//                parent.removeView(view);
//        }

        try {
            Log.d(TAG, "onCreateView");
            view = inflater.inflate(R.layout.fragment_booking_location, null);
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
        textVTitleTopBar.setText(Utility.getTextHtml(R.string.text_title_booking,getActivity()));
        sivHomeActionBar = (ScaleImageView)v.findViewById(R.id.sivHomeActionBar);
        sivHomeActionBar.setOnClickListener(this);
    }
    LatLng latLngSelect=null;
    String cityName = null;
    private void findId(final View v)
    {
        initTopBar(v);

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(l==null && googleMap!=null) {
                    l = (Location) location;
                    pos = new LatLng(l.getLatitude(), l.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                    latLngSelect = pos;
                    cityName = getCityName(latLngSelect);
                }
                l = (Location) location;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };


        textVTitleTop = (TextView)v.findViewById(R.id.textVTitleTop);
        textVSafekeepStation = (TextView)v.findViewById(R.id.textVSafekeepStation);
//        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapLocation);

//        supportMapFragment = (SupportMapFragment) fm.findFragmentByTag("MapFragment");

        if (supportMapFragment == null)
        {
            supportMapFragment = SupportMapFragment.newInstance();

            fm.beginTransaction().replace(R.id.mapLocation, supportMapFragment)
//            fm.beginTransaction().add(R.id.mapLocation, supportMapFragment,"MapFragment")
                    .commit();
//            fm.executePendingTransactions();
        }
        supportMapFragment.getMapAsync(this);


    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleClientMap(googleMap);
            } else {
                checkLocationPermission();
            }
        }
        else
            buildGoogleClientMap(googleMap);
    }
    GoogleApiClient mGoogleApiClient;
    protected synchronized void buildGoogleApiClient() {

    }
    LocationListener ll;
    Location l;

    LatLng pos;
    LocationManager lm;
    private void buildGoogleClientMap(final GoogleMap googleMap)
    {

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        if(getActivity()==null)
            return;
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    lm.requestLocationUpdates(lm.NETWORK_PROVIDER, 0, 0, ll);
                    googleMap.setMyLocationEnabled(true);

//            if(l!=null) {
//                pos = new LatLng(l.getLatitude(), l.getLongitude());
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
//            }

                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            /*map.setOnMapLongClickListener(MapyFragment.this);
            map.setOnMapClickListener(MapFragment.this);*/
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
//                    latLngSelect = latLng;
////                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.marker);
//                    cityName = getCityName(latLng);
//                    MarkerOptions mo = new MarkerOptions();
//                    mo.position(latLng);
//                    mo.title(getCityName(latLng));
//                    mo.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker));
//                    googleMap.addMarker(mo);
                        }
                    });
                    getListKiosk();
                }
//            }
//        },2000);

    }
    private void addMarker(HLockerLocation hLockerLocation)
    {
        try {
            MarkerOptions mo = new MarkerOptions();
            final LatLng latLng = new LatLng(Double.parseDouble(hLockerLocation.getLatitude()), Double.parseDouble(hLockerLocation.getLongitude()));
            mo.position(latLng);
            mo.title(hLockerLocation.getLocation());
            mo.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker));
            googleMap.addMarker(mo);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }, 100);

            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }catch (Exception e)
        {

        }
//        final CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
//        googleMap.animateCamera(center, new GoogleMap.CancelableCallback() {
//            @Override
//            public void onFinish() {
//                googleMap.moveCamera(center);
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });

    }
    private String getCityName(LatLng latLng)
    {
        if(getActivity()==null)
            return "";
        Geocoder geocoder = new Geocoder(
                getActivity(), Locale
                .getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude,
                    latLng.longitude, 1);
            String CityName = addresses.get(0).getAddressLine(0);
            return CityName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), Utility.getText(R.string.text_permission_denied,getActivity()), Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void initDefault()
    {
        textVTitleTop.setText(Utility.getTextHtml(R.string.text_location_locker,getActivity()));
    }
    private void firstLoad()
    {


    }
    public void onClick(View v)
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (v.getId())
        {

            case R.id.sivBackActionBar:
            case R.id.sivHomeActionBar:
//                ManagerChangeFragment.removeFragment(fm,this);
                Fragment fragmentHome = fm.findFragmentByTag(FragmentHomeMainMenu.class.getName());
                if(fragmentHome==null) {
                    Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentHomeMainMenu.class.getName());
                    ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);
                }
                else {
                    fm.beginTransaction().remove(fragmentHome).commit();
                    Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentHomeMainMenu.class.getName());
                    ManagerChangeFragment.showFragment(fm, fragment, R.id.frameContent, null);

                }

                FrameLayout fl =  (FrameLayout) getActivity().findViewById(R.id.frameContent);
                if(fl!=null)
                    fl.setVisibility(View.VISIBLE);
                break;


        }
    }
    @Override
    public void onPause() {
        super.onPause();

        final FragmentManager fragManager = this.getChildFragmentManager();
        final Fragment fragment = fragManager.findFragmentById(R.id.mapLocation);
        if(fragment!=null){
            fragManager.beginTransaction().remove(fragment).commit();
        }
    }
    @Override
    public void onDestroy() {

        final FragmentManager fragManager = this.getChildFragmentManager();
        final Fragment fragment = fragManager.findFragmentById(R.id.mapLocation);
        if(fragment!=null){
            fragManager.beginTransaction().remove(fragment).commit();
        }
        super.onDestroy();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        SupportMapFragment f = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapLocation);
//        final FragmentManager fragManager = this.getChildFragmentManager();
//        final Fragment fragment = fragManager.findFragmentById(R.id.mapLocation);
//        if(fragment!=null){
//            fragManager.beginTransaction().remove(fragment).commit();
//        }
    }
    @Override
    public void onAttachFragment(Fragment fragment)
    {
        super.onAttachFragment(fragment);
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapLocation);
        if (supportMapFragment == null)
        {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapLocation, supportMapFragment)
//            fm.beginTransaction().add(R.id.mapLocation, supportMapFragment,"MapFragment")
                    .commit();
//            fm.executePendingTransactions();

        }
        supportMapFragment.getMapAsync(this);
    }
    @Override
    public void onResume()
    {

        super.onResume();
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapLocation);
        if (supportMapFragment == null)
        {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapLocation, supportMapFragment)
//            fm.beginTransaction().add(R.id.mapLocation, supportMapFragment,"MapFragment")
                    .commit();
//            fm.executePendingTransactions();

        }
        supportMapFragment.getMapAsync(this);
//        if(hLockerLocationResponse!=null)
//            getListKioskSuccess(hLockerLocationResponse);
    }
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            FragmentManager fm = getChildFragmentManager();
            supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapLocation);
            if (supportMapFragment == null)
            {
                supportMapFragment = SupportMapFragment.newInstance();
                fm.beginTransaction().replace(R.id.mapLocation, supportMapFragment)
//            fm.beginTransaction().add(R.id.mapLocation, supportMapFragment,"MapFragment")
                        .commit();
//            fm.executePendingTransactions();

            }

            supportMapFragment.getMapAsync(this);
        }
    }
//    private static HLockerLocationResponse hLockerLocationResponse;
    private void getListKioskSuccess(HLockerLocationResponse hLockerLocationResponse)
    {
        Utility.closeDialogLoading();
//        this.hLockerLocationResponse = hLockerLocationResponse;
        if(hLockerLocationResponse!=null) {
            if (hLockerLocationResponse.getSuccess()) {
                for (int i = 0; i < hLockerLocationResponse.getData().size(); i++) {
                    addMarker(hLockerLocationResponse.getData().get(i));
                }
            } else
                Toast.makeText(getActivity(), hLockerLocationResponse.getMessage(), Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getActivity(),Utility.getText(R.string.text_error_unknown,getActivity()),Toast.LENGTH_SHORT).show();
        }
    }
    private void getListKiosk()
    {
        Utility.showDialogLoading(getActivity());


        Map<String,String> params = new HashMap<>();
        BaseEndpoint bService = BaseGenerator.createService(BaseEndpoint.class, GlobalVariable.ACCESSTOKEN, GlobalVariable.Language, params);

        bService.getListKiosk().enqueue(new Callback<HLockerLocationResponse>() {
            @Override
            public void onResponse(Call<HLockerLocationResponse> call, Response<HLockerLocationResponse> response) {
                getListKioskSuccess(response.body());
            }

            @Override
            public void onFailure(Call<HLockerLocationResponse> call, Throwable t) {
                getListKioskFailed();
            }
        });


    }
    private void getListKioskFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(getActivity(),Utility.getTextHtml(R.string.text_error_connection,getActivity()),Toast.LENGTH_LONG).show();
    }
}	

