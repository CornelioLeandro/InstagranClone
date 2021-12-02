package com.leandro.instagram.main.camera.datasource;

import android.content.Context;

import com.leandro.instagram.commom.presenter.Presenter;

public interface GalleryDataSource {
    void findPictures(Context context, Presenter presenter);
}
