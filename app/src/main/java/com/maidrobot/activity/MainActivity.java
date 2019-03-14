package com.maidrobot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import qsbk.app.BoboSDK;
import qsbk.app.live.ui.adventure.AdventureFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BoboSDK.changeToTest();

        getSupportFragmentManager().beginTransaction().add(R.id.container, new AdventureFragment(), "bobo").commitAllowingStateLoss();
    }

}
