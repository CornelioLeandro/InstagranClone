package com.leandro.instagram.login.datasource;

import com.leandro.instagram.commom.model.Database;
import com.leandro.instagram.commom.model.UserAuth;
import com.leandro.instagram.commom.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {
    @Override
    public void login(String email, String password, Presenter presenter) {
        Database.getInstance().login(email,password)
                .addOnSuccessListener(new Database.OnSuccessListener<UserAuth>() {
                    @Override
                    public void onSuccess(UserAuth response) {
                        presenter.onSucess(response);
                    }
                })
                .addOnFailureListener(new Database.OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        presenter.onError(e.getMessage());
                    }
                })
                .addOnCompleteListener(new Database.OncCompleteListener() {
                    @Override
                    public void onComplete() {
                        presenter.onComplete();
                    }
                });
    }
}
