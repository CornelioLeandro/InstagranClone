package com.leandro.instagram.register.presentation;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.leandro.instagram.R;
import com.leandro.instagram.commom.view.AbstractFragment;
import com.leandro.instagram.commom.view.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterEmailFragment extends AbstractFragment<RegisterPresenter> implements  RegisterView.EmailView{
    @BindView(R.id.register_edittext_email_input)
    TextInputLayout inputLayoutEmail;

    @BindView(R.id.register_edittext_email)
    EditText editTextEmail;

    @BindView(R.id.register_button_enter)
    LoadingButton buttonNewxt;

    public  RegisterEmailFragment(){}

    public static RegisterEmailFragment newInstance(RegisterPresenter presenter) {
            RegisterEmailFragment fragment = new RegisterEmailFragment();
            fragment.setPresenter(presenter);
            presenter.setEmailView(fragment);
            return fragment;
        }



    @Override
    public void showProgressBar() {
        buttonNewxt.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        buttonNewxt.showProgress(false);
    }

    @Override
    public void onFailureForm(String emailError) {
        inputLayoutEmail.setError(emailError);
        editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background));
    }

    @Override
    public void showNextView() {

    }

    @OnClick(R.id.register_textview_email_login)
    public void onTextViewLoginClick(){
        if (isAdded() && getActivity() != null)
            getActivity().finish();
    }

    @OnClick(R.id.register_button_enter)
    public void onButtonNextClick(){

    }

    @OnTextChanged(R.id.register_edittext_email)
    public void onTextChanged(CharSequence s){
        buttonNewxt.setEnabled(!editTextEmail.getText().toString().isEmpty());

        editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background));
        inputLayoutEmail.setError(null);
        inputLayoutEmail.setErrorEnabled(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_email;
    }

}
