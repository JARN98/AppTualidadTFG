package com.example.apptualidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Geocode.Geocode;
import com.example.apptualidad.Model.AddNoticiaDto;
import com.example.apptualidad.Model.EditNoticia;
import com.example.apptualidad.Responses.GetOneNoticiaResponse;
import com.example.apptualidad.Services.DashboardService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNoticia extends AppCompatActivity {
    private EditText editText_title_create, editText_description_addnotice, editText_direccion_addnotice;
    private Button button_next_addnotice;
    private AddNoticiaDto addNoticia;
    private String loc;
    private String id = "";
    private EditNoticia editNoticia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_noticia);

        findids();

        id = getIntent().getStringExtra("id");

        if(id == null) {
            crearNoticia();
        } else {
            getOneNoticia();
            actualizarNoticia();
        }


    }

    private void actualizarNoticia() {
        button_next_addnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loc = getLocation(editText_direccion_addnotice.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (loc.equals("0.0,0.0")) {
                    Toast.makeText(AddNoticia.this, "Debe escribir una dirección válida", Toast.LENGTH_SHORT).show();
                } else {
                    editNoticia = new EditNoticia(editText_title_create.getText().toString(), editText_description_addnotice.getText().toString(), editText_direccion_addnotice.getText().toString());

                    DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(AddNoticia.this), TipoAutenticacion.JWT);
                    Call<GetOneNoticiaResponse> call = service.putNoticia(id, editNoticia);

                    call.enqueue(new Callback<GetOneNoticiaResponse>() {
                        @Override
                        public void onResponse(Call<GetOneNoticiaResponse> call, Response<GetOneNoticiaResponse> response) {
                            if ( response.isSuccessful() ) {
                                Intent i = new Intent(AddNoticia.this, PhotoActivity.class);
                                i.putExtra("id", response.body().getId());
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(AddNoticia.this, "Fallo al actualizar la noticia", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetOneNoticiaResponse> call, Throwable t) {
                            Log.e("NetworkFailure", t.getMessage());
                            Toast.makeText(AddNoticia.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }

    private void crearNoticia() {

        button_next_addnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loc = getLocation(editText_direccion_addnotice.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (loc.equals("0.0,0.0")) {
                    Toast.makeText(AddNoticia.this, "Debe escribir una dirección válida", Toast.LENGTH_SHORT).show();
                } else {


                    addNoticia = new AddNoticiaDto(editText_title_create.getText().toString(), editText_description_addnotice.getText().toString(), editText_direccion_addnotice.getText().toString(), loc);

                    DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(AddNoticia.this), TipoAutenticacion.JWT);

                    Call<GetOneNoticiaResponse> call = service.addNoticia(addNoticia);

                    call.enqueue(new Callback<GetOneNoticiaResponse>() {
                        @Override
                        public void onResponse(Call<GetOneNoticiaResponse> call, Response<GetOneNoticiaResponse> response) {
                            if (response.isSuccessful()) {
                                Intent i = new Intent(AddNoticia.this, PhotoActivity.class);
                                i.putExtra("id", response.body().getId());
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(AddNoticia.this, "Fallo al añadir noticia", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetOneNoticiaResponse> call, Throwable t) {
                            Log.e("NetworkFailure", t.getMessage());
                            Toast.makeText(AddNoticia.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }

    private void getOneNoticia() {

        DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(AddNoticia.this), TipoAutenticacion.JWT);

        Call<GetOneNoticiaResponse> call = service.getOneNoticia(id);

        call.enqueue(new Callback<GetOneNoticiaResponse>() {
            @Override
            public void onResponse(Call<GetOneNoticiaResponse> call, Response<GetOneNoticiaResponse> response) {
                if ( response.isSuccessful() ) {
                    editText_description_addnotice.setText(response.body().getDescription());
                    editText_title_create.setText(response.body().getTitle());
                    editText_direccion_addnotice.setText(response.body().getDireccion());
                } else {
                    Toast.makeText(AddNoticia.this, "Fallo al traer la noticia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOneNoticiaResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(AddNoticia.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findids() {
        editText_title_create = findViewById(R.id.editText_title_create);
        editText_description_addnotice = findViewById(R.id.editText_description_addnotice);
        editText_direccion_addnotice = findViewById(R.id.editText_direccion_addnotice);
        button_next_addnotice = findViewById(R.id.button_next_addnotice);
    }

    public String getLocation(String direccion) throws IOException {
        String loc = Geocode.getLongLat(AddNoticia.this, direccion);
        return loc;
    }
}
