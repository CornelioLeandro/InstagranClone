package com.leandro.instagram.main.home.datasource;

import com.leandro.instagram.commom.model.Database;
import com.leandro.instagram.commom.model.Feed;
import com.leandro.instagram.commom.presenter.Presenter;

import java.util.List;

public class HomeLocalDataSource implements HomeDataSource {
    @Override
    public void findFeed(Presenter<List<Feed>> presenter) {
        Database db = Database.getInstance();
        db.findFeed(db.getUser().getUUID())
                .addOnSuccessListener(presenter::onSucess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }
}
