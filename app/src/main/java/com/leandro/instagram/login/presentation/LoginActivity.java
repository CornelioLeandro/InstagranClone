package com.leandro.instagram.login.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class LoginActivity extends AbstractActivity implements LoginView {
    @BindView(R.id.login_edittext_email)
    EditText editTextEmail;
    @BindView(R.id.login_edittext_password)
    EditText editTextPassword;

    @BindView(R.id.login_edittext_password_input)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.login_edittext_email_input)
    TextInputLayout inputLayoutEmail;

    @BindView(R.id.login_button_enter)
    LoadingButton buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        editTextEmail.addTextChangedListener(watcher);
        editTextPassword.addTextChangedListener(watcher);

        buttonEnter.setOnClickListener(v -> {
            buttonEnter.showProgress(true);

            new Handler().postDelayed(() -> {
                buttonEnter.showProgress(false);
            }, 400);

        });
    }

    private TextWatcher watcher = new TextWatcher() {
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
    };

    @Override
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            inputLayoutEmail.setError(emailError);
            editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background));
        }
    }

    @Override
    public void onUserLogged() {
        // TODO: 22/07/2021
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


}