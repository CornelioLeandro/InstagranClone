package com.leandro.instagram.main.presentation;

import android.net.Uri;

import com.leandro.instagram.commom.model.Feed;
import com.leandro.instagram.commom.model.Post;
import com.leandro.instagram.commom.model.User;
import com.leandro.instagram.commom.view.View;

import java.util.List;

public interface MainView extends View {

    void scrollToolbarEnabled(boolean enabled);

    void showprofile(String user);

    void disposeProfileDetail();

    public interface ProfileView extends View {

        void showPhoto(Uri photo);

        void showData(String name, String following, String followers, String posts, boolean editProfile, boolean follow);

        void showPosts(List<Post> posts);
    }

    public interface HomeView extends View {

        void showFeed(List<Feed> response);

    }

    public interface SearchView {

        void showUsers(List<User> users);
    }
}
