package com.example.apptualidad;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptualidad.Fragments.EditPhotoFragment;
import com.example.apptualidad.Fragments.LoginFragment;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.Photo;
import com.example.apptualidad.Responses.UploadPhotoUser;
import com.example.apptualidad.Services.DashboardService;
import com.example.apptualidad.Util.FileUtils;
import com.example.apptualidad.ViewModels.EliminarFoto;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoActivity extends AppCompatActivity implements listaNoticiasInterface {
    private static final int REQUEST_CODE_LOAD_IMAGE = 20; //esta vez se merece esta nota el proyecto
    private String id;
    private FloatingActionButton floatingActionButton_addphoto;
    private TextView textView_title_noticia, textView6;
    Uri uriSelected;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        floatingActionButton_addphoto = findViewById(R.id.floatingActionButton_addphoto);
        textView6 = findViewById(R.id.textView6);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_photo, new EditPhotoFragment())
                .commit();

        Intent intent = getIntent();

        id = intent.getStringExtra("id");


        floatingActionButton_addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhotoActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == REQUEST_CODE_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            progressDialog = new ProgressDialog(PhotoActivity.this);
            progressDialog.setMessage("Loading...");
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + uri.toString());
                uriSelected = uri;
                try {
                    subirFotos();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void subirFotos() throws IOException {
        progressDialog = new ProgressDialog(PhotoActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(false);
        progressDialog.show();

        InputStream inputStream = PhotoActivity.this.getContentResolver().openInputStream(uriSelected);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int cantBytes;
        byte[] buffer = new byte[1024 * 4];

        while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
            baos.write(buffer, 0, cantBytes);
        }


        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(PhotoActivity.this.getContentResolver().getType(uriSelected)), baos.toByteArray());


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", "photo", requestFile);

        RequestBody noticia = RequestBody.create(MultipartBody.FORM, id);


        DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(PhotoActivity.this), TipoAutenticacion.JWT);
        Call<UploadPhotoUser> call = service.addPhoto(body, noticia);


        call.enqueue(new Callback<UploadPhotoUser>() {
            @Override
            public void onResponse(Call<UploadPhotoUser> call, Response<UploadPhotoUser> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    progressDialog.dismiss();
                    EliminarFoto eliminarFoto = ViewModelProviders.of(PhotoActivity.this).get(EliminarFoto.class);

                    eliminarFoto.selectedAplicar("si");
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(PhotoActivity.this, "Casiiii", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UploadPhotoUser> call, Throwable t) {
                Toast.makeText(PhotoActivity.this, "APIIIIII", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });



    }

    public void performFileSearch() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_LOAD_IMAGE);
    }




    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public String getIdNoticia() {
        return id;
    }
}
