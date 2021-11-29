package com.leandro.instagram.main.camera.presentation;

import android.content.Intent;
import android.graphics.Camera;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractActivity;
import com.leandro.instagram.main.presentation.Context;

import butterknife.BindView;

public class AddActivity extends AbstractActivity implements AddView {
    @BindView(R.id.add_viewpager)
    ViewPager viewPager;

    @BindView(R.id.add_tab_layout)
    TabLayout tabLayout;

    public static void launch(Context context) {
        Intent intent = new Intent(context, AddActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            Drawable ic_close = findDrawable(R.drawable.ic_close);
            getSupportActionBar().setHomeAsUpIndicator(ic_close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(findColor(R.color.blueEnable));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                Log.d("Teste", "" + tab.getPosition());
            }
        });
    }

    @Override
    protected void onInject() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        GalleryFragment galleryFragment = new GalleryFragment();
        adapter.add(galleryFragment);

        CameraFragment cameraFragment = new CameraFragment().newInstance(this);
        adapter.add(cameraFragment);

        adapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tabLeft = tabLayout.getTabAt(0);
        if (tabLeft != null)
            tabLeft.setText(getString(R.string.gallery));

        TabLayout.Tab tabRight = tabLayout.getTabAt(1);
        if (tabRight != null)
            tabRight.setText(getString(R.string.photo));

        viewPager.setCurrentItem(adapter.getCount() - 1);
    }

    @Override
    public void onImageLoaded(Uri uri) {
        AddCaptionActivity.lauch(this, uri);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add;
    }


}