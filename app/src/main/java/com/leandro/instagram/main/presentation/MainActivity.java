package com.leandro.instagram.main.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractActivity;
import com.leandro.instagram.main.camera.presentation.AddActivity;
import com.leandro.instagram.main.home.datasource.HomeDataSource;
import com.leandro.instagram.main.home.datasource.HomeLocalDataSource;
import com.leandro.instagram.main.home.presentation.HomeFragment;
import com.leandro.instagram.main.home.presentation.HomePresenter;
import com.leandro.instagram.main.profile.datasource.ProfileDataSource;
import com.leandro.instagram.main.profile.datasource.ProfileLocalDataSource;
import com.leandro.instagram.main.profile.presentation.ProfileFragment;
import com.leandro.instagram.main.profile.presentation.ProfilePresenter;
import com.leandro.instagram.main.search.presetation.SearchFragment;

public class MainActivity extends AbstractActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView {

    public static final int LOGIN_ACTIVITY = 0;
    public static final int REGISTER_ACTIVITY = 1;
    public static final String ACT_SOURCE = "act_source";

    private ProfilePresenter profilePresenter;
    private HomePresenter homePresenter;

    Fragment homeFragment;
    Fragment profileFragment;
  //  Fragment cameraFragment;
    Fragment searchFragment;
    Fragment active;


    public static void launch(android.content.Context context, int source) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACT_SOURCE, source);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            Drawable drawable = getResources().getDrawable(R.drawable.ic_insta_camera);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onInject() {
        ProfileDataSource profileDataSource= new ProfileLocalDataSource();
         profilePresenter = new ProfilePresenter(profileDataSource);

        HomeDataSource homeDataSource = new HomeLocalDataSource();
        homePresenter = new HomePresenter(homeDataSource);

        homeFragment = HomeFragment.newInstance(this, homePresenter);
        profileFragment = ProfileFragment.newInstance(this, profilePresenter);
    //    cameraFragment = new CameraFragment();
        searchFragment = new SearchFragment();

        active = homeFragment;

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.main_fragment, profileFragment).hide(profileFragment).commit();
    //    fm.beginTransaction().add(R.id.main_fragment, cameraFragment).hide(cameraFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, searchFragment).hide(searchFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, homeFragment).hide(homeFragment).commit();

    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.main_progress).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        findViewById(R.id.main_progress).setVisibility(View.GONE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        BottomNavigationView bv = findViewById(R.id.main_bottom_nav);
        bv.setOnNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int source = extras.getInt(ACT_SOURCE);
            if (source == REGISTER_ACTIVITY) {
                getSupportFragmentManager().beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                scrollToolbarEnabled(true);
                profilePresenter.findUser();
            }
        }
    }

    @Override
    public void scrollToolbarEnabled(boolean enabled) {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.main_appbar);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        if (enabled) {
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
            appBarLayout.setLayoutParams(appBarLayoutParams);
        } else {
            params.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
            appBarLayout.setLayoutParams(appBarLayoutParams);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.menu_bottom_home:
                fm.beginTransaction().hide(active).show(homeFragment).commit();
                homePresenter.findFeed();
                active = homeFragment;
                scrollToolbarEnabled(false);
                return true;
            case R.id.menu_bottom_search:
                fm.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                return true;
            case R.id.menu_bottom_profile:
                fm.beginTransaction().hide(active).show(profileFragment).commit();
   //             profilePresenter.findUser();
     //           active = profileFragment;
                scrollToolbarEnabled(true);
                return true;
            case R.id.menu_bottom_add:
//                fm.beginTransaction().hide(active).show(cameraFragment).commit();
//                active = cameraFragment;
                AddActivity.launch(this);
                return true;
        }
        return false;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}