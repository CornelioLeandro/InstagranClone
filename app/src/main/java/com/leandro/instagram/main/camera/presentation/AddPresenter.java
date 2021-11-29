package com.leandro.instagram.main.camera.presentation;

import android.net.Uri;

import com.leandro.instagram.commom.presenter.Presenter;
import com.leandro.instagram.main.camera.datasource.AddDataSource;

public class AddPresenter implements Presenter<Void> {
    private final AddCaptionView view;
    private final AddDataSource dataSource;

    public AddPresenter(AddCaptionView view, AddDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    void createPost(Uri uri, String caption) {
        view.showProgressBar();
        dataSource.savePost(uri, caption, this);
    }

    @Override
    public void onSucess(Void response) {
        view.postSave();
    }

    @Override
    public void onError(String message) {
// TODO: 28/11/2021
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
