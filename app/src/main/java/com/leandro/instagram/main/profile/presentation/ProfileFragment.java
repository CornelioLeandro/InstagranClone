package com.leandro.instagram.main.profile.presentation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leandro.instagram.R;
import com.leandro.instagram.commom.model.Database;
import com.leandro.instagram.commom.model.Post;
import com.leandro.instagram.commom.view.AbstractFragment;
import com.leandro.instagram.main.presentation.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends AbstractFragment<ProfilePresenter> implements MainView.ProfileView {

    @BindView(R.id.profile_image_icon)
    CircleImageView imageViewProfile;

    @BindView(R.id.profile_textview_username)
    TextView txtUsername;

    @BindView(R.id.profile_textview_following_count)
    TextView txtFollowingCount;

    @BindView(R.id.profile_textview_followers_count)
    TextView txtFollowersCount;

    @BindView(R.id.profile_textview_post_count)
    TextView txtPostCount;

    @BindView(R.id.profile_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.profile_navigation_tabs)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.profile_button_edit_perfil)
    Button button_edit_perfil;


    private PostAdapter postAdapter;
    private MainView mainView;
    private ProfilePresenter profilePresenter;

    public static ProfileFragment newInstance(MainView mainView, ProfilePresenter profilePresenter) {
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setMainView(mainView);
        profileFragment.setPresenter(profilePresenter);

        profilePresenter.setView(profileFragment);
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
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_profile_grid:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    return true;
                case R.id.menu_profile_list:
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    return true;
            }
            return false;
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        postAdapter = new PostAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(postAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findUser();
    }

    @Override
    public void showPhoto(Uri photo) {
        if (getContext() != null && getContext().getContentResolver() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photo);
                imageViewProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e("Teste", e.getMessage(), e);
            }

        }
    }


    @Override
    public void showData(String name, String following, String followers, String posts, boolean editProfile, boolean follow) {
        txtUsername.setText(name);
        txtFollowersCount.setText(followers);
        txtFollowingCount.setText(following);
        txtPostCount.setText(posts);

        if (editProfile) {
            button_edit_perfil.setText(R.string.edit_profile);
        } else if (follow) {
            button_edit_perfil.setText(R.string.unfollow);
            button_edit_perfil.setTag(false);
        } else {
            button_edit_perfil.setText(R.string.follow);
            button_edit_perfil.setTag(true);
        }
    }

    @OnClick(R.id.profile_button_edit_perfil)
    public void onButtonFollowClick(){
        Boolean follow = (Boolean) button_edit_perfil.getTag();
        button_edit_perfil.setText(follow ? R.string.unfollow : R.string.follow);
        presenter.follow(follow);
        button_edit_perfil.setTag(!follow);
    }

    @Override
    public void showPosts(List<Post> posts) {
        postAdapter.setPosts(posts);
        postAdapter.notifyDataSetChanged();
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
        return R.layout.fragment_main_profile;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!presenter.getUser().equals(Database.getInstance().getUser().getUUID()))
                    mainView.disposeProfileDetail();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

        private List<Post> posts = new ArrayList<>();

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PostViewHolder(getLayoutInflater().inflate(R.layout.item_profile_grid, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            holder.bind(posts.get(position));
        }

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }

    private class PostViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imagePost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.gallery_Image_grid);
        }

        public void bind(Post post) {
            this.imagePost.setImageURI(post.getUri());
        }
    }
}
