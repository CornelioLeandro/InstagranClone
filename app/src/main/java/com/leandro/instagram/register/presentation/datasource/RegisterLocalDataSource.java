package com.leandro.instagram.register.presentation.datasource;

import com.leandro.instagram.commom.model.Database;
import com.leandro.instagram.commom.model.UserAuth;
import com.leandro.instagram.commom.presenter.Presenter;

public class RegisterLocalDataSource implements RegisterDataSource {
    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        Database.getInstance().createUser(name,email,password)
                .addOnSuccessListener((Database.OnSuccessListener<UserAuth>) presenter::onSucess)
                .addOnFailureListener(e-> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }
}
