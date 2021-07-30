package com.leandro.instagram.login.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractActivity;
import com.leandro.instagram.commom.view.LoadingButton;
import com.leandro.instagram.login.datasource.LoginDataSource;
import com.leandro.instagram.login.datasource.LoginLocalDataSource;
import com.leandro.instagram.main.presentation.MainActivity;
import com.leandro.instagram.register.presentation.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.leandro.instagram.main.presentation.MainActivity.launch;

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

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();

    }

    @Override
    protected void onInject() {
        LoginDataSource dataSource = new LoginLocalDataSource();
        presenter = new LoginPresenter(this, dataSource);

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
            inputLayoutPassword.setError(passwordError);
            editTextPassword.setBackground(findDrawable(R.drawable.edit_text_background));
        }
    }

    @Override
    public void onUserLogged() {
        launch(this);
    }

    @OnClick(R.id.login_button_enter)
    public void onButtonEnterClick() {
        presenter.login(editTextEmail.getText().toString(), editTextPassword.getText().toString());
    }

    @OnClick(R.id.login_textview_register)
    public void onTextViewRegisterClick(){
        RegisterActivity.launch(this);
    }

    @OnTextChanged({R.id.login_edittext_email,R.id.login_edittext_password})
    public void onTextChanged(CharSequence s){
        buttonEnter.setEnabled(!editTextEmail.getText().toString().isEmpty() &&
                !editTextPassword.getText().toString().isEmpty());

        if(s.hashCode() == editTextEmail.getText().hashCode()){
            editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background));
            inputLayoutEmail.setError(null);
            inputLayoutEmail.setErrorEnabled(false);
        }else if (s.hashCode() == editTextPassword.getText().hashCode()){
            editTextPassword.setBackground(findDrawable(R.drawable.edit_text_background));
            inputLayoutPassword.setError(null);
            inputLayoutPassword.setErrorEnabled(false);
        }
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }
}