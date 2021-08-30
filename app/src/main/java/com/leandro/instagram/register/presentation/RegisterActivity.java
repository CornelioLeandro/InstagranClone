package com.leandro.instagram.register.presentation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractActivity;
import com.leandro.instagram.main.presentation.MainActivity;
import com.leandro.instagram.register.presentation.datasource.RegisterDataSource;
import com.leandro.instagram.register.presentation.datasource.RegisterLocalDataSource;

import butterknife.BindView;

public class RegisterActivity extends AbstractActivity implements RegisterView {
    @BindView(R.id.register_scrollview)
    ScrollView scrollView;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();
    }


    @Override
    protected void onInject() {
        RegisterDataSource dataSource = new RegisterLocalDataSource();
        presenter = new RegisterPresenter(dataSource);
        presenter.setRegisterView(this);
        showNextView(RegisterSteps.EMAIL);
    }

    @Override
    public void showNextView(RegisterSteps steps) {
        Fragment frag = null;

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
        switch (steps) {
            case EMAIL:
                layoutParams.gravity = Gravity.BOTTOM;
                frag = RegisterEmailFragment.newInstance(presenter); break;
            case NAME_PASSWORD:
                layoutParams.gravity = Gravity.BOTTOM;
                frag = RegisterNamePasswordFragment.newInstance(presenter);
                break;
            case WELCOME:
                layoutParams.gravity = Gravity.BOTTOM;
                frag = RegisterWelcomeFragment.newInstance(presenter);
                break;
            case PHOTO:
                layoutParams.gravity = Gravity.TOP;
                frag = RegisterPhotoFragment.newInstance(presenter);
                break;
        }
        scrollView.setLayoutParams(layoutParams);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (manager.findFragmentById(R.id.register_fragment) == null) {
            transaction.add(R.id.register_fragment, frag, steps.name());
        } else {
            transaction.replace(R.id.register_fragment, frag, steps.name());
            transaction.addToBackStack(steps.name());
        }
        transaction.commit();
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }
}