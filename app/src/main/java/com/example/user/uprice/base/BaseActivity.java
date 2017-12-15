package com.example.user.uprice.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by user on 2017/12/12.
 */

public class BaseActivity extends AppCompatActivity {

    BaseApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initApp();
    }

    public void initApp() {
        application = (BaseApplication) getApplication();
    }

    public BaseApplication getBaseApplication() {
        return application;
    }
}
