package com.leandro.instagram.login.presentation;

import com.leandro.instagram.commom.view.View;

public interface LoginView extends View {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();
}
