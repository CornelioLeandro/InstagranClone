package com.leandro.instagram.main.profile.presentation;

import com.leandro.instagram.commom.model.Post;
import com.leandro.instagram.commom.model.User;
import com.leandro.instagram.commom.model.UserProfile;
import com.leandro.instagram.commom.presenter.Presenter;
import com.leandro.instagram.main.presentation.MainView;
import com.leandro.instagram.main.profile.datasource.ProfileDataSource;

import java.util.List;

public class ProfilePresenter implements Presenter<UserProfile> {

    private final ProfileDataSource dataSource;
    private MainView.ProfileView view;

    public ProfilePresenter(ProfileDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }

    public void findUser() {
        view.showProgressBar();
        dataSource.findUser(this);
    }

    @Override
    public void onSucess(UserProfile userProfile) {
        User user = userProfile.getUser();
        List<Post> posts = userProfile.getPosts();

        view.showData(
                user.getName(),
                String.valueOf(user.getFollowing()),
                String.valueOf(user.getFollowers()),
                String.valueOf(user.getPosts())
        );
        view.showPosts(posts);
        if (user.getUri() != user.getUri()) ;
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onComplete() {
        view.hideProgressBar();

    }


}
