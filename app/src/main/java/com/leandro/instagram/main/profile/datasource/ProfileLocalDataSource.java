package com.leandro.instagram.main.profile.datasource;

import com.leandro.instagram.commom.model.Database;
import com.leandro.instagram.commom.model.Post;
import com.leandro.instagram.commom.model.User;
import com.leandro.instagram.commom.model.UserProfile;
import com.leandro.instagram.commom.presenter.Presenter;

import java.util.List;

public class ProfileLocalDataSource implements ProfileDataSource {
    @Override
    public void findUser(String uuid, Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(uuid)
                .addOnSuccessListener((Database.OnSuccessListener<User>) user1 -> {
                    db.findPosts(uuid)
                            .addOnSuccessListener((Database.OnSuccessListener<List<Post>>) posts -> {
                                db.following(db.getUser().getUUID(), uuid)
                                        .addOnSuccessListener((Database.OnSuccessListener<Boolean>) following -> {
                                            presenter.onSucess(new UserProfile(user1, posts, following));
                                            presenter.onComplete();
                                        });
                            });
                });
    }

    @Override
    public void follow(String user) {
        Database.getInstance().follow(Database.getInstance().getUser().getUUID(), user);
    }

    @Override
    public void unfollow(String user) {
        Database.getInstance().follow(Database.getInstance().getUser().getUUID(), user);
    }
}
