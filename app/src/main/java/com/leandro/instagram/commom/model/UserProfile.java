package com.leandro.instagram.commom.model;

import java.util.List;

public class UserProfile {
    private User user;
    private List<Post> posts;
    private boolean following;

    public UserProfile(User user, List<Post> posts, boolean following) {
        this.user = user;
        this.posts = posts;
        this.following = following;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
