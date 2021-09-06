package com.leandro.instagram.register.presentation;

import android.net.Uri;
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
    private RegisterView.PhotoView photoview;
    private Uri uri;

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

    public  void setPhotoView(RegisterView.PhotoView photoView){
        this.photoview = photoView;
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

        namePasswordView.showProgressBar();
        dataSource.createUser(name,email,password,this);
    }

    public void showPhotoView() {
        registerView.showNextView(RegisterSteps.PHOTO);
    }

    public void setUri(Uri uri){
        this.uri = uri;
        photoview.onImageCropperd(uri);
    }

    public void showCamera(){
        registerView.showCamera();
    }

    public void showGallery(){
        registerView.showGallery();
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
