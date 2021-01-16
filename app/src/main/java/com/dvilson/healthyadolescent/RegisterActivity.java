package com.dvilson.healthyadolescent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dvilson.healthyadolescent.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText fullName, pseudo,email,password,confirmPassword;
    Button signUp;
    TextView singIn;
    FirebaseAuth mAuth;
    ProgressBar mProgressBar;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(pseudo.getText().toString())
                        && !isEmpty(fullName.getText().toString())
                        && !isEmpty(email.getText().toString())
                        && !isEmpty(password.getText().toString())
                        && !isEmpty(confirmPassword.getText().toString())){

                    if(isValidEmail(email.getText().toString())){
                        if(doStringMatch(password.getText().toString().trim(),confirmPassword.getText().toString().trim())){
                            mProgressBar.setVisibility(View.VISIBLE);
                            register(email.getText().toString().trim(),password.getText().toString().trim());
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"Please Enter a valid address email",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this,"Please  fill out all the fields",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void init(){

        fullName = findViewById(R.id.activity_register_edit_txt_full_name);
        pseudo = findViewById(R.id.activity_register_edit_txt_pseudo);
        email = findViewById(R.id.activity_register_edit_txt_email);
        password = findViewById(R.id.activity_register_edit_txt_password);
        confirmPassword = findViewById(R.id.activity_register_edit_txt_confirm_password);
        signUp = findViewById(R.id.activity_register_btn_sign_up);
        singIn = findViewById(R.id.activity_register_tv_sign_in);
        mProgressBar = findViewById(R.id.activity_register_progressBar);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Users");

    }
     private void register(String email, String password){
         if(mAuth.getCurrentUser() != null){
             Log.d(TAG, "register:"+mAuth.getCurrentUser().getUid());
             mAuth.signOut();
         }else{
             showDialog();
             mAuth.createUserWithEmailAndPassword(email, password)
                     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 User user = new User(fullName.getText().toString().trim(),
                                         pseudo.getText().toString().trim());

                                         mReference
                                                 .child(mAuth.getCurrentUser().getUid())
                                                 .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {

                                         if(task.isSuccessful()){
                                            // UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder();
                                             hideDialog();
                                             sendVerifcationEmail();
                                             mAuth.signOut();


                                             startActivity(new Intent(RegisterActivity.this,LogInActivity.class));
                                             finish();
                                             Toast.makeText(RegisterActivity.this,"User Registered successfully",Toast.LENGTH_LONG).show();
                                         }else{
                                             hideDialog();
                                             Toast.makeText(RegisterActivity.this,"Error wwhile trying to register the user",Toast.LENGTH_LONG).show();
                                         }
                                     }
                                 });

                             }else{
                                 Log.d(TAG, "onComplete:"+ Objects.requireNonNull(task.getException()).toString());
                             }

                         }
                     });
         }
    }

    private boolean isEmpty(String s){
        return s.equals("");
    }

    private boolean doStringMatch( String s1, String s2){
        return  s1.equals(s2);
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    private void sendVerifcationEmail(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Sent the email verification ",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(RegisterActivity.this,"Couldn't send the email verification",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void hideDialog(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void  showDialog(){
        if (mProgressBar.getVisibility()== View.GONE){
            mProgressBar.setVisibility(View.VISIBLE);
        }

    }

    public void signInActivity(View view){
        startActivity(new Intent(RegisterActivity.this,LogInActivity.class));
    }
}