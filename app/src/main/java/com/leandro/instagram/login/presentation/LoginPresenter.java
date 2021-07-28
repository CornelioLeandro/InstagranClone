package com.leandro.instagram.login.presentation;

import android.os.Handler;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.model.UserAuth;
import com.leandro.instagram.commom.presenter.Presenter;
import com.leandro.instagram.commom.util.Strings;
import com.leandro.instagram.login.datasource.LoginDataSource;

class LoginPresenter implements Presenter {
    private final LoginView view;
    private final LoginDataSource dataSource;

    LoginPresenter(LoginView view, LoginDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    void login(String email, String password){
        if (!Strings.emailValid(email)){
            view.onFailureForm(view.getContext().getString(R.string.invalid_email), null);
            return;
        }
        view.showProgressBar();
        dataSource.login(email,password,this);

        new Handler().postDelayed(() -> {
            view.hideProgressBar();
            view.onFailureForm("Erro1","Erro2");
        },2000);
    }

    @Override
    public void onSucess(UserAuth userAuth) {
        view.onUserLogged();

    }

    @Override
    public void onError(String message) {
        view.onFailureForm(null, message);
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
