package com.dvilson.healthyadolescent.viewmodels;

import android.app.Application;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.util.LogTime;
import com.dvilson.healthyadolescent.LogInActivity;
import com.dvilson.healthyadolescent.RegisterActivity;
import com.dvilson.healthyadolescent.models.User;
import com.dvilson.healthyadolescent.repositories.AuthAppRepository;
import com.dvilson.healthyadolescent.repositories.DatabaseRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class LoginRegisterViewModel  extends AndroidViewModel {

    private AuthAppRepository mAuthAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private DatabaseRepository mDatabaseRepository;

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        mAuthAppRepository = new AuthAppRepository(application);
        userLiveData = mAuthAppRepository.getUserLiveData();
        mDatabaseRepository = new DatabaseRepository(application);
        

    }

    public void login(String email,String password){
        if(!isEmpty(email) && !isEmpty(password)){
            if(isValidEmail(email.trim())){

                mAuthAppRepository.login(email,password);

            }else{

                Toast.makeText(getApplication().getBaseContext(),"please enter a valid address mail ",Toast.LENGTH_LONG).show();
            }

        }

        else{
            Toast.makeText(getApplication().getBaseContext(),"Please fill out all the fields of the form",Toast.LENGTH_SHORT).show();
        }

    }

    public void register(String email,String password,String confirmPassword,String pseudo, String fullName) {
        if (userLiveData != null) {
            userLiveData.getValue().getUid();
        } else {
                    if(!isEmpty(pseudo)
                            && !isEmpty(fullName)
                            && !isEmpty(email)
                            && !isEmpty(password)
                            && !isEmpty(confirmPassword)){

                        if(isValidEmail(email)){
                            if(doStringMatch(password,confirmPassword)){
                                mAuthAppRepository.register(email,password);
                                String userUID =  mAuthAppRepository.getUserLiveData().getValue().getUid();
                                mDatabaseRepository.saveUser(userUID,fullName,pseudo);

                            }
                        }else{
                            Toast.makeText(getApplication().getBaseContext(),"Please Enter a valid address email",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(getApplication().getBaseContext(),"Please  fill out all the fields",Toast.LENGTH_LONG).show();
                    }

        }




    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }


    private boolean isEmpty(String s ){
        return s.equals("");
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    private boolean doStringMatch( String s1, String s2){
        return  s1.equals(s2);
    }



}

