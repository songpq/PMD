package app.android.pmdlocker.com.pmd_locker.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
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

import app.android.pmdlocker.com.pmd_locker.LoginActivity;
import app.android.pmdlocker.com.pmd_locker.R;
import app.android.pmdlocker.com.pmd_locker.libraries.ScaleImageView;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserOTPResponse;
import app.android.pmdlocker.com.pmd_locker.models.responses.UserResponse;
import app.android.pmdlocker.com.pmd_locker.networks.BaseEndpoint;
import app.android.pmdlocker.com.pmd_locker.networks.BaseGenerator;
import app.android.pmdlocker.com.pmd_locker.utils.GlobalVariable;
import app.android.pmdlocker.com.pmd_locker.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCustomProfileLocation extends FragmentBase implements View.OnClickListener,OnMapReadyCallback {

    private final static String TAG = FragmentCustomProfileLocation.class.getName();


    View viewTopBar;
    ScaleImageView sivBackTopBar;
    TextView textVTitleTopBar;
    ScaleImageView sivHomeActionBar;

    TextView textVNextAction;
    TextView textVTitleTop;
    SupportMapFragment supportMapFragment;
    GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            Log.d(TAG, "onCreateView");
            View view = inflater.inflate(R.layout.fragment_custom_profile_location, null);
            findId(view);
            initDefault();
            firstLoad();

            return view;
        } catch (Exception ex) {
            Log.i(TAG, Log.getStackTraceString(ex));
            return null;
        }
    }
    @Override
    public void onAttachFragment(Fragment fragment)
    {
        super.onAttachFragment(fragment);
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapLocationProfile);
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
    public void onDestroy() {

        final FragmentManager fragManager = this.getChildFragmentManager();
        final Fragment fragment = fragManager.findFragmentById(R.id.mapLocationProfile);
        if(fragment!=null){
            fragManager.beginTransaction().remove(fragment).commit();
        }
        super.onDestroy();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        SupportMapFragment f = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapLocationProfile);
//        if (f != null)
//            getFragmentManager().beginTransaction().remove(f).commit();
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
        textVTitleTopBar.setText(Utility.getTextHtml(R.string.text_profile_customization,getActivity()));
        sivHomeActionBar =(ScaleImageView)v.findViewById(R.id.sivHomeActionBar);
        sivHomeActionBar.setOnClickListener(this);
    }
    private void findId(View v)
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
//        Criteria criteria = new Criteria();
//        String provider = lm.getBestProvider(criteria, true);
//        l = lm.getLastKnownLocation(provider);
//        if (l != null) {
//            onLocationChanged(location);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(lm.NETWORK_PROVIDER, 0, 0, ll);
        }


        textVTitleTop = (TextView)v.findViewById(R.id.textVTitleTop);
        textVNextAction = (TextView)v.findViewById(R.id.textVNextAction);
        textVNextAction.setOnClickListener(this);
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapLocationProfile);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapLocationProfile, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);


    }
    LocationListener ll;
    Location l=null;

    LatLng pos;
    LocationManager lm;
    private void buildGoogleClientMap(final GoogleMap googleMap)
    {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        if(getActivity()==null)
            return;
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    lm.requestLocationUpdates(lm.NETWORK_PROVIDER, 0, 0, ll);
                    googleMap.setMyLocationEnabled(true);

                    if(GlobalVariable.userLogin.getPreferredLockerLocation()!=null) {
                        String txt = GlobalVariable.userLogin.getPreferredLockerLocation();
                        txt = txt.replace("(","");
                        txt = txt.replace(")","");
                        String []split = txt.split(",");
                        if(split.length==2 && !TextUtils.isEmpty(split[0]) && !TextUtils.isEmpty(split[1])) {
                            double latiude = Double.parseDouble(split[0]);
                            double longitude = Double.parseDouble(split[1]);
                            latLngSelect = new LatLng(latiude, longitude);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLngSelect));


                            MarkerOptions mo = new MarkerOptions();
                            mo.position(latLngSelect);
                            mo.title(GlobalVariable.userLogin.getPreferredLockerLocationName());
                            mo.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker));
                            googleMap.addMarker(mo);
                        }
                    }


                    if(l!=null) {
                        pos = new LatLng(l.getLatitude(), l.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                    }
                    if(latLngSelect!=null) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLngSelect));
                    }
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            /*map.setOnMapLongClickListener(MapyFragment.this);
            map.setOnMapClickListener(MapFragment.this);*/
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            latLngSelect = latLng;
//                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.marker);
                            cityName = getCityName(latLng);
                            MarkerOptions mo = new MarkerOptions();
                            mo.position(latLng);
                            mo.title(getCityName(latLng));
                            mo.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker));
                            googleMap.addMarker(mo);
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                    });
                }
