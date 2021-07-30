package com.leandro.instagram.register.presentation;

public interface RegisterView {

    interface EmailView{
        void onFailureForm(String emailError);
    }
}
