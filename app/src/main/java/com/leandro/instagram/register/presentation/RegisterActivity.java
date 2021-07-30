package com.leandro.instagram.register.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractActivity;
import com.leandro.instagram.main.presentation.MainActivity;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    public static void launch(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();
    }


    @Override
    protected void onInject() {
     RegisterEmailFragment frag = new RegisterEmailFragment();
        FragmentManager manager = getSupportFragmentManager();
       FragmentTransaction transaction = manager.beginTransaction();

       transaction.add(R.id.register_fragment, frag, "fragment1");

       transaction.commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

}