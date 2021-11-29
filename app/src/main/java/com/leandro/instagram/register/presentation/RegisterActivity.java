package com.leandro.instagram.register.presentation;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.component.MediaHelper;
import com.leandro.instagram.commom.view.AbstractActivity;
import com.leandro.instagram.main.presentation.MainActivity;
import com.leandro.instagram.register.presentation.datasource.RegisterDataSource;
import com.leandro.instagram.register.presentation.datasource.RegisterLocalDataSource;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends AbstractActivity implements RegisterView, MediaHelper.OnImageCroppedListener {
    @BindView(R.id.register_scrollview)
    ScrollView scrollView;

    @BindView(R.id.register_root_container)
    FrameLayout rootContainer;

    @BindView(R.id.register_crop_image_view)
    CropImageView cropImageView;

    @BindView(R.id.register_button_crop)
    Button buttoncrop;

    private MediaHelper mediaHelper;

    public static void launch(android.content.Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private RegisterPresenter presenter;

    @Override
    public void onImagePicked(Uri uri) {
        cropImageView.setImageUriAsync(uri);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();
        mediaHelper = MediaHelper.getInstance(this)
                .cropView(cropImageView)
                .listener(this);
    }

    @Override
    protected void onInject() {
        RegisterDataSource dataSource = new RegisterLocalDataSource();
        presenter = new RegisterPresenter(dataSource);
        presenter.setRegisterView(this);
        showNextView(RegisterSteps.EMAIL);
    }

    @Override
    public void showNextView(RegisterSteps steps) {
        Fragment frag = null;

        switch (steps) {
            case EMAIL:
                frag = RegisterEmailFragment.newInstance(presenter);
                break;
            case NAME_PASSWORD:
                frag = RegisterNamePasswordFragment.newInstance(presenter);
                break;
            case WELCOME:
                frag = RegisterWelcomeFragment.newInstance(presenter);
                break;
            case PHOTO:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) scrollView.getLayoutParams();
                layoutParams.gravity = Gravity.CENTER;
                scrollView.setLayoutParams(layoutParams);
                frag = RegisterPhotoFragment.newInstance(presenter);
                break;
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (manager.findFragmentById(R.id.register_fragment) == null) {
            transaction.add(R.id.register_fragment, frag, steps.name());
        } else {
            transaction.replace(R.id.register_fragment, frag, steps.name());
            transaction.addToBackStack(steps.name());
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropViewEnabled(true);
        MediaHelper mediaHelper = MediaHelper.getInstance(this);
        mediaHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageCropped(Uri uri) {
        presenter.setUri(uri);
    }

    private void cropViewEnabled(boolean enabled) {
        cropImageView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(enabled ? View.GONE : View.VISIBLE);
        buttoncrop.setVisibility(enabled ? View.VISIBLE : View.GONE);
        rootContainer.setBackgroundColor(enabled ? findColor(R.color.black): findColor(R.color.white));
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this, MainActivity.REGISTER_ACTIVITY);
    }

    @Override
    public void showCamera() {
        mediaHelper.chooserCamera();
    }

    @Override
    public void showGallery() {
        MediaHelper.getInstance(this)
        .chooserGallery();
    }

    @OnClick(R.id.register_button_crop)
    public void onButtonCropClick(){
        cropViewEnabled(false);
        MediaHelper.getInstance(this).cropImage(cropImageView);
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }
}