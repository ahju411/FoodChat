package com.example.foodchat;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class GpsTracker extends Service implements LocationListener {

    private final Context mContext;
    Location location;
    double latitude; //위도
    double longitude; //경도

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;

    public GpsTracker(Context context){
        this.mContext = context;
        getLocation();
    }

    public Location getLocation(){
        try{
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); //gps나 network가 가능한지 정보를 얻는다.
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled){
                Toast.makeText(this, "GPS나 Network를 사용할 수 없습니다..", Toast.LENGTH_SHORT).show();
            }else{
                int hasFineLoactionPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_COARSE_LOCATION);

                if(hasFineLoactionPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){
                    ;
                }else
                    return  null;

                if (isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);

                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }if (location != null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }

                if (isGPSEnabled){
                    if (location == null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                        if (locationManager != null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        }catch (Exception e){
            Log.d("@@@",""+e.toString());
        }
        return location;
    }

    public double getLatitude() {

        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if(location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void stopUsingGPS(){
        if(locationManager!=null){
            locationManager.removeUpdates(GpsTracker.this);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}

