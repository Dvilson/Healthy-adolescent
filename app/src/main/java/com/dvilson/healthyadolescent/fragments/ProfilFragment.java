package com.dvilson.healthyadolescent.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvilson.healthyadolescent.R;
import com.dvilson.healthyadolescent.models.User;
import com.dvilson.healthyadolescent.viewmodels.LoginRegisterViewModel;
import com.dvilson.healthyadolescent.viewmodels.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfilFragment extends Fragment {
    private TextInputEditText mEditTextFullName, mEditTextPseudo, mEditTextEmail;
    TextView mTextViewName;
    CircleImageView imgProfil;
    UserViewModel mUserViewModel;
    LoginRegisterViewModel mViewModel;



    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(UserViewModel.class);
        mViewModel  = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(LoginRegisterViewModel.class);

        mUserViewModel.getUserMutableLiveData().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null ){
                    String pseudo = user.getPseudo();
                    String fullName  = user.getFullName();
                    mEditTextPseudo.setText(pseudo);
                    mEditTextFullName.setText(fullName);
                    mTextViewName.setText(pseudo);
                    mEditTextEmail.setText(mViewModel.getUserLiveData().getValue().getEmail());

                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View result = inflater.inflate(R.layout.fragment_profil, container, false);
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditTextFullName = view.findViewById(R.id.edit_full_name);
        mEditTextPseudo = view.findViewById(R.id.edit_pseudo);
        mEditTextEmail = view.findViewById(R.id.edit_email);
        mTextViewName = view.findViewById(R.id.text_pseudo);
    }
}