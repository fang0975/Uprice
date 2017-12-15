package com.example.user.uprice.base;

import android.app.Application;

import com.example.user.uprice.StationModel;

import java.util.List;

/**
 * Created by user on 2017/12/12.
 */

public class BaseApplication extends Application {

    private List<StationModel> stationModelList;


    public List<StationModel> getStationModelList() {
        return stationModelList;
    }

    public void setStationModelList(List<StationModel> stationModelList) {
        this.stationModelList = stationModelList;
    }
}
