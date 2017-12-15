package com.example.user.uprice;

import android.app.FragmentTransaction;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.uprice.base.BaseFragment;
import com.example.user.uprice.libs.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by user on 2017/11/29.
 */

public class MapStationFragment extends BaseFragment implements OnMapReadyCallback {

    MapFragment mMapFragment;
    View myView;
    Button buttonSearch;
    GoogleMap map=null;

    /* private static final LatLng model1.name = new LatLng(model1.longitude,model1.latitude);
 */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_maps, container, false);
        buttonSearch = myView.findViewById(R.id.button_search);
        setView();
        return myView;
    }

    private void setView() {
        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                List<StationModel> nearStation = Utils.getNearStation(getBaseApplication(), map.getCameraPosition().zoom, map.getCameraPosition().target);
                for(StationModel stationModel:nearStation){
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(stationModel.latitude, stationModel.longitude))
                            .title(stationModel.name)
                            .snippet(stationModel.address + "\t" + stationModel.time)

                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_gasstationmark)));
                }
            }
        });
    }
    private LocationManager locationManager;

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        locationManager = (LocationManager) getActivity()
                .getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setCompassEnabled(true);
        map.setMyLocationEnabled(true);
        map.setMaxZoomPreference(18);
        map.setMinZoomPreference(12);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16)                   // Sets the zoom
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
