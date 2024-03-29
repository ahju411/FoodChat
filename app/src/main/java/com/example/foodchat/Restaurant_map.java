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
        Log.d("식당주소: ",store_address_string);




        //button = findViewById(R.id.buttonmap);


       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String str = editText.getText().toString();
                *//*List<Address> addressList = null;
                try{
                    // editText에 입력한 거를 지오 코딩을 통해서 경도 위도로 바꾸기
                    addressList = geocoder.getFromLocationName(
                            str, 10
                    );

                }catch (IOException e){
                    e.printStackTrace();
                }
                String []splitStr = addressList.get(0).toString().split(",");
                for (int i = 0; i < splitStr.length; i++) {
                    Log.d("내가 검색한 거",splitStr[i]);
                }

                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length()-2); //주소 부분이다
                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); //위도
                String longtitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); //경도

                //MapCoordLatLng point = new MapCoordLatLng(Double.parseDouble(latitude), Double.parseDouble(longtitude));

                MapPOIItem marker = new MapPOIItem();
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(latitude), Double.parseDouble(longtitude));
                marker.setItemName("Default Makrer");
                marker.setTag(0);
                marker.setMapPoint(mapPoint); //위에 mappoint 위도 경도를 따서 표시함
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본 블루핀
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); //클릭 시 레드핀
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
        Log.i("만들어짐", String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
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
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithMarkerHeadingWithoutMapMoving);
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
            Log.d("testLoc","내 주소 받아짐");
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }
    private void setMap(){
        List<Address> addressList = null;
        try{
            // editText에 입력한 거를 지오 코딩을 통해서 경도 위도로 바꾸기
            addressList = geocoder.getFromLocationName(
                    store_address_string, 10
            );

        }catch (IOException e){
            e.printStackTrace();
        }
        String []splitStr = addressList.get(0).toString().split(",");
        for (int i = 0; i < splitStr.length; i++) {
            Log.d("내가 검색한 거",splitStr[i]);
        }

        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length()-2); //주소 부분이다
        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); //위도
        String longtitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); //경도

        //MapCoordLatLng point = new MapCoordLatLng(Double.parseDouble(latitude), Double.parseDouble(longtitude));

        MapPOIItem marker = new MapPOIItem();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(latitude), Double.parseDouble(longtitude));
        marker.setItemName("식당");
        marker.setTag(1);
        marker.setMapPoint(mapPoint); //위에 mappoint 위도 경도를 따서 표시함
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본 블루핀
        marker.setCustomImageResourceId(R.drawable.restaurant2);
        marker.setCustomImageAutoscale(false);
        marker.setCustomImageAnchor(0.5f,1.0f);
        //marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); //클릭 시 레드핀
        mapView.addPOIItem(marker);
        mapView.moveCamera(CameraUpdateFactory.newMapPoint(mapPoint,2));
    }



    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
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

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
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
