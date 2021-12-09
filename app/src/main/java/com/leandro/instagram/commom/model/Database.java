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

        String email = "user1@gmail.com";
        String password = "123";
        String name = "Leandro";
        init(email, password, name);

        for (int i = 0; i < 30; i++) {
            email = "user" + i + "@gmail.com";
            password = "123" + i;
            name = "Leandro" + i;
            init(email, password, name);
        }
        ;

        // init();
    }

    public static Database getInstance() {
        return new Database();
        // if (INSTANCE == null)
        //    INSTANCE = new Database();
        //  INSTANCE.init();
        //return INSTANCE;
    }

    private static void init(String email, String password, String name) {
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

    public Database follow(String uuidME, String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<String>> followersMap = Database.followers;
            HashSet<String> followers = followersMap.get(uuid);

            if (followers == null) {

                followers = new HashSet<>();
                followersMap.put(uuid, followers);
            }
            followers.add(uuidME);
            if (onSuccessListener != null)
                onSuccessListener.onSuccess(true);
        });
        return this;
    }

    public Database unfollow(String uuidME, String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<String>> followersMap = Database.followers;
            HashSet<String> followers = followersMap.get(uuid);

            if (followers == null) {

                followers = new HashSet<>();
                followersMap.put(uuid, followers);
            }
            followers.remove(uuidME);
            if (onSuccessListener != null)
                onSuccessListener.onSuccess(true);
        });
        return this;
    }


    public Database following(String uuidMe, String uuid) {
        timeOut(() -> {
            HashMap<String, HashSet<String>> followers = Database.followers;

            HashSet<String> followersOfUser = followers.get(uuid);
            if (followersOfUser == null)
                followersOfUser = new HashSet<>();
            boolean following = false;
            for (String userUuid : followersOfUser) {
                if (userUuid.equals(uuidMe)) {
                    following = true;
                    break;
                }
            }
            if (onSuccessListener != null)
                onSuccessListener.onSuccess(following);
            else if (onFailureListener != null)
                onFailureListener.onFailure(new IllegalArgumentException("Usuario não encontrato"));
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
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
        timeOut(() -> {
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

    public Database findUsers(String uuid, String query) {
        timeOut(() -> {
            ArrayList<User> users = new ArrayList<>();
            for (User user : Database.users) {
                if (!user.getUuid().equals(uuid) && user.getName().contains(query)) {
                    users.add(user);
                }
            }

            onSuccessListener.onSuccess(users);
            onCompleteListener.onComplete();
        });
        return this;
    }

    // select * from users where uuid = ? encontra o usuario logado
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
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));
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

    public Database createPost(String uuid, Uri uri, String caption) {

        HashMap<String, HashSet<Post>> postMap = Database.posts;

        HashSet<Post> posts = postMap.get(uuid);

        if (posts == null) {
            posts = new HashSet<>();
            postMap.put(uuid, posts);
        }

        Post post = new Post();
        post.setUri(uri);
        post.setCaption(caption);
        post.setTimestamp(System.currentTimeMillis());
        post.setUuid(String.valueOf(post.hashCode()));
        posts.add(post);

        HashMap<String, HashSet<String>> followersMap = Database.followers;

        HashSet<String> followers = followersMap.get(uuid);

        if (followers == null) {
            followers = new HashSet<>();
            followersMap.put(uuid, followers);
        } else {

            HashMap<String, HashSet<Feed>> feedMap = Database.feed;

            for (String follower : followers) {
                HashSet<Feed> feeds = feedMap.get(follower);

                if (feeds != null) {
                    Feed feed = new Feed();
                    feed.setUri(post.getUri());
                    feed.setCaption(post.getCaption());

                    // feed.setPublisher()
                    feed.setTimestamp(post.getTimestamp());

                    feeds.add(feed);
                }
            }
            HashSet<Feed> feedMe = feedMap.get(uuid);
            if (feedMe != null) {
                Feed feed = new Feed();
                feed.setUri(post.getUri());
                feed.setCaption(post.getCaption());
                // feed.setPublisher()
                feed.setTimestamp(post.getTimestamp());
                feedMe.add(feed);
            }
        }
        if (onSuccessListener != null)
            onSuccessListener.onSuccess(null);

        if (onCompleteListener != null)
            onCompleteListener.onComplete();

        return this;
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
