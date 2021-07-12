package com.leandro.instagram.login.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.leandro.instagram.R;

public class LoginActivity extends AppCompatActivity {

    private LoadingButton buttonEnter;

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().isEmpty())
                buttonEnter.setEnabled(true);
            else
                buttonEnter.setEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTextEmail = findViewById(R.id.login_edittext_email);
        final EditText editTextPassword = findViewById(R.id.login_edittext_password);

        editTextEmail.addTextChangedListener(watcher);
        editTextPassword.addTextChangedListener(watcher);


        buttonEnter = findViewById(R.id.login_button_enter);
        buttonEnter.setOnClickListener(v -> {
            buttonEnter.showProgress(true);

            new Handler().postDelayed(() -> {
                buttonEnter.showProgress(false);

                TextInputLayout inputLayouttest = findViewById(R.id.login_edittext_email_input);
                inputLayouttest.setError("Email Invalido");
                editTextEmail.setBackground(ContextCompat.getDrawable(LoginActivity.this,
                        R.drawable.edit_text_background_erro));

                TextInputLayout inputLayoutPassword = findViewById(R.id.login_edittext_password_input);
                inputLayoutPassword.setError("Senha invalida");
                editTextPassword.setBackground(ContextCompat.getDrawable(LoginActivity.this,
                        R.drawable.edit_text_background_erro));

            }, 400);

        });
    }
}