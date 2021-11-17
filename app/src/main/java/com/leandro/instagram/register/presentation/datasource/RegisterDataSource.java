package com.leandro.instagram.register.presentation.datasource;

import android.net.Uri;

import com.leandro.instagram.commom.presenter.Presenter;

public interface RegisterDataSource {

    void createUser(String name, String email, String passeword, Presenter presenter);

    void addPhoto(Uri uri, Presenter presenter);
}
