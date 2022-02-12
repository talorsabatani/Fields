package com.talor.fields;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Fragment_Map extends Fragment {
    private static GoogleMap mMap;
    private boolean markerChanged = false;
    private boolean zoomOnce = false;
    private Marker marker;
    private Marker prevMarker;
    private double userLat;
    private double userLng;
    private CallBack_showPopUp callBack_showPopUp;
    public FieldsData fieldsData;


    public void setCallBack_showPopUp(CallBack_showPopUp callBack_showPopUp) {
        this.callBack_showPopUp = callBack_showPopUp;
    }


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            showParkDetails();
            showMarker(userLat, userLng);
            readFieldsAndShowOnMap();
        }
    };

    public Fragment_Map() {
        this.userLat =0.0;
        this.userLng=0.0;
    }

    public double getUserLat() {
        return userLat;
    }

    public Fragment_Map setUserLat(double userLat) {
        this.userLat = userLat;
        return this;
    }

    public double getUserLng() {
        return userLng;
    }

    public Fragment_Map setUserLng(double userLng) {
        this.userLng = userLng;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


        return view;
    }


    //show current user marker
    public void showMarker(double lat, double lng) {
        if (markerChanged == true) {
            marker.remove();
            markerChanged = false;
        }
        if(mMap!=null) {
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            markerChanged = true;
            if (!zoomOnce) {
                zoomOnMarker(marker);
                zoomOnce = true;
            }
        }

    }

    public void zoomOnMarker(Marker marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13f));
    }

    // add park marker on map
    public void addFieldsMarkers(double lat, double lng, String pid) {
        Marker parkMarker;
        if (mMap != null) {
            parkMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng)));
            parkMarker.setTag(pid);
        }
    }


    public void showParkDetails() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.getTag() != null) {
                    if (prevMarker != null && prevMarker != marker) {
                        prevMarker.setIcon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }
                    prevMarker = marker;
                    marker.setIcon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                    zoomOnMarker(marker);
                    callBack_showPopUp.PopUpWindowOnMap((String) marker.getTag());
                    return true;
                }
                return false;
            }
        });
    }

    private void readFieldsAndShowOnMap() {
        fieldsData = new FieldsData();
        fieldsData.getFields();
        fieldsData.setCallBack_UploadFields(callBack_uploadFields);
    }

    private CallBack_UploadFields callBack_uploadFields = new CallBack_UploadFields() {
        @Override
        public void UploadFields(List<Field> fields_list) {
            for (Field field : fields_list) {
                addFieldsMarkers(field.getLat(), field.getLng(), field.getPid());
            }
        }
    };
}
