package com.leandro.instagram.register.presentation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractActivity;
import com.leandro.instagram.register.presentation.datasource.RegisterDataSource;
import com.leandro.instagram.register.presentation.datasource.RegisterLocalDataSource;

public class RegisterActivity extends AbstractActivity implements RegisterView {

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
        Fragment frag = RegisterEmailFragment.newInstance(presenter);
        switch (steps) {
            case EMAIL:
                break;
            case NAME_PASSWORD: frag = RegisterNamePasswordFragment.newInstance(presenter); break;
            case WELCOME: frag = RegisterWelcomeFragment.newInstance(presenter); break;
        }
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
    protected int getLayout() {
        return R.layout.activity_register;
    }
}