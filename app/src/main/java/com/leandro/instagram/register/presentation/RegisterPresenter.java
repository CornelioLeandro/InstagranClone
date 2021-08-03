package com.leandro.instagram.register.presentation;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.util.Strings;

public class RegisterPresenter {
    private RegisterView.EmailView emailView;
    private String email;

    public void setEmailView(RegisterView.EmailView view) {
        this.emailView = emailView;
    }

    public void setEmail(String email){
        if(!Strings.emailValid(email)) {
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }
        this.email = email;
    }
}
