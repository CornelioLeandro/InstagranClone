package com.leandro.instagram.register.presentation.datasource;

import android.net.Uri;

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

    @Override
    public void addPhoto(Uri uri, Presenter presenter) {
       Database db =  Database.getInstance();
                db.addPhoto(db.getUser().getUUID(), uri)
                        .addOnSuccessListener((Database.OnSuccessListener<Boolean>) presenter::onSucess);
    }
}
