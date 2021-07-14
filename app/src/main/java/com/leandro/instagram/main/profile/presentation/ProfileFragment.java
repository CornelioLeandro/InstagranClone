package com.leandro.instagram.main.profile.presentation;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leandro.instagram.R;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_profile, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.profile_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new PostAdapter());


        return view;
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
