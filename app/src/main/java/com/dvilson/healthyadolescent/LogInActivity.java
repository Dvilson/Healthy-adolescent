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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LogInActivity";
    FirebaseAuth.AuthStateListener mAuthStateListener;
    LoginRegisterViewModel mViewModel;
    EditText email,password;
    Button signIn;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mViewModel = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(LoginRegisterViewModel.class);
        mViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null ){
                    startActivity(new Intent(LogInActivity.this,MainActivity.class));

                }
            }
        });
        setupFirebaseAuth();
        init();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(email.getText().toString()) && !isEmpty(password.getText().toString())){
                    if(isValidEmail(email.getText().toString().trim())){
                        showDialog();
                        mViewModel.login(email.getText().toString().trim(),password.getText().toString().trim());
                        hideDialog();

                    }else{

                        Toast.makeText(LogInActivity.this,"please enter a valid address mail ",Toast.LENGTH_LONG).show();
                    }

                }

                else{
                    Toast.makeText(LogInActivity.this,"Please fill out all the fields of the form",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    public void init (){

        email = findViewById(R.id.activity_login_edit_txt_email);
        password = findViewById(R.id.activity_login_edit_txt_password);
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

    private boolean isEmpty(String s ){
        return s.equals("");
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
        mViewModel.getUserLiveData().observe(this,);
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