package com.leandro.instagram.main.profile.datasource;

import com.leandro.instagram.commom.model.UserProfile;
import com.leandro.instagram.commom.presenter.Presenter;
import com.leandro.instagram.main.profile.presentation.ProfilePresenter;

public interface ProfileDataSource {

    void findUser(Presenter<UserProfile> presenter);
}
