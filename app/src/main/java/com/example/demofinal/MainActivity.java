package com.example.demofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.demofinal.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Add Fragment
        if (savedInstanceState == null) { // if open app first time
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentMainFragment, new MainFragment())
                    .commit(); // insert new flagment
        }
    }
}