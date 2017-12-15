package com.example.user.uprice.base;

import android.app.Fragment;

/**
 * Created by user on 2017/12/12.
 */

public class BaseFragment extends Fragment {


    public BaseApplication getBaseApplication() {
        return ((BaseActivity) (getActivity())).getBaseApplication();
    }
}
