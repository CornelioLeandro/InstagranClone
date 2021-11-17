package com.leandro.instagram.main.profile.presentation;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractFragment;
import com.leandro.instagram.main.home.presentation.HomeFragment;
import com.leandro.instagram.main.presentation.MainView;

import butterknife.BindView;

public class ProfileFragment extends AbstractFragment<ProfilePresenter> {

    private MainView mainView;
    @BindView(R.id.profile_recycler)
    RecyclerView recyclerView;

    public static ProfileFragment newInstance(MainView mainView) {
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setMainView(mainView);
        return profileFragment;
    }

    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }


    public ProfileFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = super.onCreateView(inflater,container,savedInstanceState);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new PostAdapter());
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_profile;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
        private int[] images = new int[]{
                R.drawable.ic_insta_add,R.drawable.ic_insta_add,R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,R.drawable.ic_insta_add,R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,R.drawable.ic_insta_add,R.drawable.ic_insta_add,
                R.drawable.ic_insta_add,R.drawable.ic_insta_add,R.drawable.ic_insta_add,
        };
        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PostViewHolder(getLayoutInflater().inflate(R.layout.item_profile_grid,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            holder.bind(images[position]);
        }

        @Override
        public int getItemCount() {
            return images.length;
        }
    }

    private class PostViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imagePost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.profileImage_grid);
        }

        public void bind(int image) {
            this.imagePost.setImageResource(image);
        }
    }
}
