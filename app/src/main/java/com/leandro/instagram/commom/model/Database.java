package com.leandro.instagram.commom.model;

import android.net.Uri;
import android.os.Handler;

import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Set<UserAuth> usersAuth;
    private static Set<User> users;
    private static Database INSTANCE;
    private static Set<Uri> storages;

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OncCompleteListener onCompleteListener;
    private UserAuth userAuth;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storages = new HashSet<>();
    }

    public static Database getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Database();
        INSTANCE.init();
        return INSTANCE;
    }

    private void init() {
        String email = "user1@gmail.com";
        String password = "123";
        String name = "user";

        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(email);
        userAuth.setPassword(password);
        usersAuth.add(userAuth);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUuid(userAuth.getUUID());

        users.add(user);
        this.userAuth = userAuth;
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

    public Database addPhoto(String uuid,Uri uri){
        timeOut(() ->{
            Set<User> users = Database.users;
            for (User user: users){
                if (user.getUuid().equals(uuid)){
                    user.setUri(uri);
                }
            }
            storages.add(uri);
            onSuccessListener.onSuccess(true);
        });
        return this;
    }

    public Database createUser(String name, String email, String password){
        timeOut(() ->{
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
                this.userAuth = userAuth;
                if (onSuccessListener != null)
                onSuccessListener.onSuccess(userAuth);
            }else{
                this.userAuth = null;
                if (onSuccessListener != null)
                onFailureListener.onFailure(new IllegalArgumentException("Usuario já existe"));
            }
            if (onSuccessListener != null)
            onCompleteListener.onComplete();
        });
        return  this;
    }

    public Database login(String email, String password) {
        timeOut(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            if (usersAuth.contains(userAuth)) {
                this.userAuth = userAuth;
                onSuccessListener.onSuccess(userAuth);
            } else {
                this.usersAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuario nao encontrado"));
            }
            onCompleteListener.onComplete();
        });
        return this;
    }

    public UserAuth getUser(){
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
