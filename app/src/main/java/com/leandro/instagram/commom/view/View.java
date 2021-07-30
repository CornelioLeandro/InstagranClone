package com.leandro.instagram.commom.view;

import android.content.Context;

public interface View {

    Context getContext();

    void showProgressBar();

    void hideProgressBar();

    void setStatusBarDark();


}
