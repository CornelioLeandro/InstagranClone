package com.leandro.instagram.register.presentation;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractFragment;
import com.leandro.instagram.commom.component.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterNamePasswordFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.NamePasswordView {

    @BindView(R.id.register_edittext_name_input)
    TextInputLayout inputLayoutName;

    @BindView(R.id.register_edittext_name)
    EditText editTextName;

    @BindView(R.id.register_edittext_name_password_input)
    TextInputLayout inputLayoutNamePassword;

    @BindView(R.id.register_edittext_name_password)
    EditText editTextNamePassword;

    @BindView(R.id.register_edittext_name_password_confirm_input)
    TextInputLayout inputLayoutNamePasswordConfirm;

    @BindView(R.id.register_edittext_name_password_confirm)
    EditText editTextNamePasswordConfirm;


    @BindView(R.id.register_name_button_next)
    LoadingButton buttonNext;

    public RegisterNamePasswordFragment() {
    }

    public static RegisterNamePasswordFragment newInstance(RegisterPresenter presenter) {
        RegisterNamePasswordFragment fragment = new RegisterNamePasswordFragment();
        fragment.setPresenter(presenter);
        presenter.setNamePasswordView(fragment);
        return fragment;
    }

    @Override
    public void showProgressBar() {
        buttonNext.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        buttonNext.showProgress(false);
    }


    @Override
    public void onFailureForm(String nameError, String passwordError) {
        if (nameError != null) {
            inputLayoutName.setError(nameError);
            editTextName.setBackground(findDrawable(R.drawable.edit_text_background_erro));
        }
        if (passwordError != null) {
            inputLayoutNamePassword.setError(passwordError);
            editTextNamePassword.setBackground(findDrawable(R.drawable.edit_text_background_erro));
        }
    }

    @Override
    public void onFailureCreateUser(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.register_textview_login)
    public void onTextViewLoginClick() {
        if (isAdded() && getActivity() != null)
            getActivity().finish();
    }

    @OnClick(R.id.register_name_button_next)
    public void onButtonNextClick() {
        presenter.setNameAndPasswordView(editTextName.getText().toString(),
                editTextNamePassword.getText().toString(),
                editTextNamePasswordConfirm.getText().toString());
    }

    @OnTextChanged({R.id.register_edittext_name,R.id.register_edittext_name_password,R.id.register_edittext_name_password_confirm})
    public void onTextChanged(CharSequence s){
        buttonNext.setEnabled(!editTextName.getText().toString().isEmpty() &&
                !editTextNamePassword.getText().toString().isEmpty() &&
                !editTextNamePasswordConfirm.getText().toString().isEmpty());

        editTextName.setBackground(findDrawable(R.drawable.edit_text_background_erro));
        inputLayoutName.setError(null);
        inputLayoutName.setErrorEnabled(false);

        editTextNamePassword.setBackground(findDrawable(R.drawable.edit_text_background_erro));
        inputLayoutNamePassword.setError(null);
        inputLayoutNamePassword.setErrorEnabled(false);

        editTextNamePasswordConfirm.setBackground(findDrawable(R.drawable.edit_text_background_erro));
        inputLayoutNamePasswordConfirm.setError(null);
        inputLayoutNamePasswordConfirm.setErrorEnabled(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_name_password;
    }
}
