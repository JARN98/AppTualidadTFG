package com.example.apptualidad.Fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptualidad.DashboardActivity;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Generator.UtilUser;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.EditPass;
import com.example.apptualidad.Model.EditUser;
import com.example.apptualidad.Model.User;
import com.example.apptualidad.R;
import com.example.apptualidad.Responses.LoginResponse;
import com.example.apptualidad.Responses.UploadPhotoUser;
import com.example.apptualidad.Services.SessionService;
import com.example.apptualidad.Services.UserService;
import com.example.apptualidad.ViewModels.EditarPerfilViewModel;
import com.example.apptualidad.ViewModels.SubirFoto;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PerfilFragment extends Fragment {

    private static final int READ_REQUEST_CODE = 7;
    private listaNoticiasInterface mListener;
    private ImageView imageView;
    private Button button_change_photo;
    private EditText editText_change_perfil, editText_change_name, editText_pass_actual, editText_pass_nueva, editText_pass_rep;
    boolean valido = false;
    boolean cambiarPass = false;
    Uri uriSelected;

    public PerfilFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_perfil, container, false);

        findids(view);


        cambiarPhoto();

        return view;
    }


    private void cambiarPhoto() {
        
        button_change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
        

    }

    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("Filechooser URI", "Uri: " + uri.toString());
                uriSelected = uri;
                try {
                    subirFotoApi();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void subirFotoApi() throws IOException {

        InputStream inputStream = getContext().getContentResolver().openInputStream(uriSelected);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int cantBytes;
        byte[] buffer = new byte[1024 * 4];

        while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
            baos.write(buffer, 0, cantBytes);
        }


        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContext().getContentResolver().getType(uriSelected)), baos.toByteArray());


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", "photo", requestFile);

        RequestBody user = RequestBody.create(MultipartBody.FORM, UtilUser.getId(getContext()));
        UserService service = ServiceGenerator.createService(UserService.class, UtilToken.getToken(getContext()), TipoAutenticacion.JWT);
        Call<UploadPhotoUser> call = service.addPhoto(body, user);

        call.enqueue(new Callback<UploadPhotoUser>() {
            @Override
            public void onResponse(Call<UploadPhotoUser> call, Response<UploadPhotoUser> response) {
                if (response.isSuccessful()) {
                    SubirFoto subirFoto = ViewModelProviders.of((FragmentActivity) getContext()).get(SubirFoto.class);

                    subirFoto.selectedAplicar("si");
                } else {
                    Toast.makeText(getContext(), "Fallo al subir las fotos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadPhotoUser> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        EditarPerfilViewModel editarPerfilViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(EditarPerfilViewModel.class);

        editarPerfilViewModel.getAll().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                editarPerfil();
            }
        });

        getUser();

        SubirFoto subirFoto = ViewModelProviders.of((FragmentActivity) getContext()).get(SubirFoto.class);

        subirFoto.getAll().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                getUser();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof listaNoticiasInterface) {
            mListener = (listaNoticiasInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement listaNoticiasInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getUser() {
        UserService service = ServiceGenerator.createService(UserService.class, UtilToken.getToken(getContext()), TipoAutenticacion.JWT);
        Call<User> call = service.getMe();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    editText_change_name.setText(response.body().getName());
                    editText_change_perfil.setText(response.body().getEmail());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    Glide
                            .with(getContext())
                            .load(response.body().getPicture())
                            .into(imageView);
                } else {
                    Toast.makeText(getContext(), "Error, no se puede mostrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findids(View view) {
        imageView = view.findViewById(R.id.imageView);
        button_change_photo = view.findViewById(R.id.button_change_photo);
        editText_change_perfil = view.findViewById(R.id.editText_change_perfil);
        editText_change_name = view.findViewById(R.id.editText_change_name);
        editText_pass_actual = view.findViewById(R.id.editText_pass_actual);
        editText_pass_nueva = view.findViewById(R.id.editText_pass_nueva);
        editText_pass_rep = view.findViewById(R.id.editText_pass_rep);
    }

    private void editarPerfil() {


        valido = validarFormulario();

        if (valido) {
            EditUser editUser = new EditUser(editText_change_name.getText().toString(), editText_change_perfil.getText().toString());
            UserService service = ServiceGenerator.createService(UserService.class, UtilToken.getToken(getContext()), TipoAutenticacion.JWT);
            Call<User> call = service.editMe(editUser, UtilUser.getId(getContext()));

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if ( response.isSuccessful()) {
                        Toast.makeText(getContext(), "Perfil editado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Ha habido un error al editar tu perfil", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Lo siento, su formulario no es válido", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarFormulario() {
        boolean sirve = true;

        if(editText_change_name.getText().toString().isEmpty()) {
            sirve = false;
        }

        if (editText_change_perfil.getText().toString().isEmpty()) {
            sirve = false;
        }

        if(!editText_pass_actual.getText().toString().isEmpty() && !editText_pass_nueva.getText().toString().isEmpty() && !editText_pass_actual.getText().toString().isEmpty()) {
            if (!editText_pass_nueva.getText().toString().equals(editText_pass_rep.getText().toString())) {
                sirve = false;
            } else {
                String credentials = Credentials.basic(UtilUser.getEmail(getContext()), editText_pass_actual.getText().toString());
                SessionService service = ServiceGenerator.createService(SessionService.class);
                Call<LoginResponse> call = service.doLogin(credentials);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.code() != 201) {
                            // error
                            valido = false;
                        } else {
                            valido = true;
                            changuePass();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
                sirve =  valido;
            }
        } else if(!editText_pass_actual.getText().toString().isEmpty() || !editText_pass_nueva.getText().toString().isEmpty() || !editText_pass_rep.getText().toString().isEmpty()) {
            sirve = false;
        } else {
            sirve = true;
        }

        return sirve;
    }

    private void changuePass() {
        EditPass editPass = new EditPass(editText_pass_nueva.getText().toString());
        String credentials = Credentials.basic(UtilUser.getEmail(getActivity()), editText_pass_actual.getText().toString());
        UserService service1 = ServiceGenerator.createService(UserService.class);
        Call<User> call1 = service1.editPass(credentials, UtilUser.getId(getContext()), editPass);

        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if ( response.isSuccessful()) {
                    Toast.makeText(getContext(), "Contraseña editada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Ha habido un error al editar tu contraseña", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
