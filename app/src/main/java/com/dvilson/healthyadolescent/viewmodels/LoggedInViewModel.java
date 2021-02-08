package com.dvilson.healthyadolescent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dvilson.healthyadolescent.repositories.AuthAppRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInViewModel  extends AndroidViewModel {

    private AuthAppRepository mAuthAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean>loggedOutLiveData;



    public LoggedInViewModel(@NonNull Application application) {
        super(application);
        mAuthAppRepository = new AuthAppRepository(application);
        userLiveData = mAuthAppRepository.getUserLiveData();
        loggedOutLiveData = mAuthAppRepository.getLoggetOutLiveData();
    }



    public void logOut(){
        mAuthAppRepository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
}
