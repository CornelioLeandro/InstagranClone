package com.leandro.instagram.login.presentation;

public interface LoginView {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();
}
