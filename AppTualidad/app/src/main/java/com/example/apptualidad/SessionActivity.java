package com.example.apptualidad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.apptualidad.Fragments.LoginFragment;
import com.example.apptualidad.Fragments.RegistroFragment;

public class SessionActivity extends AppCompatActivity implements LoginFragment.LoginInterface, RegistroFragment.RegistroIterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.SessionContainer, new LoginFragment())
                .commit();
    }

    public void navegarLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SessionContainer, new LoginFragment())
                .commit();
    }

    public void navegarRegistro() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SessionContainer, new RegistroFragment())
                .commit();
    }



}
