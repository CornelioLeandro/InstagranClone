package com.leandro.instagram.login.datasource;

import com.leandro.instagram.commom.presenter.Presenter;

public interface LoginDataSource {

    void login(String email, String password, Presenter presenter);
}
