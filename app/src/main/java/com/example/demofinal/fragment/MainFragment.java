package com.example.demofinal.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demofinal.R;
import com.example.demofinal.ServiceActivity;
import com.example.demofinal.utility.MyAlert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainFragment extends Fragment {
    private String emailString, passwordString;

    //  press key:   ctrl + o
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkStatus();
        // register controller
        // press alt + enter
        registerController();
//loginController
        loginController();
    }

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailEditText = getView().findViewById(R.id.emailEditText);
                EditText passwordEditText = getView().findViewById(R.id.passwordEditText);

                emailString = emailEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                if (emailString.isEmpty() || passwordString.isEmpty()) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog("Have space", "Please fill all Blank");
                } else {
                    checkAuthen();
                }
            }
        });
    }


    private void checkAuthen() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                    //                            getActivity().getSupportFragmentManager().beginTransaction()
                    //                                    .replace(R.id.contentMainFragment, new ServiceFragment())
                    //                                    .commit();
                    moveToService();
                } else {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog("Authenticate fail", "Because ==>" + task.getException().getMessage());
                }
            }
        });
    }

    private void checkStatus() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) { // if already logged
                   moveToService();         }     }

            private void moveToService () {
                startActivity(new Intent(getActivity(), ServiceActivity.class));
                getActivity().finish();
            }


            private void registerController () {
                TextView textView = getView().findViewById(R.id.txtRegister);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {             // Replace fragment
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.contentMainFragment, new RegisterFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                });
            }

            @Nullable
            @Override
            public View onCreateView (@NonNull LayoutInflater
            inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState){
                View view = inflater.inflate(R.layout.fragment_main, container, false);
                return view;
            }
        }