//            }
//        },2000);

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
            String CityName="";
            if(addresses.size()>0)
                CityName = addresses.get(0).getAddressLine(0);
            return CityName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    LatLng latLngSelect=null;
    String cityName = null;
    private void updateLocationSuccess(UserResponse userResponse)
    {
        Utility.closeDialogLoading();
        FragmentManager fm = getFragmentManager();

        if(true)
            return;
        if(userResponse!=null) {
            if(true)//if(userResponse.getSuccess())
            {
//                GlobalVariable.userLogin = userResponse.getData();
//                Utility.saveUserInfo(GlobalVariable.userLogin,getActivity());
                Fragment fragment = ManagerChangeFragment.createFragment(fm, FragmentCustomProfileLocker.class.getName());
                ManagerChangeFragment.addFragment(fm, fragment, R.id.frameProfile, FragmentCustomProfileLocation.class.getName());
            }
            else {
                Toast.makeText(getActivity(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }
        else
            Toast.makeText(getActivity(),Utility.getText(R.string.text_error_unknown,getActivity()),Toast.LENGTH_SHORT).show();
    }
    private void updateLocationFailed()
    {
        Utility.closeDialogLoading();
        Toast.makeText(getActivity(),Utility.getTextHtml(R.string.text_error_connection,getActivity()),Toast.LENGTH_LONG).show();
    }
    private void updateLocation(LatLng latLngSelect,String locationName)
    {
        if(latLngSelect==null || locationName==null)
        {
            Toast.makeText(getActivity(),Utility.getText(R.string.text_select_location,getActivity()),Toast.LENGTH_SHORT).show();
            return;
        }
        String location = "("+latLngSelect.latitude+","+latLngSelect.longitude+")";

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentCustomProfileLocker fragment = (FragmentCustomProfileLocker)ManagerChangeFragment.createFragment(fm, FragmentCustomProfileLocker.class.getName());
        fragment.cityName = locationName;
        fragment.location = location;
        ManagerChangeFragment.addFragment(fm, fragment, R.id.frameProfile, FragmentCustomProfileLocation.class.getName());

        if(true)
            return;
//        Utility.showDialogLoading(getActivity());

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
    GoogleApiClient mGoogleApiClient;
    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                    @Override
//                    public void onConnected(@Nullable Bundle bundle) {
//
//                    }
//
//                    @Override
//                    public void onConnectionSuspended(int i) {
//
//                    }
//                })
//                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//                    }
//                })
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
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

    @Override
    public void onMapReady(GoogleMap googleMapLocal) {
        this.googleMap = googleMapLocal;
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        });
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
    private void initDefault()
    {

        textVNextAction.setText(Utility.getTextHtml(R.string.text_next,getActivity()));
        textVTitleTop.setText(Utility.getTextHtml(R.string.text_which_usually_location,getActivity()));
    }
    private void firstLoad()
    {


    }
    public void onClick(View v)
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        switch (v.getId())
        {
            case R.id.textVNextAction:
                updateLocation(latLngSelect,cityName);

                break;
            case R.id.sivBackActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                break;
            case R.id.sivHomeActionBar:
                ManagerChangeFragment.removeFragment(fm,this);
                ManagerChangeFragment.removeFragment(fm,FragmentCustomProfileLocation.class.getName());
                ManagerChangeFragment.removeFragment(fm,FragmentAccount.class.getName());
                FrameLayout fl =  (FrameLayout) getActivity().findViewById(R.id.frameContent);
                if(fl!=null)
                    fl.setVisibility(View.VISIBLE);
                break;
        }
    }


}	

