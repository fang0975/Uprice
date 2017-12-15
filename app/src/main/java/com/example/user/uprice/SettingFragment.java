package com.example.user.uprice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.uprice.base.BaseFragment;

/**
 * Created by user on 2016/12/21.
 */

public class SettingFragment extends BaseFragment {
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.setting_layout, container , false);
        getActivity().setTitle("關於我們");
        return myView;
    }

}
