package com.example.user.uprice.libs;

import com.example.user.uprice.StationModel;
import com.example.user.uprice.base.BaseApplication;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/12/16.
 */

public class Utils {

    public static List<StationModel> getNearStation(BaseApplication baseApplication, double zoomLevel, LatLng latLng) {
        List<StationModel> nearStation = new ArrayList<>();
        for (StationModel stationModel : baseApplication.getStationModelList()) {
            if (isNear(zoomConvert(zoomLevel), latLng, new LatLng(stationModel.latitude, stationModel.longitude))) {
                nearStation.add(stationModel);
            }
        }
        return nearStation;
    }

    public static boolean isNear(Double pa, LatLng a, LatLng b) {
        if (Math.sqrt(Math.pow(a.latitude - b.latitude, 2.0) + Math.pow(a.longitude - b.longitude, 2.0)) <= pa) {
            return true;
        } else return false;
    }

    public static Double zoomConvert(double zoomLevel) {
        return (591657550.500000 / Math.pow(2.0, zoomLevel - 1.0)/3000000);
    }
}
