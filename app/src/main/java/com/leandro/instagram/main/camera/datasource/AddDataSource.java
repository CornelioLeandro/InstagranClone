package com.leandro.instagram.main.camera.datasource;

import android.net.Uri;

import com.leandro.instagram.commom.presenter.Presenter;

public interface AddDataSource {

    void savePost(Uri uri, String caption, Presenter presenter);
}
