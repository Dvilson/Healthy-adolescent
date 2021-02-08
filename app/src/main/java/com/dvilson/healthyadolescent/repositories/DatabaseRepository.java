package com.dvilson.healthyadolescent.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dvilson.healthyadolescent.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DatabaseRepository {

    private Application mApplication;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth mFirebaseAuth;

    private MutableLiveData<User> mUserLiveDataDatabase;


    public DatabaseRepository(Application application) {
        mApplication = application;
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mReference = mDatabase.getReference("Users");
        mUserLiveDataDatabase = new MutableLiveData<>();

        if(mFirebaseAuth.getCurrentUser()!= null ){

            String uid = mFirebaseAuth.getCurrentUser().getUid();
            Query query = mReference.orderByKey().equalTo(uid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot singleSnapshot: snapshot.getChildren()){
                        User user = singleSnapshot.getValue(User.class);
                        mUserLiveDataDatabase.postValue(user);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

    public MutableLiveData<User> getUserLiveData() {
        return mUserLiveDataDatabase;
    }

    public void saveUser(String uid, String fullName, String pseudo){
        User user = new User(fullName,pseudo);
        uid = mFirebaseAuth.getCurrentUser().getUid();
        mReference.child(uid).setValue(user);
    }
}
