package com.vit.app.custom_launcher.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.vit.app.custom_launcher.R;

/**
 * Created by Alexander Belokopytov on 16.02.2015.
 *
 * it is a root activity parent for GridFragment
 */
public class RootActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_layout);
    }
}
