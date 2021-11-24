package com.leandro.instagram.main.camera.presentation;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.component.CameraPreview;
import com.leandro.instagram.commom.component.MediaHelper;
import com.leandro.instagram.commom.view.AbstractFragment;

import butterknife.BindView;

public class CameraFragment extends AbstractFragment {

    @BindView(R.id.camera_progress)
    ProgressBar progressBar;

    @BindView(R.id.camera_surface)
    FrameLayout frameLayout;

    @BindView(R.id.container_preview)
    LinearLayout containerPreview;

    @BindView(R.id.camera_image_view_picture)
    Button buttonCamera;
    private MediaHelper mediaHelper;
    private Camera camera;

    public CameraFragment()  {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (getContext() != null){
            mediaHelper = MediaHelper.getInstace(this);
            if (mediaHelper.checkCameraHardware(getContext())){
                camera = mediaHelper.getCameraInstace();
               CameraPreview cameraPreview = new CameraPreview(getContext(), camera);
                frameLayout.addView(cameraPreview);
            }

        }
        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected int getLayout() {
        return (R.layout.fragment_main_camera);
    }

}
