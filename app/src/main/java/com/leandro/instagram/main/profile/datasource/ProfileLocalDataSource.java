package com.leandro.instagram.main.profile.datasource;

import com.leandro.instagram.commom.model.Database;
import com.leandro.instagram.commom.model.Post;
import com.leandro.instagram.commom.model.User;
import com.leandro.instagram.commom.model.UserProfile;
import com.leandro.instagram.commom.presenter.Presenter;
import com.leandro.instagram.main.profile.presentation.ProfilePresenter;

import java.util.List;

public class ProfileLocalDataSource implements ProfileDataSource {
    @Override
    public void findUser(Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(db.getUser().getUUID())
                .addOnSuccessListener((Database.OnSuccessListener<User>) user -> {
                    db.findPosts(db.getUser().getUUID())
                            .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                                presenter.onSucess(new UserProfile(user, posts));
                                presenter.onComplete();
                            });
                });
    }
}
