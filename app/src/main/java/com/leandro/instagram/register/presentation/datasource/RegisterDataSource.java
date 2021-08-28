package com.leandro.instagram.register.presentation.datasource;

import com.leandro.instagram.commom.presenter.Presenter;

public interface RegisterDataSource {

    void createUser(String name, String email, String passeword, Presenter presenter);
}
