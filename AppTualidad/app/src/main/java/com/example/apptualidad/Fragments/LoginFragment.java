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
import com.example.apptualidad.R;
import com.example.apptualidad.Responses.LoginResponse;
import com.example.apptualidad.Services.SessionService;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText editText_email_login, editText_password_login;
    private Button Button_login, Button_loginToRegistro;
    ProgressDialog progressDialog;
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

    private void findIds(View view) {
        editText_email_login = view.findViewById(R.id.editText_email_login);
        editText_password_login = view.findViewById(R.id.editText_password_login);
        Button_login = view.findViewById(R.id.Button_login);
        Button_loginToRegistro = view.findViewById(R.id.Button_loginToRegistro);
    }

    private void events() {
        Button_loginToRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInterface.navegarRegistro();
            }
        });
        Button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                doLogin();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginInterface) {
            loginInterface = (LoginInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement loginInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginInterface = null;
    }

    public void doLogin() {

        String email = editText_email_login.getText().toString();
        String password = editText_password_login.getText().toString();

        String credentials = Credentials.basic(email, password);


        if (validarString(email) && validarString(password)) {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading..."); // Setting Message
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);

            SessionService service = ServiceGenerator.createService(SessionService.class);
            Call<LoginResponse> call = service.doLogin(credentials);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() != 201) {
                        // error
                        Log.e("RequestError", response.message());
                        Toast.makeText(getContext(), "Email o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {

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
        } else {

            Toast.makeText(getContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    Boolean validarString(String texto) {
        return texto != null && texto.trim().length() > 0;
    }


    public interface LoginInterface {
        void navegarRegistro();
    }

}
