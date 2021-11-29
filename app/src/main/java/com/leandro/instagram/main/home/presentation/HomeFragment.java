package com.leandro.instagram.main.home.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.model.Feed;
import com.leandro.instagram.commom.view.AbstractFragment;
import com.leandro.instagram.main.presentation.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends AbstractFragment<HomeFragment> implements MainView.HomeView {

    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;

    private FeedAdapter feedAdapter;
    private MainView mainView;
    private HomePresenter homePresenter;

    public static HomeFragment newInstance(MainView mainView, HomePresenter homePresenter) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setMainView(mainView);
        homeFragment.setPresenter(homeFragment);
        homePresenter.setView(homeFragment);

        return homeFragment;
    }


    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public HomeFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        feedAdapter = new FeedAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.home_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(feedAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       // homePresenter.findFeed();
    }

    @Override
    public void showFeed(List<Feed> response) {
    feedAdapter.setFeed(response);
    feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        mainView.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        mainView.hideProgressBar();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_home;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class FeedAdapter extends RecyclerView.Adapter<PostViewHolder> {

        private List<Feed> feed = new ArrayList<>();

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PostViewHolder(getLayoutInflater().inflate(R.layout.item_post_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            holder.bind(feed.get(position));
        }

        @Override
        public int getItemCount() {
            return feed.size();
        }

        public void setFeed(List<Feed> fedd) {
            this.feed = fedd;
        }
    }

    private class PostViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imagePost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.profile_image_grid);
        }

        public void bind(Feed feed) {
            // TODO: 19/11/2021
            this.imagePost.setImageURI(feed.getUri());
        }
    }
}

