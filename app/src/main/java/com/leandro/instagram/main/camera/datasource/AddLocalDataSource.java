package com.leandro.instagram.main.camera.datasource;

import android.net.Uri;

import com.leandro.instagram.commom.model.Database;
import com.leandro.instagram.commom.presenter.Presenter;

public class AddLocalDataSource  implements AddDataSource{
    @Override
    public void savePost(Uri uri, String caption, Presenter presenter) {
        Database db = Database.getInstance();
        db.createPost(db.getUser().getUUID(),uri,caption)
                .addOnSuccessListener((Database.OnSuccessListener<Void>) presenter::onSucess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }
}
