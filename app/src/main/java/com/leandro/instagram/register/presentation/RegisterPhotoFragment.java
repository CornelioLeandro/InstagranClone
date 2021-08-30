package com.leandro.instagram.register.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractFragment;
import com.leandro.instagram.commom.view.CustomDialog;
import com.leandro.instagram.commom.view.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

    @BindView(R.id.register_button_photo_jump)
    Button button_jump;
    @BindView(R.id.register_button_photo_next)
    LoadingButton button_next;


    public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    public RegisterPhotoFragment() {
    }

    @OnClick(R.id.register_button_photo_next)
    public void onButtonNextClick() {

    }

    @OnClick(R.id.register_button_photo_jump)
        public void onButtonJumpoClick() {
            presenter.jumpRegistration();
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button_jump.setEnabled(true);
        // CustomDialog customDialog = new CustomDialog.Builder(getContext())
        //         .setTitle(R.string.define_photo_profile)
        //         .addButton((v) -> {
        //             switch (v.getId()){
        //                 case R.string.take_picture:
        //                     Log.i("teste", "Take picture");
        //                     break;
        //                 case R.string.search_gallery:
        //                     Log.i("teste", "search_gallery");
        //                     break;
        //                 case R.string.import_facebook:
        //                     Log.i("teste", "import_facebook");
        //                     break;
        //             }
        //         },R.string.take_picture,R.string.search_gallery,R.string.import_facebook)
        //         .build();
        // customDialog.show();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }
}

