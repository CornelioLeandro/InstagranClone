package com.leandro.instagram.login.datasource;

import com.leandro.instagram.commom.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {
    @Override
    public void login(String email, String password, Presenter presenter) {
        presenter.onError("erro1");
        presenter.onComplete();
    }
}
