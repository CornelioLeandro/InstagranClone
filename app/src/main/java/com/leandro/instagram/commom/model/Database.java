package com.leandro.instagram.commom.model;

import android.net.Uri;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Set<UserAuth> usersAuth;
    private static Set<User> users;
    private static Database INSTANCE;
    private static Set<Uri> storages;
    private static HashMap<String, HashSet<Post>> posts;
    private static HashMap<String, HashSet<Feed>> feed;
    private static HashMap<String, HashSet<String>> followers;

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OncCompleteListener onCompleteListener;
    private static UserAuth userAuth;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storages = new HashSet<>();
        posts = new HashMap<>();
        feed = new HashMap<>();
        followers = new HashMap<>();

        init();
    }

    public static Database getInstance() {
        return new Database();
        // if (INSTANCE == null)
        //    INSTANCE = new Database();
        //  INSTANCE.init();
        //return INSTANCE;
    }

    private static void init() {
        String email = "user1@gmail.com";
        String password = "123";
        String name = "Leandro Silva";

        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(email);
        userAuth.setPassword(password);
        usersAuth.add(userAuth);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUuid(userAuth.getUUID());

        users.add(user);
        Database.userAuth = userAuth;
    }

    public <T> Database addOnSuccessListener(OnSuccessListener<T> listener) {
        this.onSuccessListener = listener;
        return this;
    }

    public <T> Database addOnFailureListener(OnFailureListener listener) {
        this.onFailureListener = listener;
        return this;
    }

    public <T> Database addOnCompleteListener(OncCompleteListener listener) {
        this.onCompleteListener = listener;
        return this;
    }

    //select * from posts p inner join users u on p.user_id = u.id where u.uuid = ?
    public Database findPosts(String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<Post>> posts = Database.posts;
            HashSet<Post> res = posts.get(uuid);

            if (res == null)
                res = new HashSet<>();
            if (onSuccessListener != null)
                onSuccessListener.onSuccess(new ArrayList<>(res));
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }


    public Database findFeed(String uuid) {
        timeOut(() ->{
            HashMap<String, HashSet<Feed>> feed = Database.feed;
            HashSet<Feed> res = feed.get(uuid);

            if (res == null)
                res = new HashSet<>();

            if (onSuccessListener != null)
                onSuccessListener.onSuccess(new ArrayList<>(res));

            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    // select * from users where uuid = ?
    public Database findUser(String uuid) {
        timeOut(() -> {
            Set<User> users = Database.users;
            User res = null;
            for (User user : users) {
                if (user.getUuid().equals(uuid)) {
                    res = user;
                    break;
                }
            }
            if (onSuccessListener != null && res != null) {
                onSuccessListener.onSuccess(res);
            } else if (onFailureListener != null) {
                onFailureListener.onFailure(new IllegalArgumentException("Usuario nao encontrato"));
            }
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    public Database addPhoto(String uuid, Uri uri) {
        timeOut(() -> {
            Set<User> users = Database.users;
            for (User user : users) {
                if (user.getUuid().equals(uuid)) {
                    user.setUri(uri);
                }
            }
            storages.add(uri);
            onSuccessListener.onSuccess(true);
        });
        return this;
    }

    public Database createPost(String uuid,Uri uri,String caption){
        timeOut(() ->{
            HashMap<String, HashSet<Post>> postsMap = Database.posts;
            HashSet<Post> posts = postsMap.get(uuid);
            if (posts == null){
                posts = new HashSet<>();
                postsMap.put(uuid, posts);
            }
            Post post = new Post();
            post.setUri(uri);
            post.setCaption(caption);
            post.setTimestamp(System.currentTimeMillis())
            post.setUuid(String.valueOf(post.hashCode()));
            posts.add(post);

            HashMap<String, HashSet<String>> followersMap = Database.followers;
            HashSet<String> followers = followersMap.get(uuid);
            if (followers != null){
                followers = new HashSet<>();
                followersMap.put(uuid,followers);
            }else {
                HashMap<String, HashSet<Feed>> feedMap = Database.feed;
                for (String follower : followers){
                    HashSet<Feed> feeds = feedMap.get(follower);
                    if (feeds != null){
                        Feed feed = new Feed();
                        feed.setUri(post.getUri());
                        feed.setCaption(post.getCaption());

                    }
                }

            }
        });
    }

    public Database createUser(String name, String email, String password) {
        timeOut(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            usersAuth.add(userAuth);

            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setUuid(userAuth.getUUID());

            boolean added = users.add(user);
            if (added) {
                Database.userAuth = userAuth;

                HashMap<String, HashSet<String>> followersMap = Database.followers;
                followersMap.put(userAuth.getUUID(), new HashSet<>());

                HashMap<String, HashSet<Feed>> feedsMap = Database.feed;
                feedsMap.put(userAuth.getUUID(), new HashSet<>());


                if (onSuccessListener != null)
                    onSuccessListener.onSuccess(userAuth);
            } else {
                Database.userAuth = null;
                if (onSuccessListener != null)
                    onFailureListener.onFailure(new IllegalArgumentException("Usuario já existe"));
            }
            if (onSuccessListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    public Database login(String email, String password) {
        timeOut(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            if (usersAuth.contains(userAuth)) {
                Database.userAuth = userAuth;
                onSuccessListener.onSuccess(userAuth);
            } else {
                Database.usersAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuario nao encontrado"));
            }
            onCompleteListener.onComplete();
        });
        return this;
    }

    public UserAuth getUser() {
        return userAuth;
    }

    private void timeOut(Runnable r) {
        new Handler().postDelayed(r, 1400);
    }

    public interface OnSuccessListener<T> {
        void onSuccess(T response);
    }

    public interface OnFailureListener<T> {
        void onFailure(Exception e);
    }

    public interface OncCompleteListener<T> {
        void onComplete();
    }
}
