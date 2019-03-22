package com.example.apptualidad.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptualidad.DashboardActivity;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Generator.UtilUser;
import com.example.apptualidad.Model.UserDto;
import com.example.apptualidad.R;
import com.example.apptualidad.Responses.LoginResponse;
import com.example.apptualidad.Services.SessionService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {
    private EditText editText_name_registro, editText_email_registro, editText_password_registro;
    private Button Button_registro, Button_registroToLogin;
    private RegistroIterface registroIterface;
    ProgressDialog progressDialog;


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

        Button_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.show();*/
                doRegister();
            }
        });
    }


    public void doRegister() {

        String email = editText_email_registro.getText().toString();
        String password = editText_password_registro.getText().toString();
        String name = editText_name_registro.getText().toString();

        UserDto usuarioARegistrar = new UserDto(name, password, email);

        if (validarString(email) && validarString(password)) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading..."); // Setting Message
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// Progress Dialog Style Spinner
            progressDialog.setCancelable(false);
            progressDialog.show();

            SessionService service = ServiceGenerator.createService(SessionService.class);
            Call<LoginResponse> call = service.doRegister(usuarioARegistrar);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() != 201) {
                        // error
                        progressDialog.dismiss();
                        Log.e("RequestError", response.message());
                        Toast.makeText(getContext(), "Registro fallido", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        UtilToken.setToken(getActivity(), response.body().getToken());
                        UtilUser.setUserInfo(getActivity(), response.body().getUser());

                        startActivity(new Intent(getActivity(), DashboardActivity.class));
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
            progressDialog.dismiss();
        } else {

            Toast.makeText(getContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        progressDialog.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegistroIterface) {
            registroIterface = (RegistroIterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement registroIterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        registroIterface = null;
    }

    private void findIds(View view) {
        editText_name_registro = view.findViewById(R.id.editText_name_registro);
        editText_email_registro = view.findViewById(R.id.editText_email_registro);
        editText_password_registro = view.findViewById(R.id.editText_password_registro);
        Button_registro = view.findViewById(R.id.Button_registro);
        Button_registroToLogin = view.findViewById(R.id.Button_registroToLogin);


    }

    Boolean validarString(String texto) {
        return texto != null && texto.trim().length() > 0;
    }

    public interface RegistroIterface {
        void navegarLogin();
    }

}
