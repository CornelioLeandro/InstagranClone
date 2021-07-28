package com.leandro.instagram.commom.presenter;

import com.leandro.instagram.commom.model.UserAuth;

public interface Presenter {

    void onSucess(UserAuth response);

    void onError(String message);

    void  onComplete();



}
