package com.leandro.instagram.main.camera.presentation;

import android.net.Uri;

import com.leandro.instagram.commom.view.View;

import java.util.List;

public interface GalleryView extends View {

    void  onPictureLoaded(List<Uri> uris);
}
