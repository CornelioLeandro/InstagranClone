package com.leandro.instagram.commom.presenter;

import com.leandro.instagram.commom.model.UserAuth;

public interface Presenter<T> {

    void onSucess(T response);

    void onError(String message);

    void  onComplete();



}
