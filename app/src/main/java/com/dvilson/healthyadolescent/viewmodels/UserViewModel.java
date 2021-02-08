package com.dvilson.healthyadolescent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dvilson.healthyadolescent.models.User;
import com.dvilson.healthyadolescent.repositories.DatabaseRepository;
import com.google.firebase.database.DatabaseReference;

public class UserViewModel extends AndroidViewModel {
    DatabaseRepository mDatabaseRepository;
    MutableLiveData<User> mUserMutableLiveData;



    public UserViewModel(@NonNull Application application) {
        super(application);
        mDatabaseRepository = new DatabaseRepository(application);
        mUserMutableLiveData = mDatabaseRepository.getUserLiveData();

    }

    public void saveUser(String uid,String fullName, String pseudo){
        mDatabaseRepository.saveUser(uid,fullName,pseudo);


    }

    public LiveData<User> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }

}

