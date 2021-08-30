package com.leandro.instagram.register.presentation;

import android.widget.TextView;

import com.leandro.instagram.R;
import com.leandro.instagram.commom.model.UserAuth;
import com.leandro.instagram.commom.presenter.Presenter;
import com.leandro.instagram.commom.util.Strings;
import com.leandro.instagram.register.presentation.datasource.RegisterDataSource;

public class RegisterPresenter implements Presenter<UserAuth> {
    private final RegisterDataSource dataSource;
    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView namePasswordView;
    private RegisterView registerView;

    private String email;
    private String name;
    private String password;

    public RegisterPresenter(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void setRegisterView(RegisterView registerView){
        this.registerView = registerView;
    }


    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

   public void setNamePasswordView(RegisterView.NamePasswordView namePasswordView){
    this.namePasswordView = namePasswordView;
    }

    public void setEmail(String email){
        if(!Strings.emailValid(email)) {
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }
        this.email = email;
        registerView.showNextView(RegisterSteps.NAME_PASSWORD);
    }
    public void setNameAndPasswordView(String name, String password,String confirmPassword){
        if (!password.equals(confirmPassword)){
        namePasswordView.onFailureForm(null,namePasswordView.getContext().getString(R.string.password_not_equal));
        return;
        }
        this.name = name;
        this.password = password;

        namePasswordView.showProgressBar();
        dataSource.createUser(this.name,this.email,this.password,this);
    }

    public void showPhotoView() {
        registerView.showNextView(RegisterSteps.PHOTO);
    }

    public String getName() {
        return name;
    }

    public void jumpRegistration(){
        registerView.onUserCreated();
    }

    @Override
    public void onSucess(UserAuth response) {
        registerView.showNextView(RegisterSteps.WELCOME);
    }

    @Override
    public void onError(String message) {
        namePasswordView.onFailureCreateUser(message);
    }

    @Override
    public void onComplete() {
    namePasswordView.hideProgressBar();
    }

}
