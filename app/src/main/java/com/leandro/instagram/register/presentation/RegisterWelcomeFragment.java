package com.leandro.instagram.register.presentation;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractFragment;
import com.leandro.instagram.commom.component.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterWelcomeFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.WelcomeView {
    @BindView(R.id.register_textview_welcome)
    TextView textViewWelcome;

    @BindView(R.id.register_button_welcome_next)
    LoadingButton buttonNext;

    @BindView(R.id.register_textview_welcoem_login)
    TextView textViewLogin;

    public RegisterWelcomeFragment() {}

    public static Fragment newInstance(RegisterPresenter presenter) {
        RegisterWelcomeFragment fragment = new RegisterWelcomeFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext.setEnabled(true);
        textViewWelcome.setText(getString(R.string.welcome_to_instagram, presenter.getName()));
    }

    @OnClick(R.id.register_button_welcome_next)
    public void onButtonNextClick(){
        presenter.showPhotoView();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_welcome;
    }
}
