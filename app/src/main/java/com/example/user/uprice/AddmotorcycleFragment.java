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

public class AddmotorcycleFragment extends BaseFragment {
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.addmotorcycle_layout, container , false);
        getActivity().setTitle("新增一台機車");
        return myView;
    }

}
