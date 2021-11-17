package com.leandro.instagram.register.presentation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.component.CustomDialog;
import com.leandro.instagram.commom.view.AbstractFragment;
import com.leandro.instagram.commom.component.LoadingButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

    @BindView(R.id.register_button_photo_jump)
    Button button_jump;
    @BindView(R.id.register_button_photo_next)
    LoadingButton button_next;
    @BindView(R.id.register_camera_icon)
    ImageView imageViewCropped;


    public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        presenter.setPhotoView(fragment);
        fragment.setPresenter(presenter);
        return fragment;
    }

    public RegisterPhotoFragment() {
    }

    @OnClick(R.id.register_button_photo_next)
    public void onButtonNextClick() {
        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton((v) -> {
                    switch (v.getId()) {
                        case R.string.take_picture:
                            presenter.showCamera();
                            break;
                        case R.string.search_gallery:
                            presenter.showGallery();
                            break;
                    }
                }, R.string.take_picture, R.string.search_gallery, R.string.import_facebook)
                .build();
        customDialog.show();
    }

    @OnClick(R.id.register_button_photo_jump)
    public void onButtonJumpoClick() {
        presenter.jumpRegistration();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button_next.setEnabled(true);
    }

    public void showProgressBar(){
        button_next.showProgress(true);
    }

    @Override
    public void onImageCropperd(Uri uri) {
        if (getContext() != null && getContext().getContentResolver() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                imageViewCropped.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e("Teste", e.getMessage(), e);
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }
}

