package com.dvilson.healthyadolescent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dvilson.healthyadolescent.viewmodels.LoginRegisterViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LogInActivity";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private LoginRegisterViewModel mViewModel;
    private TextInputEditText mEditTextEmail,mEditTextPassword;
    private Button signIn;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginRegisterViewModel.class);
        setupFirebaseAuth();
        init();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEditTextEmail.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();

                mViewModel.login(email,password);
            }
        });




    }

    public void init (){

        mEditTextEmail = findViewById(R.id.activity_login_edit_email);
        mEditTextPassword = findViewById(R.id.activity_login_edit_password);
        signIn = findViewById(R.id.activity_login_btn_sign_in);
        mProgressBar = findViewById(R.id.activity_login_progressBar);

    }
    public void registerActivity(View view){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    private void setupFirebaseAuth(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: Signed_in "+ user.getUid());
                    if (user.isEmailVerified()){
                        Toast.makeText(LogInActivity.this,"You're autheticad with " + user.getEmail(),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(LogInActivity.this,"Check you're inbox to verify you email",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Log.d(TAG, "onAuthStateChanged: Signed_out");
                }
            }
        };
    }


    private void hideDialog(){
        mProgressBar.setVisibility(View.GONE);
    }
    private void showDialog(){
        if(mProgressBar.getVisibility() == View.GONE){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // checkAuthenticationState();
    }

    private void checkAuthenticationState(){

        FirebaseUser  user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent intent = new Intent(this,LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();


        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }
}