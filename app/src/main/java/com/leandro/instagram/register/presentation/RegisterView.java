package com.leandro.instagram.register.presentation;

import android.content.Context;

import com.leandro.instagram.commom.view.View;

public interface RegisterView {

    void showNextView(RegisterSteps steps);

    interface EmailView{

        Context getContext();

        void onFailureForm(String emailError);

    }

    interface NamePasswordView  extends View {

        Context getContext();

        void onFailureForm(String nameError, String passwordError);

        void onFailureCreateUser(String message);
    }

    interface WelcomeView{

        Context getContext();

        void onFailureForm(String emailError);
    }
}
