package com.leandro.instagram.register.presentation;

import android.content.Context;

public interface RegisterView {

    void showNextView(RegisterSteps steps);

    interface EmailView{

        Context getContext();

        void onFailureForm(String emailError);

    }

    interface NamePasswordView {

        Context getContext();

        void onFailureForm(String nameError, String passwordError);
    }
}
