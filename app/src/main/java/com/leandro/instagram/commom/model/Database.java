package com.leandro.instagram.commom.model;

import android.os.Handler;

import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Set<UserAuth> usersAuth;
    private static Database INSTANCE;
    private UserAuth userAuth;

    private OnSuccessListener onSuccessListener;
    private OnFailureListener onFailureListener;
    private OncCompleteListener onCompleteListener;


    static {
        usersAuth = new HashSet<>();
        usersAuth.add(new UserAuth("user1@gmail.com", "1234"));
        usersAuth.add(new UserAuth("user2@gmail.com", "12345"));
        usersAuth.add(new UserAuth("user3@gmail.com", "123456"));
        usersAuth.add(new UserAuth("user4@gmail.com", "1234567"));
        usersAuth.add(new UserAuth("user5@gmail.com", "12345678"));
    }


    public static Database getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Database();
        return INSTANCE;
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
