package com.example.apptualidad.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.apptualidad.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText editText_email_login, editText_password_login;
    private Button Button_login, Button_loginToRegistro;
    private LoginInterface loginInterface;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findIds(view);

        events();


        return view;
    }

    private void events() {
        Button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInterface.navegarRegistro();
            }
        });
    }

    private void findIds(View view) {
        editText_email_login = view.findViewById(R.id.editText_email_login);
        editText_password_login = view.findViewById(R.id.editText_password_login);
        Button_login = view.findViewById(R.id.Button_login);
        Button_loginToRegistro = view.findViewById(R.id.Button_loginToRegistro);
    }

    public interface LoginInterface {
        void navegarRegistro();
    }

}
