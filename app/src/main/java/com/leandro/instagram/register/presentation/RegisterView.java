package com.leandro.instagram.register.presentation;

import android.content.Context;
import android.net.Uri;

import com.leandro.instagram.commom.view.View;

public interface RegisterView {
    void showNextView(RegisterSteps steps);
    void onUserCreated();

    void showCamera();

    void showGallery();

    interface EmailView {
        Context getContext();
        void onFailureForm(String emailError);
    }

    interface NamePasswordView extends View {
        Context getContext();
        void onFailureForm(String nameError, String passwordError);
        void onFailureCreateUser(String message);
    }

    interface WelcomeView {
        Context getContext();

    }

    interface PhotoView extends View {
        void onImageCropperd(Uri uri);
    }
}
