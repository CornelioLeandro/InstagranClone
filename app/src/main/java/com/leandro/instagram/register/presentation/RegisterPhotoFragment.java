package com.leandro.instagram.register.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.CustomDialog;

public class RegisterPhotoFragment extends Fragment {


    public RegisterPhotoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_photo, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton((v) -> {
                    switch (v.getId()){
                        case R.string.take_picture:
                            Log.i("teste", "Take picture");
                            break;
                        case R.string.search_gallery:
                            Log.i("teste", "search_gallery");
                            break;
                        case R.string.import_facebook:
                            Log.i("teste", "import_facebook");
                            break;
                    }
                },R.string.take_picture,R.string.search_gallery,R.string.import_facebook)
                .build();
        customDialog.show();
    }
}
