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
public class RegistroFragment extends Fragment {
    private EditText editText_name_registro, editText_email_registro, editText_password_registro;
    private Button Button_registro, Button_registroToLogin;
    private RegistroIterface registroIterface;


    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_registro, container, false);

        findIds(view);

        events();

        return view;
    }

    private void events() {
        Button_registroToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registroIterface.navegarLogin();
            }
        });
    }

    private void findIds(View view) {
        editText_name_registro = view.findViewById(R.id.editText_name_registro);
        editText_email_registro = view.findViewById(R.id.editText_email_registro);
        editText_password_registro = view.findViewById(R.id.editText_password_registro);
        Button_registro = view.findViewById(R.id.Button_registro);
        Button_registroToLogin = view.findViewById(R.id.Button_registroToLogin);


    }

    public interface RegistroIterface {
        void navegarLogin();
    }

}
