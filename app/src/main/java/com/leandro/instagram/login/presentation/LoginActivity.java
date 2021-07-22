package com.leandro.instagram.login.presentation;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractActivity;
import com.leandro.instagram.commom.view.LoadingButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AbstractActivity implements LoginView , TextWatcher{

    @BindView(R.id.login_edittext_email) EditText editTextEmail;
    @BindView(R.id.login_edittext_password) EditText editTextPassword;

    @BindView(R.id.login_edittext_password_input) TextInputLayout inputLayoutPassword;
    @BindView(R.id.login_edittext_email_input) TextInputLayout inputLayoutEmail;

    @BindView(R.id.login_button_enter) LoadingButton buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editTextEmail.addTextChangedListener(this);
        editTextPassword.addTextChangedListener(this);
    }

    @Override
    public void showProgressBar() {
        buttonEnter.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        buttonEnter.showProgress(false);
    }

    @Override
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            inputLayoutEmail.setError(emailError);
            editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background));
        }
        if (passwordError != null) {
            inputLayoutPassword.setError(emailError);
            editTextPassword.setBackground(findDrawable(R.drawable.edit_text_background));
        }
    }

    @Override
    public void onUserLogged() {
        // TODO: 22/07/2021
    }

    @OnClick(R.id.login_button_enter)
    public void onButtonEnterClick() {
        buttonEnter.showProgress(true);

        new Handler().postDelayed(() -> {
            buttonEnter.showProgress(false);
        }, 400);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().isEmpty())
            buttonEnter.setEnabled(true);
        else
            buttonEnter.setEnabled(false);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }
}