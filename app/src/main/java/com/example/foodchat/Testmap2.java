//package com.example.foodchat;
//
//import android.location.Geocoder;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.FragmentActivity;
//
//import net.daum.mf.map.api.MapPoint;
//import net.daum.mf.map.api.MapReverseGeoCoder;
//import net.daum.mf.map.api.MapView;
//
//public class Testmap2 extends FragmentActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {
//    private MapView mapView;
//    private Geocoder geocoder;
//    private Button button;
//    private EditText editText;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.maptest2);
//        editText = findViewById(R.id.editText);
//        button = findViewById(R.id.button);
//
//        mapView = (MapView) findViewById(R.id.maptest2view);
//        mapView.setCurrentLocationEventListener(this);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
//        mapView.setShowCurrentLocationMarker(false);
//    }
//
//
//    @Override
//    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
//
//    }
//
//    @Override
//    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
//
//    }
//
//    @Override
//    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
//
//    }
//
//    @Override
//    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
//
//    }
//
//    @Override
//    public void onCurrentLocationUpdateFailed(MapView mapView) {
//
//    }
//
//    @Override
//    public void onCurrentLocationUpdateCancelled(MapView mapView) {
//
//    }
//}
