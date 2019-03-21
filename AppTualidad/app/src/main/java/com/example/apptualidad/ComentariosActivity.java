package com.example.apptualidad;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apptualidad.Fragments.ComentariosFragment;
import com.example.apptualidad.Fragments.LoginFragment;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Generator.UtilUser;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.AddComentario;
import com.example.apptualidad.Model.Comentarios;
import com.example.apptualidad.Services.ComentarioService;
import com.example.apptualidad.ViewModels.AniadirComentario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentariosActivity extends AppCompatActivity implements listaNoticiasInterface {
    private String id;
    private ImageView imageView_yo;
    private EditText editText_comentario;
    private TextView textView_publicar;
    private AddComentario addComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        getSupportActionBar().setTitle("Comentarios");
        
        findids();
        
        events();


        Glide
                .with(ComentariosActivity.this)
                .load(UtilUser.getImagen(ComentariosActivity.this))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView_yo);
    }

    private void events() {
        textView_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComentario = new AddComentario(editText_comentario.getText().toString());

                ComentarioService service = ServiceGenerator.createService(ComentarioService.class, UtilToken.getToken(ComentariosActivity.this), TipoAutenticacion.JWT);
                Call<Comentarios> call = service.addComent(id, addComentario);

                call.enqueue(new Callback<Comentarios>() {
                    @Override
                    public void onResponse(Call<Comentarios> call, Response<Comentarios> response) {
                        if (response.isSuccessful()) {
                            AniadirComentario aniadirComentario = ViewModelProviders.of(ComentariosActivity.this).get(AniadirComentario.class);

                            aniadirComentario.selectedAplicar("si");

                            editText_comentario.setText("");
                        } else {
                            Toast.makeText(ComentariosActivity.this, "Fallo al añadir comentario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Comentarios> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(ComentariosActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void findids() {
        imageView_yo = findViewById(R.id.imageView_yo);
        editText_comentario = findViewById(R.id.editText_comentario);
        textView_publicar = findViewById(R.id.textView_publicar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        id = intent.getStringExtra("id");


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_comments, new ComentariosFragment())
                .commit();
    }

    public String getIdNoticia(){
        return id;
    }
}
