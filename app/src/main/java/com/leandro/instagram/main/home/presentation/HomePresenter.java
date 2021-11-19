package com.leandro.instagram.main.home.presentation;

import com.leandro.instagram.commom.model.Feed;
import com.leandro.instagram.commom.presenter.Presenter;
import com.leandro.instagram.main.home.datasource.HomeDataSource;
import com.leandro.instagram.main.presentation.MainView;

import java.util.List;

public class HomePresenter implements Presenter<List<Feed>> {


    private final HomeDataSource dataSource;
    private MainView.HomeView view;

    public HomePresenter(HomeDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.HomeView view) {
        this.view = view;
    }

    public void findFeed(){
        view.showProgressBar();
        dataSource.findFeed(this);
    }

    @Override
    public void onSucess(List<Feed> response) {
    view.showFeed(response);
    }

    @Override
    public void onError(String message) {
        // TODO: 19/11/2021
    }

    @Override
    public void onComplete() {
    view.hideProgressBar();
    }
}
