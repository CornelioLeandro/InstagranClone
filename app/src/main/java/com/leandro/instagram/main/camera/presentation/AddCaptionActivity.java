package com.leandro.instagram.main.camera.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.presenter.Presenter;
import com.leandro.instagram.commom.view.AbstractActivity;
import com.leandro.instagram.main.camera.datasource.AddLocalDataSource;

import butterknife.BindView;

public class AddCaptionActivity extends AbstractActivity implements AddCaptionView {

    @BindView(R.id.main_add_caption_image_view)
    ImageView imageView;

    @BindView(R.id.main_add_caption_edit_text)
    EditText editText;

    private Uri uri;
    private AddPresenter presenter;

    public static void lauch(Context context, Uri uri) {
        Intent intent = new Intent(context, AddCaptionActivity.class);
        intent.putExtra("uri", uri);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            Drawable drawable = findDrawable(R.drawable.ic_arrow_back);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onInject() {
    uri = getIntent().getExtras().getParcelable("uri");
    imageView.setImageURI(uri);

        AddLocalDataSource datasource = new AddLocalDataSource();
        presenter = new AddPresenter(this,datasource);
    }

    @Override
    public void postSave() {
        // TODO: 28/11/2021  
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                presenter.createPost(uri, editText.getText().toString());
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_caption;
    }



}