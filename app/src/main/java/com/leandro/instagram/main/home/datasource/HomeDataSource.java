package com.leandro.instagram.main.home.datasource;

import com.leandro.instagram.commom.model.Feed;
import com.leandro.instagram.commom.presenter.Presenter;

import java.util.List;

public interface HomeDataSource {

    void findFeed(Presenter<List<Feed>> presenter);
}
