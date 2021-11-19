package com.leandro.instagram.main.presentation;

import android.net.Uri;

import com.leandro.instagram.commom.model.Feed;
import com.leandro.instagram.commom.model.Post;
import com.leandro.instagram.commom.view.View;
import com.leandro.instagram.main.home.presentation.HomePresenter;

import java.util.List;

public interface MainView extends View {

    void scrollToolbarEnabled(boolean enabled);

    public interface ProfileView extends View {

        void showPhoto(Uri photo);

        void showData(String name, String following, String followers, String posts);

        void showPosts(List<Post> posts);
    }

    public interface HomeView extends View {

        void showFeed(List<Feed> response);

    }
}
