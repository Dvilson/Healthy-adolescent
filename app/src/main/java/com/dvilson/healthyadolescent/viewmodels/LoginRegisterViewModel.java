package com.dvilson.healthyadolescent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dvilson.healthyadolescent.repositories.AuthAppRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginRegisterViewModel  extends AndroidViewModel {

    private AuthAppRepository mAuthAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        mAuthAppRepository = new AuthAppRepository(application);
        userLiveData = mAuthAppRepository.getUserLiveData();

    }

    public void login(String email,String password){
        mAuthAppRepository.login(email,password);
    }

    public void register(String email,String password){
        mAuthAppRepository.register(email,password);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
