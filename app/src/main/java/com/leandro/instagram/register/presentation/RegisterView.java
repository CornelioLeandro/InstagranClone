package com.leandro.instagram.register.presentation;

import android.content.Context;

public interface RegisterView {

    interface EmailView{

        Context getContext();

        void onFailureForm(String emailError);

        void showNextView();
    }
}
