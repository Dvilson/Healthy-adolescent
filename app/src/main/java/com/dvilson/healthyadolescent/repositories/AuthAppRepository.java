package com.dvilson.healthyadolescent.repositories;

import android.app.Application;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.PasswordAuthentication;

public class AuthAppRepository {
    private Application mApplication;
    private FirebaseAuth mFirebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggetOutLiveData;

    public AuthAppRepository(Application application) {
        mApplication = application;
        mFirebaseAuth = FirebaseAuth.getInstance();
        userLiveData= new MutableLiveData<>();
        loggetOutLiveData = new MutableLiveData<>();

        if(mFirebaseAuth.getCurrentUser() != null){
            userLiveData.postValue(mFirebaseAuth.getCurrentUser());
            loggetOutLiveData.postValue(false);
        }
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggetOutLiveData() {
        return loggetOutLiveData;
    }

    public void register (String email,String password){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(mApplication.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                userLiveData.postValue(mFirebaseAuth.getCurrentUser());

                            }else{
                                Toast.makeText(mApplication.getApplicationContext(),"Registration Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    public void login(String email, String password){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(mApplication.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                userLiveData.postValue(mFirebaseAuth.getCurrentUser());
                            }else{
                                Toast.makeText(mApplication.getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void logOut(){
        mFirebaseAuth.signOut();
        loggetOutLiveData.postValue(true);
    }
}
