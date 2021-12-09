package com.leandro.instagram.main.profile.datasource;

import com.leandro.instagram.commom.model.UserProfile;
import com.leandro.instagram.commom.presenter.Presenter;

public interface ProfileDataSource {

    void findUser(String user, Presenter<UserProfile> presenter);

    void follow(String user);

    void unfollow(String user);
}
