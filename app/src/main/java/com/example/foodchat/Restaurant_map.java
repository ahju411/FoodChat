package com.example.foodchat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.daum.android.map.coord.MapCoordLatLng;
import net.daum.mf.map.api.CameraUpdate;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Restaurant_map extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {
    private MapView mapView;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    private Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    private Button button;
    private TextView editText;
    private String store_address_string;
    private LocationManager lm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maptest2);

        Intent getintent = getIntent();
        store_address_string = getintent.getStringExtra("map_address");
        Log.d("????????????: ",store_address_string);




        //button = findViewById(R.id.buttonmap);


       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String str = editText.getText().toString();
                *//*List<Address> addressList = null;
                try{
                    // editText??? ????????? ?????? ?????? ????????? ????????? ?????? ????????? ?????????
                    addressList = geocoder.getFromLocationName(
                            str, 10
                    );

                }catch (IOException e){
                    e.printStackTrace();
                }
                String []splitStr = addressList.get(0).toString().split(",");
                for (int i = 0; i < splitStr.length; i++) {
                    Log.d("?????? ????????? ???",splitStr[i]);
                }

                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length()-2); //?????? ????????????
                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); //??????
                String longtitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); //??????

                //MapCoordLatLng point = new MapCoordLatLng(Double.parseDouble(latitude), Double.parseDouble(longtitude));

                MapPOIItem marker = new MapPOIItem();
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(latitude), Double.parseDouble(longtitude));
                marker.setItemName("Default Makrer");
                marker.setTag(0);
                marker.setMapPoint(mapPoint); //?????? mappoint ?????? ????????? ?????? ?????????
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // ?????? ?????????
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); //?????? ??? ?????????
                mapView.addPOIItem(marker);
                mapView.moveCamera(CameraUpdateFactory.newMapPoint(mapPoint,2));*//*

            }
        });*/

        mapView = (MapView) findViewById(R.id.maptest2view);
        mapView.setCurrentLocationEventListener(this);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.44787309918712,126.65693271868403),true);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithMarkerHeadingWithoutMapMoving);
            }
        },3000);







        if(!checkLocationServicesStatus()){
            checkRunTimePermission();
            showDialogForLocationServiceSetting();
        }else{
            checkRunTimePermission();
        }
        setMap();

    }





    private void getLatLong(String address){

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i("????????????", String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }


    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }




    /*
     * ActivityCompat.requestPermissions??? ????????? ????????? ????????? ????????? ???????????? ??????????????????.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // ?????? ????????? PERMISSIONS_REQUEST_CODE ??????, ????????? ????????? ???????????? ??????????????????

            boolean check_result = true;


            // ?????? ???????????? ??????????????? ???????????????.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {
                Log.d("@@@", "start");
                //?????? ?????? ????????? ??? ??????
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithMarkerHeadingWithoutMapMoving);
            } else {
                // ????????? ???????????? ????????? ?????? ????????? ??? ?????? ????????? ??????????????? ?????? ???????????????.2 ?????? ????????? ????????????.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(this, "???????????? ?????????????????????. ?????? ?????? ???????????? ???????????? ??????????????????.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(this, "???????????? ?????????????????????. ??????(??? ??????)?????? ???????????? ???????????? ?????????. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //????????? ????????? ??????
        // 1. ?????? ???????????? ????????? ????????? ???????????????.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. ?????? ???????????? ????????? ?????????
            // ( ??????????????? 6.0 ?????? ????????? ????????? ???????????? ???????????? ????????? ?????? ????????? ?????? ???????????????.)


            // 3.  ?????? ?????? ????????? ??? ??????
            Log.d("testLoc","??? ?????? ?????????");
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);


        } else {  //2. ????????? ????????? ????????? ?????? ????????? ????????? ????????? ???????????????. 2?????? ??????(3-1, 4-1)??? ????????????.

            // 3-1. ???????????? ????????? ????????? ??? ?????? ?????? ????????????
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. ????????? ???????????? ?????? ?????????????????? ???????????? ????????? ????????? ???????????? ????????? ????????????.
                Toast.makeText(this, "??? ?????? ??????????????? ?????? ?????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
                // 3-3. ??????????????? ????????? ????????? ?????????. ?????? ????????? onRequestPermissionResult?????? ???????????????.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. ???????????? ????????? ????????? ??? ?????? ?????? ???????????? ????????? ????????? ?????? ?????????.
                // ?????? ????????? onRequestPermissionResult?????? ???????????????.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }
    private void setMap(){
        List<Address> addressList = null;
        try{
            // editText??? ????????? ?????? ?????? ????????? ????????? ?????? ????????? ?????????
            addressList = geocoder.getFromLocationName(
                    store_address_string, 10
            );

        }catch (IOException e){
            e.printStackTrace();
        }
        String []splitStr = addressList.get(0).toString().split(",");
        for (int i = 0; i < splitStr.length; i++) {
            Log.d("?????? ????????? ???",splitStr[i]);
        }

        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length()-2); //?????? ????????????
        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); //??????
        String longtitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); //??????

        //MapCoordLatLng point = new MapCoordLatLng(Double.parseDouble(latitude), Double.parseDouble(longtitude));

        MapPOIItem marker = new MapPOIItem();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(latitude), Double.parseDouble(longtitude));
        marker.setItemName("??????");
        marker.setTag(1);
        marker.setMapPoint(mapPoint); //?????? mappoint ?????? ????????? ?????? ?????????
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // ?????? ?????????
        marker.setCustomImageResourceId(R.drawable.restaurant2);
        marker.setCustomImageAutoscale(false);
        marker.setCustomImageAnchor(0.5f,1.0f);
        //marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); //?????? ??? ?????????
        mapView.addPOIItem(marker);
        mapView.moveCamera(CameraUpdateFactory.newMapPoint(mapPoint,2));
    }



    //??????????????? GPS ???????????? ?????? ????????????
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("?????? ????????? ????????????");
        builder.setMessage("?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n"
                + "?????? ????????? ???????????????????");
        builder.setCancelable(true);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //???????????? GPS ?????? ???????????? ??????
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS ????????? ?????????");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
